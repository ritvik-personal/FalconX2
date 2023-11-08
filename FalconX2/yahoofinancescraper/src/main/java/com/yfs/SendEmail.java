package com.yfs;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    public static void main(String[] args) {
        // Sender's email credentials
        final String senderEmail = "falconx264@gmail.com";
        final String senderPassword = "cmkj lunm rjbs kdsi";

        // Recipient email addresses
        String[] recipientEmails = {
            "ritvik.sajja@gmail.com","sachd9143@gmail.com","angadsrandhawa@gmail.com","kyarlagadda@gmail.com","rishidasari@gmail.com"
            // Add more recipient email addresses here
        };

        // SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
        properties.put("mail.smtp.port", "587"); // Replace with the appropriate port
        //properties.put("mail.smtp.socketFactory.port", "465");
        //properties.put("mail.smtp.socketFactory.class", "javax.net.sslSSLSocketFactory");
        // Create a session with authentication
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);
           

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(senderEmail));
            
            // Set multiple recipient addresses
            InternetAddress[] recipientAddresses = new InternetAddress[recipientEmails.length];
            for (int i = 0; i < recipientEmails.length; i++) {
                recipientAddresses[i] = new InternetAddress(recipientEmails[i]);
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);

            // Set the email subject and content
            message.setSubject("Daily Stock Email");
            message.setText(YahooFS.getAllInfo());

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}