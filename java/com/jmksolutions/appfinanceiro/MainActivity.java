package com.jmksolutions.appfinanceiro;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmksolutions.appfinanceiro.Util.DataBase;

public class MainActivity extends AppCompatActivity {
    EditText EdtLogin,EdtSenha;
    TextView TvLogin;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBase(this);
        this.CriarComponentes();
        this.CriarEventos();
        db.exibeTabela("TREC","TABELA RECEITA");
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder Alerta = new AlertDialog.Builder(MainActivity.this);
        Alerta.setMessage("Você deseja fechar o aplicativo?").setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = Alerta.create();
        Alerta.setTitle("Alerta!!");
        Alerta.show();
    }

    protected void ChamarCadastro (View view){
        Intent intentRedirecionar;
        intentRedirecionar = new Intent(this, CadUserActivity.class);
        startActivity(intentRedirecionar);
        //finish();
    }

    protected  void CriarComponentes(){
        EdtLogin    = (EditText)    this.findViewById(R.id.EdtLogin);
        EdtSenha    = (EditText)    this.findViewById(R.id.EdtSenha);
        TvLogin     = (TextView)    this.findViewById(R.id.TvLogin);
    }

    protected  void CriarEventos(){

        TvLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                login_OnCLick();
            }
        });
    }
        protected void login_OnCLick(){
            String login = EdtLogin.getText().toString();
            String senha = EdtSenha.getText().toString();
            Boolean CheckAuth = db.CheckAuth(login,senha);
                if(CheckAuth==true){
                    Intent intentRedirecionar;
                    intentRedirecionar = new Intent(this, MenuActivity.class);
                    startActivity(intentRedirecionar);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Usuário ou Senha incorreto.",Toast.LENGTH_SHORT).show();
        }




}