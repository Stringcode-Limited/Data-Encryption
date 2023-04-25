package com.example.demo.service;


import com.example.demo.model.MailOptions;

import java.io.File;
import java.io.FileWriter;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.DataSource;

public class MailSender {
    public static void send(MailOptions mailOptions) {

    String fileName = "encryptedData.txt";

    try {
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(mailOptions.getFileContent());
        writer.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // send the file as an email attachment using JavaMail API
    String recipient = mailOptions.getRecipient();


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("Encrypted information");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This information is encrypted \n Key: "+mailOptions.getKey());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(fileName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("Email sent successfully.");
    } catch (Exception e) {
        e.printStackTrace();
    }

    // delete the file
    try {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
