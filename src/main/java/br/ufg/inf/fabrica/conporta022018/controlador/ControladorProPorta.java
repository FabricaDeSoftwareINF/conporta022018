package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.*;

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
    private DesignadoDAO designadoDAO;
    private PortariaReferenciadaDAO portariaReferenciadaDAO;
    private RecebedoraDAO recebedoraDAO;

    public ControladorProPorta(){
        this.undAdmDAO = new UndAdmDAO();
        this.pessoaDAO = new PessoaDAO();
        this.portariaDAO = new PortariaDAO();
        this.designadoDAO = new DesignadoDAO();
        this.portariaReferenciadaDAO = new PortariaReferenciadaDAO();
        this.recebedoraDAO = new RecebedoraDAO();
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

    private void validarCampos(Map<String, Object> parametros){

        // Verifica se possui as keys obrigatórias
        if(!parametros.containsKey("assunto") || !parametros.containsKey("dtIniVig") || !parametros.containsKey("resumo") || !parametros.containsKey("expedidora")){
            throw new IllegalArgumentException("assunto, dtIniVig, resumo e expedidora são obrigatórios");
        }

        // Testa primeiro os campos obrigatórios
        if(!(parametros.get("assunto") instanceof String)){
            throw new IllegalArgumentException("O assunto deve ser do tipo String");
        }
        if(((String) parametros.get("assunto")).isEmpty()){
            throw new IllegalArgumentException("O assunto é obrigatório");
        }

        if(!(parametros.get("dtIniVig") instanceof Date)){
            throw new IllegalArgumentException("O dtIniVig deve ser do tipo Date");
        }

        if(!(parametros.get("resumo") instanceof String)){
            throw new IllegalArgumentException("O resumo deve ser do tipo String");
        }
        if(((String) parametros.get("resumo")).isEmpty()){
            throw new IllegalArgumentException("O resumo é obrigatório");
        }
        if(!(parametros.get("expedidora") instanceof UndAdm)){
            throw new IllegalArgumentException("O expedidora deve ser do tipo UndAdm");
        }

        // Verifica se os campos opcionais são do tipo correto
        if(parametros.containsKey("dtFimVig") && !(parametros.get("dtFimVig") instanceof Date)){
            throw new IllegalArgumentException("O dtFimVig deve ser do tipo Date");
        }
        if(parametros.containsKey("dPublicDou") && !(parametros.get("dPublicDou") instanceof Date)){
            throw new IllegalArgumentException("O dPublicDou deve ser do tipo Date");
        }
        if(parametros.containsKey("horasDesig") && !(parametros.get("horasDesig") instanceof Integer)){
            throw new IllegalArgumentException("O horasDesig deve ser do tipo int");
        }
        if(parametros.containsKey("arqPdf") && !(parametros.get("arqPdf") instanceof File)){
            throw new IllegalArgumentException("O arqPdf deve ser do tipo File");
        }
        if(parametros.containsKey("designados")){
            if(!(parametros.get("designados") instanceof List<?>)){
                throw new IllegalArgumentException("O designados deve ser do tipo List<Designado>");
            } else {
                for (Object obj : (List) parametros.get("designados")) {
                    if (!(obj instanceof Designado)) {
                        throw new IllegalArgumentException("O designados deve ser do tipo List<Designado>");
                    }
                }
            }
        }
        if(parametros.containsKey("referencias")){
            if(!(parametros.get("referencias") instanceof List<?>)){
                throw new IllegalArgumentException("O referencias deve ser do tipo List<Referencia>");
            } else {
                for (Object obj : (List) parametros.get("referencias")) {
                    if (!(obj instanceof Referencia)) {
                        throw new IllegalArgumentException("O referencias deve ser do tipo List<referencias>");
                    }
                }
            }
        }
        if(parametros.containsKey("recebedoras")){
            if(!(parametros.get("recebedoras") instanceof List<?>)){
                throw new IllegalArgumentException("O recebedoras deve ser do tipo List<Recebedora>");
            } else {
                for (Object obj : (List) parametros.get("recebedoras")) {
                    if (!(obj instanceof Recebedora)) {
                        throw new IllegalArgumentException("O recebedoras deve ser do tipo List<Recebedora>");
                    }
                }
            }
        }
    }

    public Portaria salvar(Map<String, Object> parametros) throws IOException {

        // Valida os parametros
        validarCampos(parametros);

        Portaria portaria = new Portaria();
        portaria.setAssunto((String) parametros.get("assunto"));
        portaria.setDtIniVig((Date) parametros.get("dtIniVig"));

        if(parametros.containsKey("dtFimVig")){
            portaria.setDtFimVig((Date) parametros.get("dtFimVig"));
        }

        if(parametros.containsKey("dPublicDou")){
            portaria.setDtPublicDou((Date) parametros.get("dPublicDou"));
        }

        if(parametros.containsKey("horasDesig")){
            portaria.setHorasDesig((int) parametros.get("horasDesig"));
        }

        portaria.setResumo((String) parametros.get("resumo"));

        UndAdm expedidora = (UndAdm) parametros.get("expedidora");

        // Abre a transação
        this.portariaDAO.abrirTransacao();

        // Define o seqId com base na UndAdm expedidora
        int seqId = expedidora.getUltNumProp() + 1;
        portaria.setSeqId(seqId);

        // Altera o ultNumProp da UndAdm
        expedidora.setUltNumProp(seqId);
        portaria.setUnidadeExpedidora(expedidora);

        if(parametros.containsKey("designados")) {
            List<Designado> designados = (List<Designado>) parametros.get("designados");
            for (int i = 0; i < designados.size(); i++) {
                // Salva os designados
                designados.set(i, this.designadoDAO.salvar(designados.get(i)));
            }
            portaria.setDesignados(designados);
        }

        if(parametros.containsKey("referencias")) {
            List<Referencia> referencias = (List<Referencia>) parametros.get("referencias");
            for (int i = 0; i < referencias.size(); i++) {
                // Salva as referências
                referencias.set(i, this.portariaReferenciadaDAO.salvar(referencias.get(i)));
            }
            portaria.setReferencias(referencias);
        }

        if(parametros.containsKey("recebedoras")) {
            List<Recebedora> recebedoras = (List<Recebedora>) parametros.get("recebedoras");
            for (int i = 0; i < recebedoras.size(); i++) {
                // Salva as recebedoras
                recebedoras.set(i, this.recebedoraDAO.salvar(recebedoras.get(i)));
            }
            portaria.setUndRecebedora(recebedoras);
        }

        if(parametros.containsKey("arqPdf")) {
            File arqPdf = (File) parametros.get("arqPdf");
            if(arqPdf.exists()) {
                byte[] fileContent = Files.readAllBytes(arqPdf.toPath());
                portaria.setArqPdf(fileContent);
            }
        }

        // Define o status de Proposta
        portaria.setStatus(PortariaStatus.Proposta);

        portaria = this.portariaDAO.salvar(portaria);


        //this.undAdmDAO.salvar(expedidora);

        // Comitta
        try{
            this.portariaDAO.commitarTransacao();
            return portaria;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}