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
        // Busca a portaria com esse id
        Portaria portaria = this.portariaDAO.buscar(idPortaria);

        // Verifica se encontrou a portaria
        try{
            Long id = portaria.getId();
        }catch (Exception e){
            throw new IllegalArgumentException("Portaria não encontrada");
        }

        // Verifica se é uma portaria Proposta
        if(!PortariaStatus.Proposta.equals(portaria.getStatus())){
            throw new IllegalArgumentException("A portaria deve estar com o status Proposta");
        }

        return portaria;
    }

    public List<Pessoa> buscarPessoas(){
        // Busca apenas as pessoas ativas
        String query = "SELECT p FROM Pessoa p WHERE p.ehUsuAtivo = :ativo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ativo", true);


        return this.pessoaDAO.pesquisarJPQLCustomizada(query, params);
    }

    public List<Portaria> buscarPortarias(){
        // Busca apenas as portaria ativas
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
        return !assunto.isEmpty() && !resumo.isEmpty();
    }

    public List<UndAdm> buscarUndAdm() {
        return undAdmDAO.buscarTodos();
    }
}
