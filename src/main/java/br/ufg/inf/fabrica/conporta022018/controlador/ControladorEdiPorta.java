package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

public class ControladorEdiPorta {

    private Portaria portaria;
    private PortariaDAO portariaDAO;

    public ControladorEdiPorta(){
        portariaDAO = new PortariaDAO();
    }

    public void editarPortaria(Integer idPortaria){
        this.portaria = PortariaDAO.buscar(idPortaria);

        // Verifica se retornou informações
        if(this.portaria && this.verificarStatus()){
            this.gerarFormulario();
        }else{
            // Mostra mensagem de erro
        }

    }

    public boolean verificarStatus(){
        if (portaria.getStatus() != PortariaStatus.Proposta) {
            return false;
        }

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
