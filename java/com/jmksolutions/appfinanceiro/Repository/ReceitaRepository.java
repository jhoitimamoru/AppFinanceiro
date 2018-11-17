package com.jmksolutions.appfinanceiro.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.tv.TvRecordingClient;
import android.widget.Toast;

import com.jmksolutions.appfinanceiro.Model.LoginModel;
import com.jmksolutions.appfinanceiro.Model.ReceitaModel;
import com.jmksolutions.appfinanceiro.Util.DataBase;

import java.util.ArrayList;
import java.util.List;

public class ReceitaRepository {


    DataBase database;

    public ReceitaRepository(Context context){

        database =  new DataBase(context);
    }
    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param receitaModel
     */
    public void Salvar(ReceitaModel receitaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("vl_rec",        receitaModel.getVl_rec());
        contentValues.put("desc_rec",      receitaModel.getdesc_rec());
        contentValues.put("dt_rec",        receitaModel.getDt_rec());
        contentValues.put("id_conta",      receitaModel.getID_CONTA());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        database.GetConexaoDataBase().insert("TREC",null,contentValues);

    }

    public List<ReceitaModel> SelecionarTodos(String campoFiltro){
        campoFiltro = campoFiltro.trim();
        List<ReceitaModel> receitas = new ArrayList<ReceitaModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT a.id_rec,                 ");
        stringBuilderQuery.append("        a.vl_rec,                 ");
        stringBuilderQuery.append("        a.desc_rec,               ");
        stringBuilderQuery.append("        a.dt_rec,                 ");
        stringBuilderQuery.append("        a.id_conta,               ");
        stringBuilderQuery.append("        b.nome_conta              ");
        stringBuilderQuery.append("  FROM  trec a, ttipo_conta b     ");
        stringBuilderQuery.append(" WHERE  a.id_conta = b.id_conta   ");


        if(campoFiltro.trim().length()> 0){
            stringBuilderQuery.append(" AND ( a.vl_rec like '%"+campoFiltro+"%' OR ");
            stringBuilderQuery.append(" a.desc_rec like '%"+campoFiltro+"%' OR     ");
            stringBuilderQuery.append(" a.dt_rec  like '%"+campoFiltro+"%' OR     ");
            stringBuilderQuery.append(" b.nome_conta  like'%"+campoFiltro+"%' )   ");
        }
        stringBuilderQuery.append(" ORDER BY DATE(dt_rec) desc                ");
        System.out.println("AQUIIIII"+stringBuilderQuery);

        //CONSULTANDO OS REGISTROS CADASTRADOS
        SQLiteDatabase DB = database.getWritableDatabase();
        Cursor cursor = DB.rawQuery(stringBuilderQuery.toString(), null);
        try{
            if(cursor.moveToFirst()){
                do {
                    ReceitaModel receitaModel;
                    /* CRIANDO UMA NOVA PESSOAS */
                    receitaModel = new ReceitaModel();

                    //ADICIONANDO OS DADOS DA PESSOA
                    receitaModel.setId_rec(cursor.getLong(0));
                    receitaModel.setVl_rec(cursor.getString(1));
                    receitaModel.setDesc_rec(cursor.getString(2));
                    receitaModel.setDt_rec(cursor.getString(3));
                    receitaModel.setID_CONTA(cursor.getInt(4));
                    receitaModel.setDesc_conta(cursor.getString(5));


                    //ADICIONANDO UMA PESSOA NA LISTA
                    receitas.add(receitaModel);
                }while (cursor.moveToNext());
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        /*PSICIONA O CURSOR NO PRIMEIRO REGISTRO*/

        return receitas;

    }

    //Retorna Unico titulo
    public ReceitaModel getUnicoRec(long codRec) {
        ReceitaModel tRec = new ReceitaModel();
        String selectQuery = "SELECT ID_REC, VL_REC, DESC_REC, DT_REC, " +
                             "ID_CONTA FROM TREC " +
                             "WHERE ID_REC = " + codRec + " ";
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            //Percorre todos os registros
            if (cursor.moveToFirst()) {
                do {
                    tRec.setId_rec(Long.parseLong(cursor.getString(0)));//INT
                    tRec.setVl_rec(cursor.getString(1));//String
                    tRec.setDesc_rec(cursor.getString(2));//String
                    tRec.setDt_rec(cursor.getString(3));//String
                    tRec.setID_CONTA(Integer.parseInt(cursor.getString(4)));//INT
                    //ADICIONANDO UMA PESSOA NA LISTA
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return tRec;
    }


    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return database.GetConexaoDataBase().delete("trec","id_rec = ?", new String[]{Integer.toString(codigo)});
    }
    /***
     * CONSULTA A RECEITA CADASTRADA PELO CÓDIGO
     * @param codRec
     * @return
     */
    public ReceitaModel GetReceita(int  codRec){

        String selectQuery = "SELECT A.ID_REC, A.VL_REC, A.DESC_REC, A.DT_REC, " +
                             "A.ID_CONTA, B.NOME_CONTA FROM TREC A, TTIPO_CONTA B " +
                             "WHERE A.ID_CONTA = B.ID_CONTA "+
                             "AND ID_REC = " + codRec + " ";

        Cursor cursor = database.GetConexaoDataBase().rawQuery(selectQuery,null);
        cursor.moveToFirst();
        ReceitaModel receitaModel = new ReceitaModel();

            receitaModel.setId_rec(Long.parseLong(cursor.getString(0)));//INT
            receitaModel.setVl_rec(cursor.getString(1));//String
            receitaModel.setDesc_rec(cursor.getString(2));//String
            receitaModel.setDt_rec(cursor.getString(3));//String
            receitaModel.setID_CONTA(Integer.parseInt(cursor.getString(4)));//String
            receitaModel.setDesc_conta(cursor.getString(5));//String

        return   receitaModel;
    }

    /***
     * ATUALIZA UM NOVO REGISTRO NA BASE DE DADOS
     * @param receitaModel
     */
    public void Atualizar(ReceitaModel receitaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/

        contentValues.put("vl_rec",        receitaModel.getVl_rec());
        contentValues.put("desc_rec",      receitaModel.getdesc_rec());
        contentValues.put("dt_rec",        receitaModel.getDt_rec());
        contentValues.put("id_conta",      receitaModel.getID_CONTA());
        Long ID_REC = receitaModel.getId_rec();
        System.out.println("AQUUIIIIIIII"+ID_REC);
        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        database.GetConexaoDataBase().update("TREC",contentValues,"ID_REC = ?", new String[]{Long.toString(ID_REC)});

    }

}
