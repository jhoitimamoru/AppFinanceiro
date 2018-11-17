package com.jmksolutions.appfinanceiro.Util;

public class Script {

    private String CREATE_TLOGIN;
    private String CREATE_TTIPO_CONTA;
    private String CREATE_TPAG;
    private String CREATE_TREC;
    private String DROP_PAG;
    private String DROP_REC;


    public Script(){
        this.CREATE_TLOGIN = "CREATE TABLE IF NOT EXISTS TLOGIN  ( "+
                             "ID             INTEGER PRIMARY KEY AUTOINCREMENT,"+
                             "LOGIN          VARCHAR2(20)    NOT NULL,             "+
                             "SENHA          VARCHAR2(20)    NOT NULL,             "+
                             "ENDERECO       VARCHAR2(40)    NOT NULL,             "+
                             "SEXO           VARCHAR2(1)     NOT NULL,             "+
                             "DT_NASCIMENTO  VARCHAR2(20)    NOT NULL,             "+
                             "EST_CIVIL      VARCHAR2(15)    NOT NULL,             "+
                             "ATIVO          INTEGER         NOT NULL              "+
                ");";
        this.CREATE_TTIPO_CONTA = "CREATE TABLE IF NOT EXISTS TTIPO_CONTA        ("+
                                  "ID_CONTA     INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                  "NOME_CONTA   VARCHAR2(20)     NOT NULL        ,"+
                                  "SALDO_CONTA  NUMERIC(10,2)                    ,"+
                                  "TIPO_CONTA   VARCHAR2(20)                      "+
                ")";
        this.CREATE_TPAG = "CREATE TABLE IF NOT EXISTS TPAG                         ("+
                           "ID_PAG    INTEGER PRIMARY KEY AUTOINCREMENT             ,"+
                           "VL_PAG    NUMERIC(10,2)   NOT NULL                      ,"+
                           "DESC_PAG  VARCHAR2(30)   NOT NULL                       ,"+
                           "DT_DESP   DATE            NOT NULL                      ,"+
                           "ID_CONTA  INTERGER       NOT NULL                       ,"+
                           "FOREIGN KEY (ID_CONTA)  REFERENCES TTIPO_CONTA(ID_CONTA) "+
                ")";
        this.CREATE_TREC = "CREATE TABLE IF NOT EXISTS TREC                          ("+
                            "ID_REC    INTEGER PRIMARY KEY AUTOINCREMENT             ,"+
                            "VL_REC    NUMERIC(10,2)   NOT NULL                      ,"+
                            "DESC_REC  VARCHAR2(30)   NOT NULL                       ,"+
                            "DT_REC    DATE            NOT NULL                      ,"+
                            "ID_CONTA  INTERGER       NOT NULL                       ,"+
                            "FOREIGN KEY (ID_CONTA)  REFERENCES TTIPO_CONTA(ID_CONTA) "+
                ")";
        this.DROP_PAG   =" DROP TABLE IF EXISTS TPAG ";
        this.DROP_REC   =" DROP TABLE IF EXISTS TREC ";
    }

    public String getCREATE_TLOGIN() {
        return CREATE_TLOGIN;
    }
    public String getCREATE_TTIPO_CONTA() {
        return CREATE_TTIPO_CONTA;
    }
    public String getCREATE_TPAG() {
        return CREATE_TPAG;
    }
    public String getCREATE_TREC() {
        return CREATE_TREC;
    }
    public String getDROP_PAG() {
        return DROP_PAG;
    }
    public String getDROP_REC() {
        return DROP_REC;
    }
}
