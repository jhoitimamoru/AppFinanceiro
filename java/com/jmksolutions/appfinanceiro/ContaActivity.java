package com.jmksolutions.appfinanceiro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.jmksolutions.appfinanceiro.Model.TipoContaModel;
import com.jmksolutions.appfinanceiro.Repository.TipoContaRepository;
import com.jmksolutions.appfinanceiro.Util.Uteis;
import com.jmksolutions.appfinanceiro.View.ConsultaActivity;
import com.jmksolutions.appfinanceiro.View.ListViewConta;

import java.util.ArrayList;
import java.util.List;

public class ContaActivity extends AppCompatActivity {
    EditText EdtNomeConta;
    EditText EdtSaldo;
    Spinner  spinnerTipoConta;
    Button   BtnVoltar;
    Button   BtnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        //Criação do Toolbar
        this.CriarToolbar();
        //Criando Componentes
        this.CriarComponentes();
        //Listando o tipos de Contas
        this.CarregaTipoConta();
        //Criando Eventos dos botões
        this.CriarEventos();
    }

    protected void CriarToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastro de conta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //Comando para habilitar a pesquisa no toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
        // super.onCreateOptionsMenu(menu);
    }

    @Override
    //Botão voltar
    public boolean onOptionsItemSelected(MenuItem item) {
       /* int id = item.getItemId();
        {
            if(id == android.R.id.home){
                this.finish();
            }
        }*/
       switch (item.getItemId()){
           case android.R.id.home:
               this.finish();
           case R.id.pesquisa:
               //this.finish();
               Intent intentMainActivity = new Intent(getApplicationContext(), ListViewConta.class);
               try {
                   startActivity(intentMainActivity);
               }catch(Exception e){
                   System.out.println("----------------------Erro Aqui:"+e);
               }
               finish();
           }
        return  super.onOptionsItemSelected(item);
    }

    protected  void CarregaTipoConta(){

        ArrayAdapter<TipoContaModel> arrayAdapter;

        List<TipoContaModel> itens =  new ArrayList<TipoContaModel>();

        itens.add(new TipoContaModel(0, "Conta Crrente"));
        itens.add(new TipoContaModel(1, "Dinheiro"));
        itens.add(new TipoContaModel(2, "Conta Poupança"));
        itens.add(new TipoContaModel(3, "Investimentos"));
        itens.add(new TipoContaModel(4, "Outros"));

        arrayAdapter = new ArrayAdapter<TipoContaModel>(this, android.R.layout.simple_spinner_item,itens);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoConta.setAdapter(arrayAdapter);

    }

    protected void CriarComponentes(){
        EdtNomeConta       = (EditText)     this.findViewById(R.id.EdtNomeConta);
        EdtSaldo           = (EditText)     this.findViewById(R.id.EdtSaldo);
        spinnerTipoConta   = (Spinner)      this.findViewById(R.id.spinnerTipoConta);
        BtnSalvar          = (Button)       this.findViewById(R.id.BtnSalvar);
        BtnVoltar          = (Button)       this.findViewById(R.id.BtnVoltar);
    }

    protected void CriarEventos(){

        BtnSalvar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Salvar_onClick();
            }
        });


        //CRIANDO EVENTO NO BOTÃO VOLTAR
        BtnVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                /*Intent intentMenuActivity = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intentMenuActivity);*/
                finish();
            }
        });
    }

    protected  void Salvar_onClick(){
        if(EdtNomeConta.getText().toString().trim().equals("")){

            Uteis.Alert(this, this.getString(R.string.nomeConta));

            EdtNomeConta.requestFocus();
        }
        else if(EdtSaldo.getText().toString().trim().equals("")){

            Uteis.Alert(this, this.getString(R.string.vlSaldoIni));

            EdtSaldo.requestFocus();
        }
        else{

            TipoContaModel tipoContaModel = new TipoContaModel();

            tipoContaModel.setNome_conta(EdtNomeConta.getText().toString().trim());

            tipoContaModel.setSaldo_conta(EdtSaldo.getText().toString().trim());

            tipoContaModel.setTipo_conta(spinnerTipoConta.getSelectedItem().toString().trim());

            new TipoContaRepository(this).Salvar(tipoContaModel);

            Uteis.Alert(this,this.getString(R.string.registro_salvo_sucesso));
            EdtNomeConta.requestFocus();
            LimparCampos();
        }

    }

    protected void LimparCampos(){
        EdtNomeConta.setText(null);
        EdtSaldo.setText(null);
    }
}
