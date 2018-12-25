package br.ufg.inf.fabrica.conporta022018.modelo;

public enum TipoUnd {

    CURSO("Curso"),
    UNIDADE_ACADEMICA("Unidade Acadêmica"),
    UNIDADE_GESTORA("Unidade Gestora"),
    CONSELHO("Conselho"),
    UNIDADE_EXTERNA("Unidade Externa");

    public String tipo;

    TipoUnd(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

}
