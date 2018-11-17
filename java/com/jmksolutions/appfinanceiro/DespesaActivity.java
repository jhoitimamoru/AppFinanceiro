package com.jmksolutions.appfinanceiro;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.jmksolutions.appfinanceiro.Model.DespesaModel;
import com.jmksolutions.appfinanceiro.Model.TipoContaModel;
import com.jmksolutions.appfinanceiro.Repository.DespesaRepository;
import com.jmksolutions.appfinanceiro.Util.DataBase;
import com.jmksolutions.appfinanceiro.Util.Uteis;
import com.jmksolutions.appfinanceiro.View.ListViewDespesa;

import java.util.Calendar;
import java.util.List;

public class DespesaActivity extends AppCompatActivity {
    //Int para manipular o ID do campo do spinner.
    public  int campoConta = 0;
    //Int para manupular o Id da conta
    public  int IdContaParam;
    //Int para manupular o Id da Receita
    public  int IDDespesaParam = 0;
    //Menu de pesquisa
    private MenuItem menu_pesquisar;
    //String para Manipular o label do botão Salvar/Atualiza
    public  String BtnSalvaAtualiza;

    EditText    EdtValor;
    EditText    EdtDataDesp;
    EditText    EdtDescricao;
    Spinner     spinnerConta;
    Button      BtnSalvar;
    Button      BtnVoltar;
    //CRIA POPUP COM O CALENDÁRIO
    DatePickerDialog datePickerDialogDataNascimento;
    List<TipoContaModel> tipoContaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        //Criação do Toolbar
        this.CriarToolbar();

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        this.CriarComponentes();

        //Criação do Eventos
        this.CriarEventos();

        //Verifica se foi chamado de alguma tela
        this.verificaRegistro();
        //Carrega o spinner
        this.carregarSpinnerConta();

        BtnSalvaAtualiza = BtnSalvar.getText().toString();
    }
    //Comando para habilitar a pesquisa no toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
        // super.onCreateOptionsMenu(menu);
    }

    @Override
    //Botão voltar e Pesquisar
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            case R.id.pesquisa:
                //this.finish();
                Intent intentMainActivity = new Intent(getApplicationContext(), ListViewDespesa.class);
                startActivity(intentMainActivity);
        }
        return  super.onOptionsItemSelected(item);
    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){
        EdtValor                = (EditText)    this.findViewById(R.id.EdtValor);
        EdtDataDesp             = (EditText)    this.findViewById(R.id.EdtDataDesp);
        EdtDescricao            = (EditText)    this.findViewById(R.id.EdtDescricao);
        BtnSalvar               = (Button)      this.findViewById(R.id.BtnSalvar);
        BtnVoltar               = (Button)      this.findViewById(R.id.BtnVoltar);
        spinnerConta            = (Spinner)     this.findViewById(R.id.spinnerConta);
    }

    protected void CriarToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lançamento de despesa");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Chamada do calendário
    protected void CriarEventos(){

        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual   = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual   = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual   = calendarDataAtual.get(Calendar.DAY_OF_MONTH);
        DataBase db = new DataBase(getApplicationContext());
        tipoContaList = db.getAllTipoConta();
        //MONTANDO O OBJETO DE DATA PARA PREENCHER O CAMPOS QUANDO  FOR SELECIONADO
        //FORMATO DD/MM/YYYY
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialogDataNascimento = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

                //FORMATANDO O MÊS COM DOIS DÍGITOS
                String mes = (String.valueOf((mesSelecionado )).length() == 1 ? "0" + (mesSelecionado + 1 ): String.valueOf(mesSelecionado+1));
                String dia = (String.valueOf((diaSelecionado )).length() == 1 ? "0" + (diaSelecionado + 1 ): String.valueOf(diaSelecionado));

                EdtDataDesp.setText(dia + "/" + mes + "/" + anoSelecionado);

            }

        }, anoAtual, mesAtual, diaAtual);


        //CRIANDO EVENTO NO CAMPO DE DATA PARA ABRIR A POPUP
        EdtDataDesp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                datePickerDialogDataNascimento.show();
            }
        });


        //CRIANDO EVENTO NO CAMPO DE DATA PARA ABRIR A POPUP
        EdtDataDesp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                datePickerDialogDataNascimento.show();

            }
        });

        spinnerConta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view2, int position, long id) {
                TipoContaModel tipoContaModel = new TipoContaModel();
                tipoContaModel = tipoContaList.get((int)id);
                campoConta  =  tipoContaModel.getId_conta();
                if(campoConta == 99){
                    Intent intentContaActivity = new Intent(getApplicationContext(), ContaActivity.class);
                    startActivity(intentContaActivity);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

                Intent intentMenuActivity = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intentMenuActivity);
                finish();
            }
        });

    }
    //Inicio Spinner fazendo select do banco
    protected void carregarSpinnerConta(){
        // database handler
        DataBase db = new DataBase(getApplicationContext());

        // Spinner Drop down elements
        List<TipoContaModel> listaTipoConta = db.getAllTipoConta();
        // Creating adapter for spinner
        ArrayAdapter<TipoContaModel> dataAdapter = new ArrayAdapter<TipoContaModel>(this,
                android.R.layout.simple_spinner_item, listaTipoConta);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerConta.setAdapter(dataAdapter);

        BtnSalvaAtualiza = (String) BtnSalvar.getText();

        if (IdContaParam != -1) {
            //SpinnerAdapter adapter = spinnerConta.getAdapter();

            int index = 0;
            for (int i = 0; i < listaTipoConta.size(); i++) {

                //System.out.println("xxxxxxxxxxxxxxxxxxxxxxx\nConta\n"+nomeConta+"\nitem\n"+listaTipoConta.get(i));
                //if (listaTipoConta.get(i).toString().trim().equals(nomeConta.trim())){
                if (listaTipoConta.get(i).getId_conta() == IdContaParam) {
                    TipoContaModel listaTipoContaTemp = new TipoContaModel();
                    listaTipoContaTemp = listaTipoConta.get(i);
                    index = i;
                    break;

                }
            }
            spinnerConta.setSelection(index);
        }
    }

    protected void Salvar_onClick (){

            if(EdtValor.getText().toString().trim().equals("")){

                Uteis.Alert(this, this.getString(R.string.valor));

                EdtValor.requestFocus();
            }
            else if(EdtDataDesp.getText().toString().trim().equals("")){

                Uteis.Alert(this, this.getString(R.string.data));

                EdtDataDesp.requestFocus();
            }
            else if(EdtDescricao.getText().toString().trim().equals("")) {

                Uteis.Alert(this, this.getString(R.string.descricao));

                EdtDescricao.requestFocus();
            }
            else  if(spinnerConta.getSelectedItem().toString().equals("Selecione uma conta")){

                    Uteis.Alert(this, this.getString(R.string.tipoconta));

                    spinnerConta.requestFocus();
            }else {
                if(BtnSalvaAtualiza.equals("SALVAR") ){
                    //Se o botão for Salvar
                    DespesaModel despesaModel = new DespesaModel();
                    despesaModel.setVl_pag(EdtValor.getText().toString().trim());
                    despesaModel.setDt_desp(EdtDataDesp.getText().toString().trim());
                    despesaModel.setDesc_pag(EdtDescricao.getText().toString().trim());
                    despesaModel.setID_CONTA(campoConta);

                    new DespesaRepository(this).Salvar(despesaModel);
                    Uteis.Alert(this, this.getString(R.string.registro_salvo_sucesso));
                    EdtValor.requestFocus();
                    limparCampos();
                }else if(BtnSalvaAtualiza.equals("ATUALIZAR")){
                    //Se o botão for atualizar
                    DespesaModel despesaModel = new DespesaModel();
                    despesaModel.setVl_pag(EdtValor.getText().toString().trim());
                    despesaModel.setDt_desp(EdtDataDesp.getText().toString().trim());
                    despesaModel.setDesc_pag(EdtDescricao.getText().toString().trim());
                    despesaModel.setID_CONTA(campoConta);
                    despesaModel.setId_pag(Long.valueOf(IDDespesaParam));

                    try {
                        new DespesaRepository(this).Atualizar(despesaModel);
                    }catch(Exception e){
                        Log.e("LOG DE ERRO","MENSAGEM :",e);
                        System.out.println("LOG DE ERRO");
                    }

                    AlertDialog.Builder Alerta = new AlertDialog.Builder(DespesaActivity.this);
                    Alerta.setMessage("Registro atualizado com sucesso!").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = Alerta.create();
                    Alerta.setTitle("Informativo!");
                    Alerta.show();
                }
            }
    }

    protected void onResume() {
        super.onResume();
        this.carregarSpinnerConta();
        this.CriarEventos();
        BtnSalvaAtualiza = BtnSalvar.getText().toString();
    }
    protected  void limparCampos(){
        EdtValor.setText(null);
        EdtDataDesp.setText(null);
        EdtDescricao.setText(null);
    }


    //Responsavel por alimentar as colunas para alteração
    protected void carregarCampos(Integer CodPag){
        DespesaRepository despesaRepository = new DespesaRepository(this);

        DespesaModel despesaModel = despesaRepository.GetDespesa(CodPag);
        //Setando o valor
        EdtValor.setText(despesaModel.getVl_pag());
        //Setando a data
        EdtDataDesp.setText(despesaModel.getDt_desp());
        //Setando a descrição
        EdtDescricao.setText(despesaModel.getdesc_pag());
        //Passando o Id da conta para o parametro
        IdContaParam =  despesaModel.getID_CONTA();
        //Toast.makeText(getContext(),  "nome da conta"+IdContaParam,Toast.LENGTH_SHORT).show();
        BtnSalvar.setText("ATUALIZAR");
        getSupportActionBar().setTitle("Atualização de despesa");
    }

    protected void verificaRegistro() {

        Bundle b = getIntent().getExtras();
        if (b != null ) {
            IDDespesaParam = b.getInt("ID_PAG");
            carregarCampos(IDDespesaParam);
        }
        //Não há dados
    }

    public Context getContext(){
        return this;
    }


}
