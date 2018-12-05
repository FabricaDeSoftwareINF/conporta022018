package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.UndAdmDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorProPorta{

    private UndAdmDAO undAdmDAO;
    private PessoaDAO pessoaDAO;
    private PortariaDAO portariaDAO;

    public ControladorProPorta(){
        this.undAdmDAO = new UndAdmDAO();
        this.pessoaDAO = new PessoaDAO();
        this.portariaDAO = new PortariaDAO();
    }

    public List<UndAdm> buscarUndAdm(){
        return this.undAdmDAO.buscarTodos();
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

    private boolean validarCampos(String assunto, Date dtIniVig, String resumo){
        if(assunto.isEmpty() || dtIniVig.toString().isEmpty() || resumo.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public boolean salvar(String assunto, Date dtIniVig, Date dtFimVig, Date dPublicDou, int horasDesig, String resumo, File arqPdf, List<Designado> designados, List<Referencia> referencias, List<Recebedora> recebedoras) throws IOException {
        if(!this.validarCampos(assunto, dtIniVig, resumo)){
            return false;
        }

        Portaria portaria = new Portaria();
        portaria.setAssunto(assunto);
        portaria.setDtIniVig(dtIniVig);
        portaria.setDtFimVig(dtFimVig);
        portaria.setDtPublicDou(dPublicDou);
        portaria.setHorasDesig(horasDesig);
        portaria.setResumo(resumo);
        portaria.setDesignados(designados);
        portaria.setReferencias(referencias);
        portaria.setUndRecebedora(recebedoras);

        if(arqPdf.exists()) {
            byte[] fileContent = Files.readAllBytes(arqPdf.toPath());
            portaria.setArqPdf(fileContent);
        }

        portariaDAO.salvar(portaria);

        return true;
    }
}