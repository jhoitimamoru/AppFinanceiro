package com.jmksolutions.appfinanceiro.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jmksolutions.appfinanceiro.Model.LoginModel;
import com.jmksolutions.appfinanceiro.Model.ReceitaModel;
import com.jmksolutions.appfinanceiro.Model.TipoContaModel;
import com.jmksolutions.appfinanceiro.Util.DataBase;

public class TipoContaRepository {
    DataBase database;

    public TipoContaRepository(Context context){

        database =  new DataBase(context);
    }
    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param tipoContaModel
     */
    public void Salvar(TipoContaModel tipoContaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("nome_conta",       tipoContaModel.getNome_conta());
        contentValues.put("saldo_conta",      tipoContaModel.getSaldo_conta());
        contentValues.put("tipo_conta",       tipoContaModel.getTipo_conta());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        database.GetConexaoDataBase().insert("ttipo_conta",null,contentValues);

    }

}
