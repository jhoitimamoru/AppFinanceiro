package com.jmksolutions.appfinanceiro.Util;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jmksolutions.appfinanceiro.Model.TipoContaModel;
import com.jmksolutions.appfinanceiro.Util.Script;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper{

    //NOME DA BASE DE DADOS
    public static final String NOME_BASE_DE_DADOS   = "Financeiro.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;
    //Parametrizando a tabela de listview para spinner
    private static final String TABELA_TTIPO_CONTA = "TTIPO_CONTA";
    //CONSTRUTOR
    public DataBase(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        Script script = new Script();
        db.execSQL(script.getDROP_PAG());
        db.execSQL(script.getDROP_REC());

        db.execSQL(script.getCREATE_TLOGIN());
        db.execSQL(script.getCREATE_TTIPO_CONTA());
        db.execSQL(script.getCREATE_TPAG());
        db.execSQL(script.getCREATE_TREC());

    }

    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS TLOGIN");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
    //Checando se já existe este usuário cadastrado.
    public boolean CheckLogin(String Login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from tlogin where login=?", new String[]{Login});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //Checando se existe cadastro do usuário
    public boolean CheckAuth(String login,String senha){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from tlogin where login =? and senha=?", new String[]{login,senha});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    //Chamando A lista do Spinner do DespesaActivity
    public List<TipoContaModel> getAllTipoConta(){
        List<TipoContaModel> tipoContaList = new ArrayList<TipoContaModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABELA_TTIPO_CONTA + " ORDER BY NOME_CONTA";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        TipoContaModel tipoContaModelTemp = new TipoContaModel();
        tipoContaModelTemp.setId_conta(-1);
        tipoContaModelTemp.setDescricao("Selecione uma conta");
        tipoContaList.add(tipoContaModelTemp);
        try{
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TipoContaModel tipoContaModel = new TipoContaModel();
                tipoContaModel.setId_conta(Integer.parseInt(cursor.getString(0)));
                tipoContaModel.setDescricao(cursor.getString(1));
                tipoContaList.add(tipoContaModel);
            }
                 while (cursor.moveToNext());
                }
            }finally {
            if(cursor !=null) {
                cursor.close();
            }
            TipoContaModel tipoContaModel2 = new TipoContaModel();
            tipoContaModel2.setId_conta(99);
            tipoContaModel2.setDescricao("+ Cadastrar uma Conta");
            tipoContaList.add(tipoContaModel2);
        }
        // returning lables
        return tipoContaList;
    }

    public void exibeTabela(String nomeTabela, String descricao){

        System.out.println("*******************"+nomeTabela+"---"+descricao+"***********************\n");
        String retDados = "";
        String selectQuery = "SELECT * FROM "+nomeTabela+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // Percorre todos os registros
            if (cursor.moveToFirst()) {
                do {
                    System.out.print("********************");
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        System.out.print("" + cursor.getString(i) + "|");
                    }
                    System.out.print("********************\n");
                } while (cursor.moveToNext());
            }
        }finally{
            if(cursor != null)
                cursor.close();
        }
    }

}


