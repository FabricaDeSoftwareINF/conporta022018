package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.List;
import javax.persistence.*;

@Entity
@Table
public class UndAdm extends ModeloAbstrato{

public class UndAdm extends ModeloAbstrato {
    private static final long serialVersionUID = 1L;

    @Column
    private String siglaUnAdm;

    @Column
    private int minInat;

    @Column
    private String nomeUnd;

    @Column
    private int tipoUnd;

    @Column
    private String ultPort;

    @Column
    private int anoPort;

    @Column
    private int ultNumExped;

    @Column
    private int ultNumProp;

    @Column
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<UndAdm> subordinadas;

    /**
     * @return String return the siglaUnAdm
     */
    public String getSiglaUnAdm() {
        return siglaUnAdm;
    }

    /**
     * @param siglaUnAdm the siglaUnAdm to set
     */
    public void setSiglaUnAdm(String siglaUnAdm) {
        this.siglaUnAdm = siglaUnAdm;
    }

    /**
     * @return Integer return the minInat
     */
    public int getMinInat() {
        return minInat;
    }

    /**
     * @param minInat the minInat to set
     */
    public void setMinInat(int minInat) {
        this.minInat = minInat;
    }

    /**
     * @return String return the nomeUnd
     */
    public String getNomeUnd() {
        return nomeUnd;
    }

    /**
     * @param nomeUnd the nomeUnd to set
     */
    public void setNomeUnd(String nomeUnd) {
        this.nomeUnd = nomeUnd;
    }

    /**
     * @return Integer return the tipoUnd
     */
    public int getTipoUnd() {
        return tipoUnd;
    }

    /**
     * @param tipoUnd the tipoUnd to set
     */
    public void setTipoUnd(int tipoUnd) {
        this.tipoUnd = tipoUnd;
    }

    /**
     * @return String return the ultPort
     */
    public String getUltPort() {
        return ultPort;
    }

    /**
     * @param ultPort the ultPort to set
     */
    public void setUltPort(String ultPort) {
        this.ultPort = ultPort;
    }

    /**
     * @return Integer return the anoPort
     */
    public int getAnoPort() {
        return anoPort;
    }

    /**
     * @param anoPort the anoPort to set
     */
    public void setAnoPort(int anoPort) {
        this.anoPort = anoPort;
    }

    /**
     * @return Integer return the ultNumExped
     */
    public int getUltNumExped() {
        return ultNumExped;
    }

    /**
     * @param ultNumExped the ultNumExped to set
     */
    public void setUltNumExped(int ultNumExped) {
        this.ultNumExped = ultNumExped;
    }

    /**
     * @return Integer return the ultNumProp
     */
    public int getUltNumProp() {
        return ultNumProp;
    }

    /**
     * @param ultNumProp the ultNumProp to set
     */
    public void setUltNumProp(int ultNumProp) {
        this.ultNumProp = ultNumProp;
    }

    /**
     * @return List<UndAdm> return the subordinadas
     */
    public List<UndAdm> getSubordinadas() {
        return subordinadas;
    }

    /**
     * @param subordinadas the subordinadas to set
     */
    public void setSubordinadas(List<UndAdm> subordinadas) {
        this.subordinadas = subordinadas;
    }

}