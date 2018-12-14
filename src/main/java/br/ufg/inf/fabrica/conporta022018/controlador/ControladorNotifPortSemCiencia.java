/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.modelo.PortariaStatus;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;
import sun.security.krb5.internal.crypto.Des;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorNotifPortSemCiencia {

    private final static String BUSCAR_PORTARIA = "SELECT p FROM Portaria p WHERE p.dtExped <:dataProcurada";

    public void verificarCiencia() throws ParseException {

        JavaMail javaMail = new JavaMail();
        List<String> emails = encontraEmailDesig();

        if (emails.size()>0) {

            javaMail.enviarEmail(emails, "Você possui pendências de ciência da plataforma Conporta:\n");

        }
    }

    public List<String> encontraEmailDesig() throws ParseException {

        PortariaDAO portariaDAO = new PortariaDAO();
        Map<String, Object> busca = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date data = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(data);
        busca.put("dataProcurada", formatter.parse(s));

        List<Portaria> portarias = portariaDAO.pesquisarJPQLCustomizada(BUSCAR_PORTARIA, busca);
        List<String> emails = new ArrayList<>();
        Boolean flag = false;

        for (Portaria portaria : portarias){
                for (Designado designado : portaria.getDesignados()) {
                    if (designado.getDtCienciaDesig() == null || designado.getDtCienciaDesig().equals("")) {
                        emails.add(designado.getDesignado().getEmailPes());
                        flag = true;
                    }
                }
                if (flag) {
                    emails.add(portaria.getExpedidor().getEmailPes());
                    flag = false;
                }
            }

        return emails;
    }



}


