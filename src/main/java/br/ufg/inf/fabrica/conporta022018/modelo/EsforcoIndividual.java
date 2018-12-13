package br.ufg.inf.fabrica.conporta022018.modelo;

import java.util.Date;


class EsforcoIndividual {

    private String name;
    private int qtdHoras;
    private int qtdPortarias;

    public setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public setQtdHoras(int qtdHoras){
        this.qtdHoras = qtdHoras;
    }

    public int getQtdHoras(){
        return qtdHoras;
    }

    public setQtdPortarias(int qtdPortarias){
        this.qtdPortarias = qtdPortarias;
    }

    public int getQtdPortarias(){
        return qtdPortarias;
    }
}