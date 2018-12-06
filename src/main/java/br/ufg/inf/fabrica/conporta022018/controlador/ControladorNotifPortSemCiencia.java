/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

        while (iterator.hasNext()){
            emails.add(iterator.next().getDesignado().getEmailPes());
        }

        return emails;
    }


    public void enviarEmail(List<String> emails)
    {
        System.out.println("TODO enviar email");
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication("conporta2018@gmail.com", "c0np0rt4");
                    }
                });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("seuemail@gmail.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse("edionay@gmail.com, joaolucaspachecoab@gmail.com");

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Enviando email com JavaMail");//Assunto
            message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
            /**Método para enviar a mensagem criada*/
            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    }


