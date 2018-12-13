package br.ufg.inf.fabrica.conporta022018.modelo;

class EsforcoIndividual {

    private String nome;
    private int qtdHoras = 0;
    private int qtdPortarias = 0;

    public setnome(String nome){
        this.nome = nome;
    }

    public String getnome(){
        return nome;
    }

    public setQtdHoras(int qtdHoras){
        this.qtdHoras = this.qtdHoras + qtdHoras;
    }

    public int getQtdHoras(){
        return qtdHoras;
    }

    public setQtdPortarias(int qtdPortarias){
        this.qtdPortarias = this.qtdPortarias + qtdPortarias;
    }

    public int getQtdPortarias(){
        return qtdPortarias;
    }
}