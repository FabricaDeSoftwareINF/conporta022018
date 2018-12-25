package br.ufg.inf.fabrica.conporta022018.modelo;


public class PortariaExpedida  {
    private String idPortariaExpedida;
    private boolean isCancelada;

    public PortariaExpedida(String idPortariaExpedida, boolean isCancelada){
        this.idPortariaExpedida = idPortariaExpedida;
        this.isCancelada = isCancelada;

    }

    public String getIdPortariaExpedida() {
        return idPortariaExpedida;
    }

    public void setIdPortariaExpedida(String idPortariaExpedida) {
        this.idPortariaExpedida = idPortariaExpedida;
    }

    public boolean isIsCancelada() {
        return isCancelada;
    }

    public void setIsCancelada(boolean isCancelada) {
        this.isCancelada = isCancelada;
    }

    
}
