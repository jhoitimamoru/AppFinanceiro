package com.jmksolutions.appfinanceiro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jmksolutions.appfinanceiro.Util.DataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ConfigActivity extends AppCompatActivity {
    private final int PERMISSAO_LEITURA = 1;
    public  static int CONTROLE_PERMISSAO =0 ;

    /*COMPONENTES DA TELA*/
    Button BtnExportar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        this.CriarComponentes();
        this.CriarToolbar();

        //creating a new folder for the database to be backuped to
        File direct = new File(Environment.getExternalStorageDirectory() + "/Exam Creator");

        if(!direct.exists())
        {
            if(direct.mkdir())
            {
                //directory is created;
            }

        }
    }

    @Override
    //Botão voltar
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        {
            if(id == android.R.id.home){
                this.finish();
            }
        }
        return  super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent intentMainActivity = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intentMainActivity);
        finish();
    }
    //Criando Toolbar
    protected void CriarToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Configuração Geral");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){
        BtnExportar = (Button) this.findViewById(R.id.BtnExportar);

    }

    protected void ExportarBancos (View view) {
        Permissao(Manifest.permission.READ_EXTERNAL_STORAGE, PERMISSAO_LEITURA);
        exportarDatabase(this);

    }

    @SuppressWarnings("resource")
    private void exportarDatabase(Context ctx){
        File backupDB = null;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String currentDBPath = "//data//" + ctx.getPackageName()
                        + "//databases//" + DataBase.NOME_BASE_DE_DADOS + "" ;
                File currentDB = new File(data, currentDBPath);
                backupDB = new File(sd, DataBase.NOME_BASE_DE_DADOS);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    System.out.println("------------------------------entrou envio");
                    src.close();
                    dst.close();
                }
            } else {
                System.out.println("----------------ENTROU AQUI 2");
            }

        } catch (Exception e) {
            System.out.println("----------------ERRO:" + e);
        }

        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("*/*");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{});

            //Random r = new Random();

            String strBase = "Teste";

            //if (edtBaseServ.getText().toString().trim().length() > 0) {
            //    strBase = edtBaseServ.getText().toString();
            //}

            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Banco de dados: "+ strBase);
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(backupDB));
            ctx.startActivity(Intent.createChooser(emailIntent, "Exportar banco de dados"));
        }catch(Exception e){
            System.out.println("----------------------Erro Aqui:"+e.getStackTrace());
        }

    }

    /*private void exportarDatabase() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "PackageName"
                        + "//databases//" + DataBase.NOME_BASE_DE_DADOS +"";
                String backupDBPath  = "/BackupFolder/"+DataBase.NOME_BASE_DE_DADOS+"";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }*/


    private void Permissao (String permissao, int codigo){
        if (ContextCompat.checkSelfPermission(this,permissao)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissao},codigo);
        }else{
        }
    }

    public void resultadoPermissao(int codigo, @NonNull String[] permissao, @NonNull int[] resultado){
        switch (codigo){
            case PERMISSAO_LEITURA:
                if(resultado.length>0&&resultado[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permissão de leitura a diretório está autorizada",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Permissão negado", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
