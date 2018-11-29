package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

public class ControladorEdiPorta {

    private Portaria portaria;
    private PortariaDAO portariaDAO;

    public ControladorEdiPorta(){
        portariaDAO = new PortariaDAO();
    }

    public void editarPortaria(Integer idPortaria){

    }

    public boolean verificarStatus(){
        return true;
    }

    public void gerarFormulario(){
        // Renderiza a view
    }

    public void designarPessoas(){

    }

    public void referenciarPortarias(){

    }

    public void salvar(){

    }

    public void validarInformacoes(){

    }

    public void gerarBlob(){

    }
}
