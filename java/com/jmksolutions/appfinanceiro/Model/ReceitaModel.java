package com.jmksolutions.appfinanceiro.Model;

public class ReceitaModel {
    private String  codigo;
    private String  descricao;
    private Long id_rec;
    private String  vl_rec;
    private String  dt_rec;
    private String  desc_rec;
    private Integer  ID_CONTA;
    private String  desc_conta;

    public Integer getID_CONTA()    { return ID_CONTA; }
    public String  getDt_rec()      { return dt_rec;   }
    public String  getVl_rec()      { return vl_rec;   }
    public Long    getId_rec()      { return id_rec;   }
    public String  getdesc_rec()    { return desc_rec; }
    public String  getdesc_conta()  { return desc_conta; }


    public void setID_CONTA(Integer ID_CONTA)     { this.ID_CONTA = ID_CONTA; }
    public void setDt_rec(String dt_rec)          { this.dt_rec = dt_rec; }
    public void setVl_rec(String vl_rec)          { this.vl_rec = vl_rec; }
    public void setId_rec(Long id_rec)            { this.id_rec = id_rec; }
    public void setDesc_rec(String desc_rec)      { this.desc_rec = desc_rec; }
    public void setDesc_conta(String desc_conta)  { this.desc_conta = desc_conta; }

    /*RETORNA A DESCRICAO NO COMPONENTE SPINNER */
    @Override
    public String toString() {
        return this.descricao;
    }

    /*public ReceitaModel(){

    }
    public ReceitaModel(String codigo, String descricao) {

        this.codigo = codigo;
        this.descricao = descricao;

    }*/
}
