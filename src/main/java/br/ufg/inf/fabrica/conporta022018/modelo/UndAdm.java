/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.conporta022018.modelo;



/**
 *
 * @author Breno de Melo Gomes
 */

public class UndAdm {
    
    private String siglaUndAdm;
    private int minInat;
    private String nomeUnd;
    private Integer tipoUnd;
    private String ultPort;
    private Integer anoPort; 
    private Integer ultNunExped; 
    private Integer ultNunProp; 

    public String getSiglaUndAdm() {
        return siglaUndAdm;
    }

    public void setSiglaUndAdm(String siglaUndAdm) {
        this.siglaUndAdm = siglaUndAdm;
    }

    public int getMinInat() {
        return minInat;
    }

    public void setMinInat(int minInat) {
        this.minInat = minInat;
    }

    public String getNomeUnd() {
        return nomeUnd;
    }

    public void setNomeUnd(String nomeUnd) {
        this.nomeUnd = nomeUnd;
    }

    public Integer getTipoUnd() {
        return tipoUnd;
    }

    public void setTipoUnd(Integer tipoUnd) {
        this.tipoUnd = tipoUnd;
    }

    public String getUltPort() {
        return ultPort;
    }

    public void setUltPort(String ultPort) {
        this.ultPort = ultPort;
    }

    public Integer getAnoPort() {
        return anoPort;
    }

    public void setAnoPort(Integer anoPort) {
        this.anoPort = anoPort;
    }

    public Integer getUltNunExped() {
        return ultNunExped;
    }

    public void setUltNunExped(Integer ultNunExped) {
        this.ultNunExped = ultNunExped;
    }

    public Integer getUltNunProp() {
        return ultNunProp;
    }

    public void setUltNunProp(Integer ultNunProp) {
        this.ultNunProp = ultNunProp;
    }
   
}
