package com.jmksolutions.appfinanceiro.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jmksolutions.appfinanceiro.Util.DataBase;
import com.jmksolutions.appfinanceiro.Model.LoginModel;

import java.util.ArrayList;
import java.util.List;

public class LoginRepository {
    DataBase database;

    public LoginRepository(Context context){

        database =  new DataBase(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param loginModel
     */
    public void Salvar(LoginModel loginModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("login",       loginModel.getLogin());
        contentValues.put("senha",       loginModel.getSenha());
        contentValues.put("sexo",       loginModel.getSexo());
        contentValues.put("dt_nascimento", loginModel.getDt_nascimento());
        contentValues.put("est_civil",loginModel.getEst_civil());
        contentValues.put("endereco",loginModel.getEndereco());
        contentValues.put("ativo",      loginModel.getAtivo());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        database.GetConexaoDataBase().insert("tlogin",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param loginModel
     */
    public void Atualizar(LoginModel loginModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("login",       loginModel.getLogin());
        contentValues.put("senha",       loginModel.getSenha());
        contentValues.put("sexo",       loginModel.getSexo());
        contentValues.put("dt_nascimento", loginModel.getDt_nascimento());
        contentValues.put("est_civil",loginModel.getEst_civil());
        contentValues.put("endereco",loginModel.getEndereco());
        contentValues.put("ativo",      loginModel.getAtivo());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        database.GetConexaoDataBase().update("tlogin", contentValues, "id = ?", new String[]{Integer.toString(loginModel.getId())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return database.GetConexaoDataBase().delete("tlogin","id = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public LoginModel GetLogin(int codigo){


        Cursor cursor =  database.GetConexaoDataBase().rawQuery("SELECT * FROM tlogin WHERE id_pessoa= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        LoginModel loginModel =  new LoginModel();

        //ADICIONANDO OS DADOS DA PESSOA
        loginModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
        loginModel.setLogin(cursor.getString(cursor.getColumnIndex("login")));
        loginModel.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        loginModel.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
        loginModel.setDt_nascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
        loginModel.setEst_civil(cursor.getString(cursor.getColumnIndex("est_civil")));
        loginModel.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
        loginModel.setAtivo((byte)cursor.getShort(cursor.getColumnIndex("ativo")));

        //RETORNANDO A PESSOA
        return loginModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<LoginModel> SelecionarTodos(){

        List<LoginModel> logins = new ArrayList<LoginModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,           ");
        stringBuilderQuery.append("        login,        ");
        stringBuilderQuery.append("        senha,        ");
        stringBuilderQuery.append("        sexo,         ");
        stringBuilderQuery.append("        dt_nascimento,");
        stringBuilderQuery.append("        est_civil,    ");
        stringBuilderQuery.append("        endereco,     ");
        stringBuilderQuery.append("        ativo         ");
        stringBuilderQuery.append("  FROM  tlogin        ");
        stringBuilderQuery.append(" ORDER BY 1           ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        SQLiteDatabase DB = database.getWritableDatabase();
        Cursor cursor = DB.rawQuery(stringBuilderQuery.toString(), null);
        try{
            if(cursor.moveToFirst()){
                do {
                    LoginModel loginModel;
                    /* CRIANDO UMA NOVA PESSOAS */
                    loginModel = new LoginModel();

                    //ADICIONANDO OS DADOS DA PESSOA
                    loginModel.setId(cursor.getInt(0));
                    loginModel.setLogin(cursor.getString(1));
                /*loginModel.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                loginModel.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
                loginModel.setDt_nascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
                loginModel.setEst_civil(cursor.getString(cursor.getColumnIndex("est_civil")));
                loginModel.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                loginModel.setAtivo((byte)cursor.getShort(cursor.getColumnIndex("ativo")));*/


                    //ADICIONANDO UMA PESSOA NA LISTA
                    logins.add(loginModel);

                }while (cursor.moveToNext());
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        /*PSICIONA O CURSOR NO PRIMEIRO REGISTRO*/

        return logins;

    }
}
