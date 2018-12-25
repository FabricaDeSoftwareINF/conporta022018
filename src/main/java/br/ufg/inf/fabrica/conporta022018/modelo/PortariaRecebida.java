package br.ufg.inf.fabrica.conporta022018.modelo;

public class PortariaRecebida {
    private String idPortariaRecebida;
    private boolean isCancelada;

    public PortariaRecebida(String idPortariaRecebida, boolean isCancelada){
        this.idPortariaRecebida = idPortariaRecebida;
        this.isCancelada = isCancelada;

    }

    public String getIdPortariaRecebida() {
        return idPortariaRecebida;
    }

    public void setIdPortariaRecebida(String idPortariaRecebida) {
        this.idPortariaRecebida = idPortariaRecebida;
    }

    public boolean isIsCancelada() {
        return isCancelada;
    }

    public void setIsCancelada(boolean isCancelada) {
        this.isCancelada = isCancelada;
    }
  
}
