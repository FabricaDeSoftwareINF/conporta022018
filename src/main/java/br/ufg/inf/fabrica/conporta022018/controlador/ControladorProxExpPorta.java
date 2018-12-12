package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
import br.ufg.inf.fabrica.conporta022018.persistencia.PortariaDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorProxExpPorta {

    private final static String BUSCAR_PORTARIA = "SELECT p FROM Portaria p WHERE p.status =:statusAtivo";


    public void verificarPortariasExpirando() throws ParseException {

        PortariaDAO portariaDAO = new PortariaDAO();
        Map<String, Object> busca = new HashMap<>();

        busca.put("statusAtivo", "ATIVO");

        List<Portaria> portarias = portariaDAO.pesquisarJPQLCustomizada(BUSCAR_PORTARIA,busca);

        Calendar cal = Calendar.getInstance();
        Date data = cal.getTime();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = formato.format(data);
        Date dataFormatada = formato.parse(dataString);

        List<String> emails = new ArrayList<>();

        Date inicioVig;
        Date terminoVig;
        for (Portaria portaria: portarias){

            inicioVig = portaria.getDtIniVig();
            terminoVig = portaria.getDtFimVig();

            if (Math.abs(inicioVig.getTime() - terminoVig.getTime()) < 30){
                if (Math.abs(terminoVig.getTime() - dataFormatada.getTime()) < 5){
                    for (Designado designado: portaria.getDesignados()){
                        emails.add(designado.getDesignado().getEmailPes());
                    }
                    emails.add(portaria.getExpedidor().getEmailPes());
                }
            }
            if (Math.abs(inicioVig.getTime() - terminoVig.getTime()) >= 30){
                if (Math.abs(terminoVig.getTime() - dataFormatada.getTime()) < 15){
                    for (Designado designado: portaria.getDesignados()){
                        emails.add(designado.getDesignado().getEmailPes());
                    }
                    emails.add(portaria.getExpedidor().getEmailPes());
                }
            }
        }


        enviarEmail(emails,"Você possui uma portaria próxima a expirar, acesse a plataforma " +
                "Conporta para mais informações" );
    }

    public void enviarEmail(List<String> emails, String mensagem)
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
            message.setText(mensagem);


            /**Método para enviar a mensagem criada*/
            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
