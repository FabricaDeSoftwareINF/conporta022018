package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;

public class Recebedora {
    private String idUndAdm;
    private String idPortaria;
    private Date dtCienciaReeb;

    /**
     * @return String return the idUndAdm
     */
    public String getIdUndAdm() {
        return idUndAdm;
    }

    /**
     * @param idUndAdm the idUndAdm to set
     */
    public void setIdUndAdm(String idUndAdm) {
        this.idUndAdm = idUndAdm;
    }

    /**
     * @return String return the idPortaria
     */
    public String getIdPortaria() {
        return idPortaria;
    }

    /**
     * @param idPortaria the idPortaria to set
     */
    public void setIdPortaria(String idPortaria) {
        this.idPortaria = idPortaria;
    }

    /**
     * @return Date return the dtCienciaReeb
     */
    public Date getDtCienciaReeb() {
        return dtCienciaReeb;
    }

    /**
     * @param dtCienciaReeb the dtCienciaReeb to set
     */
    public void setDtCienciaReeb(Date dtCienciaReeb) {
        this.dtCienciaReeb = dtCienciaReeb;
    }

}