package com.jmksolutions.appfinanceiro.Model;

public class TipoContaModel {

    private Integer  codigo;
    private String  descricao;
    private Integer id_conta;
    private String  nome_conta;
    private String  saldo_conta;
    private String  tipo_conta;

    public Integer getCodigo()     {return codigo; }
    public String getDescricao()  {return descricao; }
    public Integer   getId_conta()   {return id_conta;}
    public String getNome_conta() {return nome_conta;}
    public String getSaldo_conta(){return saldo_conta;}
    public String getTipo_conta() {return tipo_conta;}


    public void setCodigo(Integer codigo)          { this.codigo = codigo; }
    public void setDescricao(String descricao)     { this.descricao = descricao; }
    public void setId_conta(Integer id_conta)         { this.id_conta = id_conta; }
    public void setNome_conta(String nome_conta)   { this.nome_conta = nome_conta; }
    public void setSaldo_conta(String saldo_conta) { this.saldo_conta = saldo_conta; }
    public void setTipo_conta(String tipo_conta)   { this.tipo_conta = tipo_conta; }




    /*RETORNA A DESCRICAO NO COMPONENTE SPINNER */
    @Override
    public String toString() {
        return this.descricao;
    }

    public TipoContaModel(){

    }
    public TipoContaModel(Integer codigo, String descricao) {

        this.codigo = codigo;
        this.descricao = descricao;

    }
}
