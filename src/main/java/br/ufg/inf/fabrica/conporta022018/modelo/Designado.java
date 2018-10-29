package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import java.util.List;

public class Designado {
    private String idPortaria;
    private Date dtCienciaDesig;
    private String descrFuncDesig;
    private Integer horasDefFuncDesig;
    private Integer horasExecFuncDesig;

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
     * @return Date return the dtCienciaDesig
     */
    public Date getDtCienciaDesig() {
        return dtCienciaDesig;
    }

    /**
     * @param dtCienciaDesig the dtCienciaDesig to set
     */
    public void setDtCienciaDesig(Date dtCienciaDesig) {
        this.dtCienciaDesig = dtCienciaDesig;
    }

    /**
     * @return String return the descrFuncDesig
     */
    public String getDescrFuncDesig() {
        return descrFuncDesig;
    }

    /**
     * @param descrFuncDesig the descrFuncDesig to set
     */
    public void setDescrFuncDesig(String descrFuncDesig) {
        this.descrFuncDesig = descrFuncDesig;
    }

    /**
     * @return Integer return the horasDefFuncDesig
     */
    public Integer getHorasDefFuncDesig() {
        return horasDefFuncDesig;
    }

    /**
     * @param horasDefFuncDesig the horasDefFuncDesig to set
     */
    public void setHorasDefFuncDesig(Integer horasDefFuncDesig) {
        this.horasDefFuncDesig = horasDefFuncDesig;
    }

    /**
     * @return Integer return the horasExecFuncDesig
     */
    public Integer getHorasExecFuncDesig() {
        return horasExecFuncDesig;
    }

    /**
     * @param horasExecFuncDesig the horasExecFuncDesig to set
     */
    public void setHorasExecFuncDesig(Integer horasExecFuncDesig) {
        this.horasExecFuncDesig = horasExecFuncDesig;
    }

}