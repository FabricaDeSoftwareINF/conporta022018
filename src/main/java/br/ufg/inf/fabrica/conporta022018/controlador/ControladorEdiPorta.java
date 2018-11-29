package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.UndAdm;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;

import java.util.Date;
import java.util.List;

public class ControladorEdiPorta {

    private Portaria portaria;
    private PortariaDAO portariaDAO;
    private PessoaDAO pessoaDAO;
    private UndAdmDAO undAdmDAO;

    public ControladorEdiPorta(){
        portariaDAO = new PortariaDAO();
    }

    public Portaria editarPortaria(Long idPortaria){
        Portaria portaria = this.portariaDAO.buscar(idPortaria);
        return portaria;
    }

    public List<Pessoa> buscarPessoas(){
        List<Pessoa> pessoas = this.pessoaDAO.buscarTodos();
        return pessoas;
    }

    public List<Portaria> buscarPortarias() {
        List<Portaria> portarias = portariaDAO.buscarTodos();
        return portarias;
    }

    public boolean salvar(Portaria portaria){
        if(!validarCampos(portaria.getAssunto(), portaria.getDtIniVig(), portaria.getResumo()))
            return false;

        this.portariaDAO.salvar(portaria);
        return true;
    }

    public boolean validarCampos(String assunto, Date dtIniVig, String resumo){
        if(assunto.isEmpty())
            return false;
        if(resumo.isEmpty())
            return false;

        Date today = new Date();
        if(today.after(dtIniVig))
            return false;

        return true;
    }

    public List<UndAdm> buscarUndAdm() {
        List<UndAdm> lista = undAdmDAO.buscarTodos();
        
        return lista;
    }
}
