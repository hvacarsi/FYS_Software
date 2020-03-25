/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware.controller;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author User
 */
public class Mail {

    protected final String username = "hva2016is107";
    protected final String password = "IS107Amsterdam";
    private Properties props;
    private Session session;
    private Message message;
    private Transport transport;
    private String to, subject, body;

    public Mail(String to, String subject, String body) {
        //Sla waardes op in de hoofdvariabelen
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public void sendMail() {
        //Maak variabel properties
        props = new Properties();
        //Set smtp info, username, password, port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");

        //Put props in session
        session = Session.getInstance(props);
        
        //Maak message object
        message = new MimeMessage(session);
        try {
            //Stel de instellingen, bericht zelf, onderwerp, en naar wie
            message.setFrom(new InternetAddress(username));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setText(body);
            transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }

    }

}
