package com.jmksolutions.appfinanceiro;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.jmksolutions.appfinanceiro.Util.Uteis;
import com.jmksolutions.appfinanceiro.Model.EstadoCivilModel;
import com.jmksolutions.appfinanceiro.Model.LoginModel;
import com.jmksolutions.appfinanceiro.Util.DataBase;
import com.jmksolutions.appfinanceiro.Repository.LoginRepository;
public class CadUserActivity extends AppCompatActivity {

    /*COMPONENTES DA TELA*/
    EditText         EdtNome;
    EditText         EdtEndereco;
    EditText         EdtSenha;
    RadioButton      RbMasculino;
    RadioButton      RbFeminino;
    RadioGroup       radioGroupSexo;
    EditText         EdtDataNasci;
    Spinner          spinnerEstadoCivil;
    CheckBox         CbRegistroAtivo;
    Button           BtnSalvar;
    Button           BtnVoltar;


    //CRIA POPUP COM O CALENDÁRIO
    DatePickerDialog datePickerDialogDataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_user);

        // CHAMA O METODO PARA DIZER QUAL A LOCALIZAÇÃO,
        // USADO PARA TRADUZIR OS TEXTOS DO CALENDÁRIO.
        this.Localizacao();

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        this.CriarComponentes();

        //CRIA OS EVENTOS DOS COMPONENTES
        this.CriarEventos();

        //CARREGA AS OPÇÕES DE ESTADO CIVIL
        this.CarregaEstadosCivis();

        Bundle b = getIntent().getExtras();
        int value = 0;
        if (b != null){
            value=b.getInt("ID");

        }
        Toast.makeText(this,"ID DO USUARIO"+value,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        //Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intentMainActivity);
        finish();
    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){
        EdtNome                = (EditText)    this.findViewById(R.id.EdtNome);
        EdtEndereco            = (EditText)    this.findViewById(R.id.EdtEndereco);
        EdtSenha               = (EditText)    this.findViewById(R.id.EdtSenha);
        RbMasculino            = (RadioButton) this.findViewById(R.id.RbMasculino);
        RbFeminino             = (RadioButton) this.findViewById(R.id.RbFeminino);
        radioGroupSexo         = (RadioGroup)  this.findViewById(R.id.radioGroupSexo);
        EdtDataNasci           = (EditText)    this.findViewById(R.id.EdtDataNasci);
        spinnerEstadoCivil     = (Spinner)     this.findViewById(R.id.spinnerEstadoCivil);
        CbRegistroAtivo        = (CheckBox)    this.findViewById(R.id.CbRegistroAtivo);
        BtnSalvar              = (Button)      this.findViewById(R.id.BtnSalvar);
        BtnVoltar              = (Button)      this.findViewById(R.id.BtnVoltar);

    }
    //CRIA OS EVENTOS DOS COMPONENTES
    protected  void CriarEventos(){


        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual   = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual   = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual   = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        //MONTANDO O OBJETO DE DATA PARA PREENCHER O CAMPOS QUANDO  FOR SELECIONADO
        //FORMATO DD/MM/YYYY
        datePickerDialogDataNascimento = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

                //FORMATANDO O MÊS COM DOIS DÍGITOS
                String mes = (String.valueOf((mesSelecionado + 1)).length() == 1 ? "0" + (mesSelecionado + 1 ): String.valueOf(mesSelecionado));
                String dia = (String.valueOf((diaSelecionado + 1)).length() == 1 ? "0" + (diaSelecionado ): String.valueOf(diaSelecionado));
                EdtDataNasci.setText(dia + "/" + mes + "/" + anoSelecionado);

            }

        }, anoAtual, mesAtual, diaAtual);



        //CRIANDO EVENTO NO CAMPO DE DATA PARA ABRIR A POPUP
        EdtDataNasci.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                datePickerDialogDataNascimento.show();
            }
        });




        //CRIANDO EVENTO NO CAMPO DE DATA PARA ABRIR A POPUP
        EdtDataNasci.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                datePickerDialogDataNascimento.show();

            }
        });


        //CRIANDO EVENTO NO BOTÃO SALVAR
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

                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });

    }

    //VALIDA OS CAMPOS E SALVA AS INFORMAÇÕES NO BANCO DE DADOS
    protected  void Salvar_onClick(){

        if(EdtNome.getText().toString().trim().equals("")){

            Uteis.Alert(this, this.getString(R.string.nome_obrigatorio));

            EdtNome.requestFocus();
        }
        else if(EdtEndereco.getText().toString().trim().equals("")){

            Uteis.Alert(this, this.getString(R.string.endereco_obrigatorio));

            EdtEndereco.requestFocus();

        }
        else if(EdtSenha.getText().toString().trim().equals("")){

            Uteis.Alert(this, this.getString(R.string.senha_obrigatorio));

            EdtSenha.requestFocus();
        }

        else if(!RbMasculino.isChecked() && !RbFeminino.isChecked()){

            Uteis.Alert(this, this.getString(R.string.sexo_obrigatorio));
        }
        else if(EdtDataNasci.getText().toString().trim().equals("")){

            Uteis.Alert(this, this.getString(R.string.data_nascimento_obrigatorio));

            EdtDataNasci.requestFocus();

        }
        else{


            /*CRIANDO UM OBJETO PESSOA*/
            LoginModel loginModel = new LoginModel();

            /*SETANDO O VALOR DO CAMPO NOME*/
            loginModel.setLogin(EdtNome.getText().toString().trim());

            /*SETANDO O ENDEREÇO*/
            loginModel.setEndereco(EdtEndereco.getText().toString().trim());

            /*SETANDO A SENHA*/
            loginModel.setSenha(EdtSenha.getText().toString().trim());

            /*SETANDO O SEXO*/
            if(RbMasculino.isChecked())
                loginModel.setSexo("M");
            else
                loginModel.setSexo("F");

            /*SETANDO A DATA DE NASCIMENTO*/
            loginModel.setDt_nascimento(EdtDataNasci.getText().toString().trim());

            /*REALIZANDO UM CAST PARA PEGAR O OBJETO DO ESTADO CIVIL SELECIONADO*/
            EstadoCivilModel estadoCivilModel = (EstadoCivilModel)spinnerEstadoCivil.getSelectedItem();

            /*SETANDO ESTO CIVIL*/
            loginModel.setEst_civil(estadoCivilModel.getCodigo());


            /*SETA O REGISTRO COMO INATIVO*/
            loginModel.setAtivo((byte)0);

            /*SE TIVER SELECIONADO SETA COMO ATIVO*/
            if(CbRegistroAtivo.isChecked())
                loginModel.setAtivo((byte)1);

            /*SALVANDO UM NOVO REGISTRO*/
            new LoginRepository(this).Salvar(loginModel);

            /*MENSAGEM DE SUCESSO!*/
            Uteis.Alert(this,this.getString(R.string.registro_salvo_sucesso));

            LimparCampos();
            EdtNome.requestFocus();

        }


    }

    //LIMPA OS CAMPOS APÓS SALVAR AS INFORMAÇÕES
    protected void LimparCampos(){

        EdtNome.setText(null);
        EdtSenha.setText(null);
        EdtEndereco.setText(null);

        radioGroupSexo.clearCheck();

        EdtDataNasci.setText(null);
        CbRegistroAtivo.setChecked(false);
    }
    //DIZ QUAL A LOCALIZAÇÃO PARA TRADUZIR OS TEXTOS DO CALENDÁRIO.
    protected  void Localizacao(){

        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

    }



    //CARREGA AS OPÇÕES DE ESTADO CIVIL PARA O COMPONENTE SPINNER
    protected  void CarregaEstadosCivis(){

        ArrayAdapter<EstadoCivilModel> arrayAdapter;

        List<EstadoCivilModel> itens =  new ArrayList<EstadoCivilModel>();

        itens.add(new EstadoCivilModel("S", "Solteiro(a)"));
        itens.add(new EstadoCivilModel("C", "Casado(a)"));
        itens.add(new EstadoCivilModel("V", "Viuvo(a)"));
        itens.add(new EstadoCivilModel("D", "Divorciado(a)"));


        arrayAdapter = new ArrayAdapter<EstadoCivilModel>(this, android.R.layout.simple_spinner_item,itens);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerEstadoCivil.setAdapter(arrayAdapter);

    }
}


