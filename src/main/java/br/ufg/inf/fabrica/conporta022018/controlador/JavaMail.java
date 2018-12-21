/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import br.ufg.inf.fabrica.conporta022018.modelo.Mensagem;

public class JavaMail {
    Mensagem mensagem = new Mensagem();

    /**
     * Metódo responsável pelo serviço de envio de e-mail, que pega como
     * parametro uma lista de e-mails e uma portaria;
     * @param emailUsr
     * @param portaria
     */
    public void enviarEmail(final List<String> emailUsr, final Portaria portaria) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Properties props = new Properties();

                //Parâmetros de conexão com servidor de email
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("adm_conporta@gmail.com", "Adm_ConPorta");
                    }
                });

                session.setDebug(true); //Ativa Debug para sessão

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("adm_conporta@gmail.com")); //Remetente

                    Address[] toUser = (Address[]) emailUsr.toArray(); //Destinatário(s)

                    message.setRecipients(Message.RecipientType.TO, toUser);
                    message.setSubject(mensagem.getTitulo());
                    String url = "https://conporta.com.br/" + portaria.getSeqId();
                    message.setText("<!DOCTYPE html><html>"  + mensagem.getDescricao() + url +
                            " </html>", "utf-8", "html");

                    Transport.send(message);//Método para enviar a mensagem criada

                    System.out.println("Feito!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void enviarEmail(List<String> emails, String mensagem)
        Properties props = new Properties();
    {
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                        return new PasswordAuthentication("conporta2018@gmail.com", "c0np0rt4");
                    {
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

            message.setText(mensagem);

            /**Método para enviar a mensagem criada*/

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}