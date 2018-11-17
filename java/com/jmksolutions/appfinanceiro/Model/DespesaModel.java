package com.jmksolutions.appfinanceiro.Model;

public class DespesaModel {

    private String  codigo;
    private String  descricao;
    private Long    id_pag;
    private String  vl_pag;
    private String  dt_desp;
    private String  desc_pag;
    private Integer ID_CONTA;
    private String  desc_conta;

    public Integer  getID_CONTA()  { return ID_CONTA; }
    public String   getDt_desp()   { return dt_desp; }
    public String   getVl_pag()    { return vl_pag; }
    public Long     getId_pag()    { return id_pag; }
    public String   getdesc_pag()  { return desc_pag; }
    public String   getdesc_conta(){ return desc_conta; }


    public void setID_CONTA  (Integer ID_CONTA)  { this.ID_CONTA = ID_CONTA; }
    public void setDt_desp   (String dt_desp)    { this.dt_desp = dt_desp; }
    public void setVl_pag    (String vl_pag)     { this.vl_pag = vl_pag; }
    public void setId_pag    (Long id_pag)       { this.id_pag = id_pag; }
    public void setDesc_pag  (String desc_pag)   { this.desc_pag = desc_pag; }
    public void setDesc_conta(String desc_conta)  { this.desc_conta = desc_conta; }

    /*RETORNA A DESCRICAO NO COMPONENTE SPINNER */
    @Override
    public String toString() {
        return this.descricao;
    }

    public DespesaModel(){

    }
    public DespesaModel(String codigo, String descricao) {

        this.codigo = codigo;
        this.descricao = descricao;

    }
}
