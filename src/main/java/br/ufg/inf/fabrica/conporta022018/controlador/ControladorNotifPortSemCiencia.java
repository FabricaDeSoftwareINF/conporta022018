/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;

import java.util.*;

public class ControladorNotifPortSemCiencia {

    public void verificarCiencia() {

        DesignadoDAO designadoDao = new DesignadoDAO();

    
        List<Designado> designados = designadoDao.pesquisaDesignadosSemCiencia();

        List<String> emails = getEmailDesignados(designados);

        this.enviarEmail(emails);
    }


    public List<String> getEmailDesignados(List<Designado> designados){
        List<String> emails = null;
        Iterator<Designado> iterator = designados.iterator();

        Designado designadoTemp;

        while (iterator.hasNext()){
            emails.add(iterator.next().getDesignado().getEmailPes());
        }

        return emails;
    }

    public void enviarEmail(List<String> emails){
        System.out.println("TODO enviar email");
    }
}
