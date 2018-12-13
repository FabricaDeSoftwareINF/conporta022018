/*
 * Copyright (c) 2018.
 * Fabrica de Software INF
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.fabrica.conporta022018.controlador;

import br.ufg.inf.fabrica.conporta022018.modelo.Designado;
import br.ufg.inf.fabrica.conporta022018.modelo.Portaria;
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

    private List<String> ciencia = new ArrayList<>();
    private List<String> expedidor = new ArrayList<>();



    public void verificarCiencia() throws ParseException {

        encontraEmailDesig();

        enviarEmail(ciencia, "Você possui uma portaria sem ciência");
        enviarEmail(expedidor,"Você possui designados sem ciência");

    }

    public void encontraEmailDesig(){

        ciencia.clear();
        expedidor.clear();
        PortariaDAO portariaDAO = new PortariaDAO();
        Map<String, Object> busca = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date data = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        busca.put("dataProcurada", formatter.format(data));

        List<Portaria> portarias = portariaDAO.pesquisarJPQLCustomizada(BUSCAR_PORTARIA, busca);
        List<String> ciencia = new ArrayList<>();
        List<String> expedidor = new ArrayList<>();
        Boolean flag = false;

        for (Portaria portaria : portarias){
            for (Designado  designado: portaria.getDesignados()){
                if (designado.getDtCienciaDesig() == null || designado.getDtCienciaDesig().equals("")){
                    ciencia.add(designado.getDesignado().getEmailPes());
                    flag = true;
                }
            }
            if (flag){
                expedidor.add(portaria.getExpedidor().getEmailPes());
                flag = false;
            }
        }
    }
    /**
     * Método Overload criado somente para mock de testes.
     * Recebe a dataLimite como parâmetro para que os teste sejam consistentes independente da data em que são realizados
     * @param dataLimite
     */
    public void verificarCiencia(String dataLimite) throws ParseException {


        DesignadoDAO designadoDao = new DesignadoDAO();
        PortariaDAO portariaDAO = new PortariaDAO();
        Map<String, Object> busca = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date data = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        busca.put("dataProcurada", dataLimite);

        List<Portaria> portarias = portariaDAO.pesquisarJPQLCustomizada(BUSCAR_PORTARIA, busca);
        List<String> ciencia = new ArrayList<>();
        List<String> expedidor = new ArrayList<>();
        Boolean flag = false;

        for (Portaria portaria : portarias){
            for (Designado  designado: portaria.getDesignados()){
                if (designado.getDtCienciaDesig() == null || designado.getDtCienciaDesig().equals("")){
                    ciencia.add(designado.getDesignado().getEmailPes());
                    flag = true;
                }
            }
            if (flag){
                expedidor.add(portaria.getExpedidor().getEmailPes());
                flag = false;
            }
        }

        enviarEmail(ciencia, "Vocẽ possui uma portaria sem ciência");
        enviarEmail(expedidor,"Você possui designados sem ciência");

    }


    private List<String> getEmailExpedidor(List<Portaria> expedidores) {
        List<String> emails = null;
        Iterator<Portaria> iterator = expedidores.iterator();

        while (iterator.hasNext()){
            emails.add(iterator.next().getExpedidor().getEmailPes());
        }

        return emails;

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


