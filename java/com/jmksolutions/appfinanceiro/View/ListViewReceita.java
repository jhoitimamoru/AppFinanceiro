package com.jmksolutions.appfinanceiro.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jmksolutions.appfinanceiro.CadUserActivity;
import com.jmksolutions.appfinanceiro.MainActivity;
import com.jmksolutions.appfinanceiro.Model.ReceitaModel;
import com.jmksolutions.appfinanceiro.R;
import com.jmksolutions.appfinanceiro.ReceitaActivity;
import com.jmksolutions.appfinanceiro.Repository.ReceitaRepository;
import com.jmksolutions.appfinanceiro.Util.AdaptersReceita;

import java.util.List;

public class ListViewReceita extends AppCompatActivity {
    //CRIANDO UM OBJETO DO TIPO ListView PARA RECEBER OS REGISTROS DE UM ADAPTER
    ListView listViewConsulta;
    AdaptersReceita adaptersReceita;
    int count  =-1;
    boolean chamouNovaTela =false;
    private long idDeletar;
    private String campoFiltro;

    //CRIANDO O BOTÃO VOLTAR PARA RETORNAR PARA A TELA COM AS OPÇÕES
    Button btnVoltar;
    EditText EdtFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        //VINCULANDO O LISTVIEW DA TELA AO OBJETO CRIADO
        listViewConsulta = (ListView)this.findViewById(R.id.listViewConsulta);
        //VINCULANDO O BOTÃO VOLTAR DA TELA AO OBJETO CRIADO
        btnVoltar    = (Button)this.findViewById(R.id.btnVoltar);
        //VINCULANDO O BOTÃO FILTRO DA TELA AO OBJETO CRIADO
        EdtFiltro    = (EditText)this.findViewById(R.id.EdtFiltro);

        //CHAMA O MÉTODO QUE CARREGA AS PESSOAS CADASTRADAS NA BASE DE DADOS
        this.CarregarReceitas();
        //Criar toolbar
        this.CriarToolbar();
        //CHAMA O MÉTODO QUE CRIA O EVENTO PARA O BOTÃO VOLTAR
        this.CriarEvento();
        listViewConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (count == arg3) {
                    if(chamouNovaTela == false) {
                        chamouNovaTela = true;

                        count = -1;

                        Intent intent = new Intent(getContext(), ReceitaActivity.class);
                        idDeletar = arg3;
                        Bundle b = new Bundle();
                        b.putInt("ID_REC",(int)idDeletar);


                        intent.putExtra("ID_REC",(int)idDeletar);
                        startActivity(intent);
                    }
                }

                adaptersReceita.select(position);
                count = (int)arg3;
            }
        });

        listViewConsulta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                idDeletar = id;
                confirmaDeletar();
                return true;
            }
        });


        EdtFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CarregarReceitas();
            }
        });
    }
    //Criação do toolbar
    protected void CriarToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lista de receitas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    //Botão voltar e Pesquisar
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        chamouNovaTela =false;
        count  =-1;
        CarregarReceitas();
    }

    //MÉTODO QUE CRIA EVENTO PARA O BOTÃO VOLTAR
    protected  void CriarEvento(){

        btnVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    //MÉTODO QUE CONSULTA AS PESSOAS CADASTRADAS
    protected  void CarregarReceitas(){
        count = -1;
        campoFiltro = String.valueOf(EdtFiltro.getText());
        ReceitaRepository receitaRepository=  new ReceitaRepository(this);

        //BUSCA AS PESSOAS CADASTRADAS
        List<ReceitaModel> receita = receitaRepository.SelecionarTodos(campoFiltro);
        adaptersReceita = new AdaptersReceita(receita,this);
        listViewConsulta.setAdapter(adaptersReceita);

    }

    public Context getContext() {
        return this;
    }

    private void confirmaDeletar(){

        AlertDialog.Builder Alerta = new AlertDialog.Builder(ListViewReceita.this);
        Alerta.setMessage("Você deseja excluir este registro?").setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReceitaRepository receitaRepository= new ReceitaRepository(getContext());
                        receitaRepository.getUnicoRec(idDeletar);
                        receitaRepository.Excluir((int)idDeletar);
                        Toast.makeText(getContext(),  "Registro Excluido com sucesso!!",Toast.LENGTH_SHORT).show();
                        CarregarReceitas();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        CarregarReceitas();
                    }
                });
        AlertDialog alertDialog = Alerta.create();
        Alerta.setTitle("Alerta!!");
        Alerta.show();
    }


}


