package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;
import javax.persistence.*;

/**
 * Modelo da Entidade de DESIGNADO.
 *
 * @author Edionay Aguiar
 * @since 1.0
 */

@Entity
@Table
public class Designado extends ModeloAbstrato{

    private static final long serialVersionUID = 1L;

    private Date dtCienciaDesig;
    private String descrFuncDesig;
    private int horasDefFuncDesig;
    private int horasExecFuncDesig;

    @ManyToOne
    private Pessoa designado;
    private FuncaoDesig tipFuncDesig;

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

    public Pessoa getDesignado() {
        return designado;
    }

    public void setDesignado(Pessoa designado) {
        this.designado = designado;
    }

    public FuncaoDesig getTipFuncDesig() {
        return tipFuncDesig;
    }

    public void setTipFuncDesig(FuncaoDesig tipFuncDesig) {
        this.tipFuncDesig = tipFuncDesig;
    }
}