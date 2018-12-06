package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {
    public void enviarEmail(final List<String> emailUsr,Portaria portaria) {
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
                    message.setSubject("Encaminhento ciência da portaria " +portaria.getSeqId() ); //Assunto
                    message.setText("<!DOCTYPE html><html> Por favor clicar no" +
                            " link para realizar ciência da portaria: https://conporta.com.br/ciencia/ "
                            + portaria.getSeqId() +" </html>", "utf-8", "html");

                    Transport.send(message);//Método para enviar a mensagem criada

                    System.out.println("Feito!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
