/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import br.ufg.inf.fabrica.conporta022018.modelo.Pessoa;
import br.ufg.inf.fabrica.conporta022018.modelo.Designado;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ControladorEncPort {

    private PortariaDAO portariaDAO = new PortariaDAO();
    private Pessoa pessoa = new Pessoa();
    private Designado designado = new Designado();

    public boolean encPortariaCiencia(Portaria portaria){

        if(portariaIsValida(portaria) == true){
            getEmailDesignados(portaria);
            getEmailResponsavelUnidRec(portaria);
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
     * determinada portaria.
     * @param portaria, recebe como parametro uma portaria válida.
     */
     public List <String> getEmailDesignados ( List <Designado> designados){
        Iterator<Designado> iterator = designados.iterator();
        Pessoa pessoa;
        String email;
        while (iterator.hasNext()){
            pessoa = iterator.next();

            if(pessoa == designado.getDesignado()){
                email = pessoa.getEmailPes();
                List <String> pessoas = Collections.singletonList(email);

            }

        }

        return  designados;
    }

    /**
     * Esse método tem como objetivo pegar o e-mail dos responsáveis pela
     * unidade administrativa recebedora da portaria de acordo com a regra
     * PortValid definida no documento ConPorta022018-DesigFun-EncPorta
     * @param portaria, recebe como parametro uma portaria válida.
     */
    public void getEmailResponsavelUnidRec(Portaria portaria){
        List undRecebedora = portaria.getUndRecebedora();

    }



}
