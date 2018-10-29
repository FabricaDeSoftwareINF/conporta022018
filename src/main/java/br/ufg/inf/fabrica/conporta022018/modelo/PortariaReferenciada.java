package br.ufg.inf.fabrica.conporta022018.modelo;

public class PortariaReferenciada {
    private String idPortariaReferencia;
    private String idPortariaReferenciada;
    private boolean ehCancelamento;

    /**
     * @return String return the idPortariaReferencia
     */
    public String getIdPortariaReferencia() {
        return idPortariaReferencia;
    }

    /**
     * @param idPortariaReferencia the idPortariaReferencia to set
     */
    public void setIdPortariaReferencia(String idPortariaReferencia) {
        this.idPortariaReferencia = idPortariaReferencia;
    }

    /**
     * @return String return the idPortariaReferenciada
     */
    public String getIdPortariaReferenciada() {
        return idPortariaReferenciada;
    }

    /**
     * @param idPortariaReferenciada the idPortariaReferenciada to set
     */
    public void setIdPortariaReferenciada(String idPortariaReferenciada) {
        this.idPortariaReferenciada = idPortariaReferenciada;
    }

    /**
     * @return boolean return the ehCancelamento
     */
    public boolean isEhCancelamento() {
        return ehCancelamento;
    }

    /**
     * @param ehCancelamento the ehCancelamento to set
     */
    public void setEhCancelamento(boolean ehCancelamento) {
        this.ehCancelamento = ehCancelamento;
    }

}