package com.jmksolutions.appfinanceiro.Model;

public class EstadoCivilModel {

        private String  codigo;
        private String  descricao;

        public String getCodigo() {
            return codigo;
        }
        public String getDescricao() {
            return descricao;
        }

        public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        /*RETORNA A DESCRICAO NO COMPONENTE SPINNER */
        @Override
        public String toString() {
            return this.descricao;
        }

        public EstadoCivilModel(){

        }
        public EstadoCivilModel(String codigo, String descricao) {

            this.codigo = codigo;
            this.descricao = descricao;

        }
}
