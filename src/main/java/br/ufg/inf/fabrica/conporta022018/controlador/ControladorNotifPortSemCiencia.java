/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.persistencia.DesignadoDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorNotifPortSemCiencia {

    public void verificarCiencia() throws ParseException {

        DesignadoDAO designadoDao = new DesignadoDAO();

        //Define o atraso limite apra a ciência
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date data = cal.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String dataLimite = formatter.format(data);

        List<Designado> designados = designadoDao.pesquisaDesignadosSemCiencia(formatter.parse(dataLimite));

        List<String> emails = getEmailDesignados(designados);

        this.enviarEmail(emails);
    }

    /**
     * Método criado somente para mock de testes.
     * Recebe a dataLimite como parâmetro apra que os teste funcionem independente da data atual
     * @param dataLimite
     */
    public void verificarCiencia(String dataLimite) throws ParseException {

        DesignadoDAO designadoDao = new DesignadoDAO();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        List<Designado> designados = designadoDao.pesquisaDesignadosSemCiencia(formato.parse(dataLimite));

        List<String> emails = getEmailDesignados(designados);

        this.enviarEmail(emails);

    }


    /**
     * Método Criado para a modularização do controlador,
     * com o objetivo de facilitar a legibilidade do código
     *
     * @param designados
     * @return
     */
    public List<String> getEmailDesignados(List<Designado> designados){
        List<String> emails = null;
        Iterator<Designado> iterator = designados.iterator();

        while (iterator.hasNext()){
            emails.add(iterator.next().getDesignado().getEmailPes());
        }

        return emails;
    }

    /**
     * Método Criado para a modularização do controlador,
     * com o objetivo de facilitar a legibilidade do código
     * @param emails
     */
    public void enviarEmail(List<String> emails)
    {
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

        String destinatarios = String.join(", ",emails);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("conporta2018@gmail.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(destinatarios);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Enviando email com JavaMail");//Assunto
            message.setText("Você possui pendências na plataforma Conporta");


            /**Método para enviar a mensagem criada*/
            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}


