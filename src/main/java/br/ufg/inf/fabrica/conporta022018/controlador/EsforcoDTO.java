package main.java.br.ufg.inf.fabrica.conporta022018.controlador;

import java.awt.List;

class EsforcoDTO {

    private List designados = new List<EsforcoIndividual>();
    private int qtdHoras = 0;
    private int qtdPortarias = 0;
    private int index;

    public addDesignados(String nome){
        EsforcoIndividual pessoa = new EsforcoIndividual();
        pessoa.setNome(nome);
        pessoa.setQtdHoras(0);
        pessoa.setQtdPortarias(0);
        designados.add(pessoa);
    }

    public int buscaDesignado(String nome){
        this.index = 0;
        while(!(designados(index).getNome()).equals(nome)){
            index++;
        }

        return index;
    }

    public modifyQtdHoras(int horas, int index){
        designados(index).setQtdHoras(horas);
    }

    public modifyQtdPortarias(int portarias, int index){
        designados(index).setQtdPortarias(portarias);
    }

    public List<EsforcoIndividual> getDesignados(){
        return designados;
    }
    
}