
package br.ufg.inf.fabrica.conporta022018.dto;

import java.util.Date;


public class FiltroDTO {

    private String cpfPes;

    private String siglaUnAdm;

    private Integer anoPortaria;

    private Date inicioVigencia;

    private Date fimVigencia;

    public FiltroDTO() {

    }

    public FiltroDTO(String cpfPes, String siglaUnAdm, Integer anoPortaria, Date inicioVigencia, Date fimVigencia) {
        this.cpfPes = cpfPes;
        this.siglaUnAdm = siglaUnAdm;
        this.anoPortaria = anoPortaria;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
    }

    public String getCpfPes() {
        return cpfPes;
    }

    public void setCpfPes(String cpfPes) {
        this.cpfPes = cpfPes;
    }

    public String getSiglaUnAdm() {
        return siglaUnAdm;
    }

    public void setSiglaUnAdm(String siglaUnAdm) {
        this.siglaUnAdm = siglaUnAdm;
    }

    public Integer getAnoPortaria() {
        return anoPortaria;
    }

    public void setAnoPortaria(Integer anoPortaria) {
        this.anoPortaria = anoPortaria;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public Date getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(Date fimVigencia) {
        this.fimVigencia = fimVigencia;
    }
}
