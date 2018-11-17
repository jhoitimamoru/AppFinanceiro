package com.jmksolutions.appfinanceiro.View;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jmksolutions.appfinanceiro.CadUserActivity;
import com.jmksolutions.appfinanceiro.MainActivity;
import com.jmksolutions.appfinanceiro.Model.LoginModel;
import com.jmksolutions.appfinanceiro.R;
import com.jmksolutions.appfinanceiro.Repository.LoginRepository;
import com.jmksolutions.appfinanceiro.Util.AdaptersConsulta;

import java.util.List;

    public class ListViewConta extends AppCompatActivity {
        //CRIANDO UM OBJETO DO TIPO ListView PARA RECEBER OS REGISTROS DE UM ADAPTER
        ListView listViewConsulta;
        AdaptersConsulta adaptersConsulta;
        int qntselecionado  =-1;
        private int selected = -1;
        boolean chamouNovaTela =false;

        //CRIANDO O BOTÃO VOLTAR PARA RETORNAR PARA A TELA COM AS OPÇÕES
        Button btnVoltar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_consulta);
            //VINCULANDO O BOTÃO VOLTAR DA TELA AO OBJETO CRIADO
            btnVoltar    = (Button)this.findViewById(R.id.btnVoltar);
            //CHAMA O MÉTODO QUE CARREGA AS PESSOAS CADASTRADAS NA BASE DE DADOS
            this.CarregarPessoasCadastradas();

            //CHAMA O MÉTODO QUE CRIA O EVENTO PARA O BOTÃO VOLTAR
            this.CriarEvento();
            listViewConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    if (qntselecionado == arg3) {
                        if(chamouNovaTela == false) {
                            chamouNovaTela = true;

                            qntselecionado = -1;
                            Intent intent = new Intent(getContext(), CadUserActivity.class);
                            Bundle b = new Bundle();
                            b.putInt("ID",(int)arg3);
                            intent.putExtras(b);

                            startActivity(intent);
                        }
                    }
                    selected = position;
                    adaptersConsulta.select(position);
                    qntselecionado = (int)arg3;
                }
            });

            listViewConsulta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    //idDeletar = id;
                    //confirmaDeletar();
                    LoginRepository loginRepository = new LoginRepository(getContext());
                    loginRepository.Excluir((int)id);
                    CarregarPessoasCadastradas();
                    return true;
                }
            });
        }

        @Override
        public void onResume(){
            super.onResume();
            chamouNovaTela =false;
            qntselecionado  =-1;
            CarregarPessoasCadastradas();
        }

        //MÉTODO QUE CRIA EVENTO PARA O BOTÃO VOLTAR
        protected  void CriarEvento(){

            btnVoltar.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    //REDIRECIONA PARA A TELA PRINCIPAL
                    Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentMainActivity);

                    //FINALIZA A ATIVIDADE ATUAL
                    finish();
                }
            });
        }

        //MÉTODO QUE CONSULTA AS PESSOAS CADASTRADAS
        protected  void CarregarPessoasCadastradas(){
            qntselecionado  =-1;
            LoginRepository loginRepository =  new LoginRepository(this);

            //BUSCA AS PESSOAS CADASTRADAS
            List<LoginModel> login = loginRepository.SelecionarTodos();
            Toast.makeText(this, "TEXTO"+login.size(),Toast.LENGTH_SHORT).show();
            //VINCULANDO O LISTVIEW DA TELA AO OBJETO CRIADO
            listViewConsulta = (ListView)this.findViewById(R.id.listViewConsulta);
            adaptersConsulta = new AdaptersConsulta(login,this);
            listViewConsulta.setAdapter(adaptersConsulta);


        }


        public Context getContext(){
            return this;
        }
    }

