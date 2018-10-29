package br.ufg.inf.fabrica.conporta022018.modelo;

public class PortariaReferenciada {
    private String idPortariaReferencia;
    private String idPortariaReferenciada;
    private boolean ehCancelamento;

    public PortariaReferenciada(String idPortariaReferencia, String idPortariaReferenciada, boolean ehCancelamento){
        this.idPortariaReferencia = idPortariaReferencia;
        this.idPortariaReferenciada = idPortariaReferenciada;
        this.ehCancelamento = ehCancelamento;
    }

    public String getIdPortariaReferencia() {
        return idPortariaReferencia;
    }

    public void setIdPortariaReferencia(String idPortariaReferencia) {
        this.idPortariaReferencia = idPortariaReferencia;
    }

    public String getIdPortariaReferenciada() {
        return idPortariaReferenciada;
    }

    public void setIdPortariaReferenciada(String idPortariaReferenciada) {
        this.idPortariaReferenciada = idPortariaReferenciada;
    }

    public boolean getEhCancelamento() {
        return ehCancelamento;
    }

    public void setEhCancelamento(boolean ehCancelamento) {
        this.ehCancelamento = ehCancelamento;
    }
}
