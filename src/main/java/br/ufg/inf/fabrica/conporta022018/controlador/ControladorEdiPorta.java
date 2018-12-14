package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;

import java.util.Date;
import java.util.List;

public class ControladorEdiPorta {

    private Portaria portaria;
    private PortariaDAO portariaDAO;
    private PessoaDAO pessoaDAO;
    private UndAdmDAO undAdmDAO;

    public ControladorEdiPorta(){
        this.undAdmDAO = new UndAdmDAO();
        this.pessoaDAO = new PessoaDAO();
        this.portariaDAO = new PortariaDAO();
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
        String query = "SELECT p FROM Portaria p WHERE p.status = :status";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", PortariaStatus.Ativa);

        return this.portariaDAO.pesquisarJPQLCustomizada(query, params);
    }

    public boolean salvar(Portaria portaria) {
        if(!this.validarCampos(portaria.getAssunto(), portaria.getResumo())){
            return false;
        }

        portariaDAO.salvar(portaria);

        return true;
    }

    private boolean validarCampos(String assunto, String resumo){
        if(assunto.isEmpty() || resumo.isEmpty()){
            return false;
        }

        return true;
    }

    public List<UndAdm> buscarUndAdm() {
        List<UndAdm> lista = undAdmDAO.buscarTodos();

        return lista;
    }
}
