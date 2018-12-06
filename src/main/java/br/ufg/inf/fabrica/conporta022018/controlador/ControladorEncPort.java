/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ControladorEncPort {

    private Portaria portaria = new Portaria();
    private Pessoa pessoa = new Pessoa();
    private Designado designado = new Designado();
    private PessoaDAO pessoaDAO = new PessoaDAO();

    public boolean encPortariaCiencia(Portaria portaria){

        if(portariaIsValida(portaria) == true){
            List<Designado> designados = portaria.getDesignados();
            JavaMail enviar = new JavaMail();
            enviar.enviarEmail(getEmailDesignados(designados), portaria);
            enviar.enviarEmail(getEmailResponsavelUnidRec(portaria), portaria);


        }

        return true;

    }

    /**
     * O método abaixo verifica se a portaria é válida de acordo com a regra
     * de negócio PortValid definida em ConPorta022018-DesigFun-EncPorta
     * @param portaria, recebe como parametro uma portaria
     * @return falso (false) caso portaria não seja válida, ou seja, caso não
     * atenda os critérios estabelecidos e retorna true (verdadeiro) caso seja
     * uma portaria válida.
     */
    public boolean portariaIsValida (Portaria portaria){
        if ( portaria.getUnidadeExpedidora() != null &&
                (portaria.getStatus() == PortariaStatus.ATIVA ||
                        portaria.getStatus() == PortariaStatus.CANCELADA) ){
            return true;
        }
        return  false;
    }

    /**
     * Esse método verifica se uma portaria tem designado, uma portoria pode
     * não ter designado. Sendo assim, o objetivo desse método é otimizar
     * ganho de performmace, ou seja, caso não tenha designado não será
     * necessário executar o método responsável por pegar e-mail dos designados
     * @param portaria, recebe uma portaria.
     * @return true (verdadeiro) caso portaria tenha designado e false (falso)
     * casco não tenha.
     */
    public boolean temDesignados(Portaria portaria){
        if (portaria.getDesignados() != null){
            return  true;
        }
        return  false;
    }

    /**
     * Esse método tem como objetivo pegar o e-mail dos designados de uma
     * determinada unidade.
     * @param designados
     * @return
     */
     public List <String> getEmailDesignados ( List <Designado> designados){
         List <String> email = null;
         for (Designado designado: designados) {
             
         }
         return  email;
    }

    /**
     * Esse método tem como objetivo pegar o e-mail dos responsáveis pela
     * unidade administrativa recebedora da portaria de acordo com a regra
     * PortValid definida no documento ConPorta022018-DesigFun-EncPorta
     * @param portaria, recebe como parametro uma portaria válida.
     */
    public List<String>  getEmailResponsavelUnidRec(Portaria portaria){
        List<Recebedora> undRecebedora = portaria.getUndRecebedora();
        List<String> emailRespUndRec = null;

        try{
            String jpql = "SELECT p FROM Pessoa";
            List<Pessoa> listaPessoas = pessoaDAO.pesquisarJPQLCustomizada(jpql, null);
            for (Pessoa pessoa: listaPessoas) {
                List<Gestao> listaGestao = pessoa.getGestao();
                for (Gestao gestao: listaGestao) {
                    for (Recebedora recebedora: undRecebedora) {
                        if (recebedora.getUnidadeRecebedora() == gestao.getUnAdm()) {
                            if (gestao.getdtFim() == null){
                                emailRespUndRec.add(pessoa.getEmailPes());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Não é possível pegar os e-mails");
        }
        return  emailRespUndRec;
    }

}
