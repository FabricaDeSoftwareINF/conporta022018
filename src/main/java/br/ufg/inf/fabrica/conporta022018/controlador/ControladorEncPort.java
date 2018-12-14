/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;
import br.ufg.inf.fabrica.conporta022018.modelo.*;
import br.ufg.inf.fabrica.conporta022018.persistencia.PessoaDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

import java.util.ArrayList;
import java.util.List;

public class ControladorEncPort {

    private PessoaDAO pessoaDAO = new PessoaDAO();
    private PortariaDAO portariaDAO = new PortariaDAO();
    /**
     * O método abaixo é responsável por execução do caso de uso, ou seja,
     * é responsável por chamar/invocar os demais métodos presente no
     * controlador;
     * @param portaria
     * @return
     */
    public boolean encPortariaCiencia(Portaria portaria){

        if(portariaIsValida(portaria.getId()) == true){
            List<Designado> designados = portaria.getDesignados();
            JavaMail javaMail = new JavaMail();
            javaMail.enviarEmail(getEmailDesignados(designados), portaria);
            javaMail.enviarEmail(getEmailResponsavelUndRec(portaria), portaria);

        }

        return true;

    }

    /**
     * O método abaixo verifica se a portaria é válida de acordo com a regra
     * de negócio PortValid definida em ConPorta022018-DesigFun-EncPorta
     * @param id da portaria, recebe como parametro id da portaria portaria
     * @return falso (false) caso portaria não seja válida, ou seja, caso não
     * atenda os critérios estabelecidos e retorna true (verdadeiro) caso seja
     * uma portaria válida;
     */
    public boolean portariaIsValida (Long id) {

            Portaria portaria = portariaDAO.buscar(id);

            if (portaria != null && (portaria.getStatus() == PortariaStatus.ATIVA || portaria.getStatus() == PortariaStatus.CANCELADA)
                    && portaria.getUnidadeExpedidora() != null   ) {
                return  true;
            }else{
                throw new UnsupportedOperationException("Portaria não é válida.");
        }
    }

    /**
     * Esse método tem como objetivo pegar o e-mail dos designados de uma
     * determinada unidade;
     * @param designados
     * @return
     */
     public List <String> getEmailDesignados ( List <Designado> designados){
         List <String> listaEmail = new ArrayList<>();

         for (Designado designado: designados) {
             String email = designado.getDesignado().getEmailPes();
             if (email != null) {
                 listaEmail.add(email);
             }
         }
         return  listaEmail;
    }

    /**
     * Esse método tem como objetivo pegar o e-mail dos responsáveis pela
     * unidade administrativa recebedora da portaria de acordo com a regra
     * PortValid definida no documento ConPorta022018-DesigFun-EncPorta
     * @param portaria, recebe como parametro uma portaria válida.
     */
    public List<String>  getEmailResponsavelUndRec(Portaria portaria){
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
