package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class UndAdm extends ModeloAbstrato{

    private String siglaUnAdm;
    private Integer minInat;
    private String nomeUnd;
    private Integer tipoUnd;
    private String ultPort;
    private Integer anoPort;
    private Integer ultNumExped;
    private Integer ultNumProp;

    @OneToMany
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
    public Integer getMinInat() {
        return minInat;
    }

    /**
     * @param minInat the minInat to set
     */
    public void setMinInat(Integer minInat) {
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
    public Integer getTipoUnd() {
        return tipoUnd;
    }

    /**
     * @param tipoUnd the tipoUnd to set
     */
    public void setTipoUnd(Integer tipoUnd) {
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
    public Integer getAnoPort() {
        return anoPort;
    }

    /**
     * @param anoPort the anoPort to set
     */
    public void setAnoPort(Integer anoPort) {
        this.anoPort = anoPort;
    }

    /**
     * @return Integer return the ultNumExped
     */
    public Integer getUltNumExped() {
        return ultNumExped;
    }

    /**
     * @param ultNumExped the ultNumExped to set
     */
    public void setUltNumExped(Integer ultNumExped) {
        this.ultNumExped = ultNumExped;
    }

    /**
     * @return Integer return the ultNumProp
     */
    public Integer getUltNumProp() {
        return ultNumProp;
    }

    /**
     * @param ultNumProp the ultNumProp to set
     */
    public void setUltNumProp(Integer ultNumProp) {
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