/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;

import java.util.*;
import java.sql.Time;

public class ControladorNotifPortSemCiencia {

    public void verificarCiencia() {

        DesignadoDAO designadoDao = new DesignadoDAO();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date dataLimite = calendar.getTime();

        StringBuilder builder = new StringBuilder();
        builder.append("select d from Designado d where d.dtCienciaDesig is null ");
        builder.append(" and d.portaria.dtExped <= :dtExped ");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("dtExped", dataLimite );

        List<Designado> designados = designadoDao.pesquisarJPQLCustomizada(builder.toString(),parametros);

        List<String> emails = getEmailDesignados(designados);

        enviarEmail(emails);
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
