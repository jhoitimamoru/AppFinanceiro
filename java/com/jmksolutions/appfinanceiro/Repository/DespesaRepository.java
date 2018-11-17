package com.jmksolutions.appfinanceiro.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jmksolutions.appfinanceiro.Model.DespesaModel;
import com.jmksolutions.appfinanceiro.Util.DataBase;

import java.util.ArrayList;
import java.util.List;

public class DespesaRepository {

    DataBase database;

    public DespesaRepository(Context context){

        database =  new DataBase(context);
    }
    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param despesaModel
     */
    public void Salvar(DespesaModel despesaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("vl_pag",        despesaModel.getVl_pag());
        contentValues.put("desc_pag",      despesaModel.getdesc_pag());
        contentValues.put("dt_desp",       despesaModel.getDt_desp());
        contentValues.put("id_conta",      despesaModel.getID_CONTA());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        database.GetConexaoDataBase().insert("TPAG",null,contentValues);

    }

    /***
     * CONSULTA A DESPESA CADASTRADA PELO CÓDIGO
     * @param codPag
     * @return
     */
    public DespesaModel GetDespesa(int  codPag){

        String selectQuery = "SELECT A.ID_PAG, A.VL_PAG, A.DESC_PAG, A.DT_DESP, " +
                "A.ID_CONTA, B.NOME_CONTA FROM TPAG A, TTIPO_CONTA B " +
                "WHERE A.ID_CONTA = B.ID_CONTA "+
                "AND ID_PAG = " + codPag + " ";

        Cursor cursor = database.GetConexaoDataBase().rawQuery(selectQuery,null);
        cursor.moveToFirst();
        DespesaModel despesaModel = new DespesaModel();

        despesaModel.setId_pag(Long.parseLong(cursor.getString(0)));//INT
        despesaModel.setVl_pag(cursor.getString(1));//String
        despesaModel.setDesc_pag(cursor.getString(2));//String
        despesaModel.setDt_desp(cursor.getString(3));//String
        despesaModel.setID_CONTA(Integer.parseInt(cursor.getString(4)));//String
        despesaModel.setDesc_conta(cursor.getString(5));//String

        return   despesaModel;
    }

    /***
     * ATUALIZA UM NOVO REGISTRO NA BASE DE DADOS
     * @param despesaModel
     */
    public void Atualizar(DespesaModel despesaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/

        contentValues.put("vl_pag",        despesaModel.getVl_pag());
        contentValues.put("desc_pag",      despesaModel.getdesc_pag());
        contentValues.put("dt_desp",        despesaModel.getDt_desp());
        contentValues.put("id_conta",      despesaModel.getID_CONTA());
        Long ID_PAG = despesaModel.getId_pag();
        System.out.println("AQUUIIIIIIII"+ID_PAG);
        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        database.GetConexaoDataBase().update("TPAG",contentValues,"ID_PAG = ?", new String[]{Long.toString(ID_PAG)});

    }

    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return database.GetConexaoDataBase().delete("tpag","id_pag = ?", new String[]{Integer.toString(codigo)});
    }


    public List<DespesaModel> SelecionarTodos(String campoFiltro){
        campoFiltro = campoFiltro.trim();
        List<DespesaModel> despesas = new ArrayList<DespesaModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT a.id_pag,                 ");
        stringBuilderQuery.append("        a.vl_pag,                 ");
        stringBuilderQuery.append("        a.desc_pag,               ");
        stringBuilderQuery.append("        a.dt_desp,                ");
        stringBuilderQuery.append("        a.id_conta,               ");
        stringBuilderQuery.append("        b.nome_conta              ");
        stringBuilderQuery.append("  FROM  tpag a, ttipo_conta b     ");
        stringBuilderQuery.append(" WHERE  a.id_conta = b.id_conta   ");

        if(campoFiltro.trim().length()> 0){
            stringBuilderQuery.append(" AND ( a.vl_pag like '%"+campoFiltro+"%' OR ");
            stringBuilderQuery.append(" a.desc_pag like '%"+campoFiltro+"%' OR     ");
            stringBuilderQuery.append(" a.dt_desp  like '%"+campoFiltro+"%' OR     ");
            stringBuilderQuery.append(" b.nome_conta  like'%"+campoFiltro+"%' )   ");
        }
        stringBuilderQuery.append(" ORDER BY dt_desp                 ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        SQLiteDatabase DB = database.getWritableDatabase();
        Cursor cursor = DB.rawQuery(stringBuilderQuery.toString(), null);
        try{
            if(cursor.moveToFirst()){
                do {
                    DespesaModel despesaModel;
                    /* CRIANDO UMA NOVA PESSOAS */
                    despesaModel = new DespesaModel();

                    //ADICIONANDO OS DADOS DA PESSOA
                    despesaModel.setId_pag(cursor.getLong(0));
                    despesaModel.setVl_pag(cursor.getString(1));
                    despesaModel.setDesc_pag(cursor.getString(2));
                    despesaModel.setDt_desp(cursor.getString(3));
                    despesaModel.setID_CONTA(cursor.getInt(4));
                    despesaModel.setDesc_conta(cursor.getString(5));

                    //ADICIONANDO UMA PESSOA NA LISTA
                    despesas.add(despesaModel);
                }while (cursor.moveToNext());
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        /*PSICIONA O CURSOR NO PRIMEIRO REGISTRO*/

        return despesas;

    }

    //Retorna Unico titulo
    public DespesaModel getUnicoPag(long codPag) {
        DespesaModel tPag = new DespesaModel();
        String selectQuery = "SELECT ID_PAG, VL_PAG, DESC_PAG, DT_DESP, " +
                "ID_CONTA FROM TPAG " +
                "WHERE ID_PAG = " + codPag + " ";
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            //Percorre todos os registros
            if (cursor.moveToFirst()) {
                do {
                    tPag.setId_pag(Long.parseLong(cursor.getString(0)));//INT
                    tPag.setVl_pag(cursor.getString(1));//String
                    tPag.setDesc_pag(cursor.getString(2));//String
                    tPag.setDt_desp(cursor.getString(3));//String
                    tPag.setID_CONTA(Integer.parseInt(cursor.getString(4)));//INT
                    //ADICIONANDO UMA PESSOA NA LISTA
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return tPag;
    }


}
