package br.ufg.inf.fabrica.conporta022018.modelo;

public enum PortariaStatus {
    PROPOSTA("Proposta"),
    ATIVA("Ativa"),
    EXPIRADA("Expirada"),
    CANCELADA("Cancelada");

    public String status;

    PortariaStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}