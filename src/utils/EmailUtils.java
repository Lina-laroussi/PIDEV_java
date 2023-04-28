/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import entities.User;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;



public class EmailUtils {
    
        
   public static void sendEmail(String to, String subject,User user) throws Exception {
        
      String from = "medcare.nonreply@gmail.com";
      String password = "qyarrspaqlrqgqgf";
      String host = "smtp.gmail.com";  
      
      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.socketFactory.port", "465");

      // Get default session object
      Session session = Session.getDefaultInstance(properties,
         new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(from, password);
            }
         });

      // Create message
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      MimeMultipart multipart = new MimeMultipart();

         BodyPart htmlBodyPart = new MimeBodyPart();
         String htmlFilePath = EmailUtils.class.getResource("confirmation-register.html").getPath();
         String htmlContent = "";
         try (BufferedReader br = new BufferedReader(new FileReader(htmlFilePath))) {
             String line;
             while ((line = br.readLine()) != null) {
                 htmlContent += line + "\n";
             }
         }
         String nom = user.getNom();
         String prenom=user.getPrenom();
         String email = user.getEmail();
         htmlContent = htmlContent.replace("{nom}", nom);
         htmlContent = htmlContent.replace("{prenom}", prenom);
         htmlContent = htmlContent.replace("{email}", email);
         
         htmlBodyPart.setContent(htmlContent, "text/html");
         multipart.addBodyPart(htmlBodyPart);

         message.setContent(multipart);
      // Send message
      Transport.send(message);
      System.out.println("Message sent successfully!");
   }
   
   
   
   
    public static void sendVerificationCode(String to, String subject, String verificationCode,User user) throws Exception {
        
      String from = "medcare.nonreply@gmail.com";
      String password = "qyarrspaqlrqgqgf";
      String host = "smtp.gmail.com";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.socketFactory.port", "465");

      // Get default session object
      Session session = Session.getDefaultInstance(properties,
         new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(from, password);
            }
         });

      // Create message
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      MimeMultipart multipart = new MimeMultipart();

         BodyPart htmlBodyPart = new MimeBodyPart();
         String htmlFilePath = EmailUtils.class.getResource("confirmation-register-patient.html").getPath();
         String htmlContent = "";
         try (BufferedReader br = new BufferedReader(new FileReader(htmlFilePath))) {
             String line;
             while ((line = br.readLine()) != null) {
                 htmlContent += line + "\n";
             }
         }
         String nom = user.getNom();
         String prenom=user.getPrenom();
         String email = user.getEmail();
         htmlContent = htmlContent.replace("{nom}", nom);
         htmlContent = htmlContent.replace("{prenom}", prenom);
         htmlContent = htmlContent.replace("{email}", email);
         htmlContent = htmlContent.replace("{verificationCode}", verificationCode);
         
         htmlBodyPart.setContent(htmlContent, "text/html");
         multipart.addBodyPart(htmlBodyPart);

         message.setContent(multipart);
      // Send message
      Transport.send(message);
      System.out.println("Message sent successfully!");
   }
    
    
    
    
    public static void resendVerificationCode(String to, String subject, String verificationCode,User user) throws Exception {
        
      String from = "medcare.nonreply@gmail.com";
      String password = "qyarrspaqlrqgqgf";
      String host = "smtp.gmail.com";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.socketFactory.port", "465");

      // Get default session object
      Session session = Session.getDefaultInstance(properties,
         new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(from, password);
            }
         });

      // Create message
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      MimeMultipart multipart = new MimeMultipart();

         BodyPart htmlBodyPart = new MimeBodyPart();
         String htmlFilePath = EmailUtils.class.getResource("nouveau-code-verification.html").getPath();
         String htmlContent = "";
         try (BufferedReader br = new BufferedReader(new FileReader(htmlFilePath))) {
             String line;
             while ((line = br.readLine()) != null) {
                 htmlContent += line + "\n";
             }
         }
         String nom = user.getNom();
         String prenom=user.getPrenom();
         String email = user.getEmail();
         htmlContent = htmlContent.replace("{nom}", nom);
         htmlContent = htmlContent.replace("{prenom}", prenom);
         htmlContent = htmlContent.replace("{email}", email);
         htmlContent = htmlContent.replace("{verificationCode}", verificationCode);
         
         htmlBodyPart.setContent(htmlContent, "text/html");
         multipart.addBodyPart(htmlBodyPart);

         message.setContent(multipart);
      // Send message
      Transport.send(message);
      System.out.println("Message sent successfully!");
   }
    
    
    
    
    public static void sendPassword(String to, String subject, String verificationCode,User user) throws Exception {
        
      String from = "medcare.nonreply@gmail.com";
      String password = "qyarrspaqlrqgqgf";
      String host = "smtp.gmail.com";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.socketFactory.port", "465");

      // Get default session object
      Session session = Session.getDefaultInstance(properties,
         new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(from, password);
            }
         });

      // Create message
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
       MimeMultipart multipart = new MimeMultipart();

         BodyPart htmlBodyPart = new MimeBodyPart();
         String htmlFilePath = EmailUtils.class.getResource("Forgot-password.html").getPath();
         String htmlContent = "";
         try (BufferedReader br = new BufferedReader(new FileReader(htmlFilePath))) {
             String line;
             while ((line = br.readLine()) != null) {
                 htmlContent += line + "\n";
             }
         }
         String nom = user.getNom();
         String email = user.getEmail();
         htmlContent = htmlContent.replace("{nom}", nom);
         htmlContent = htmlContent.replace("{email}", email);
         htmlContent = htmlContent.replace("{verificationCode}", verificationCode);
         
         htmlBodyPart.setContent(htmlContent, "text/html");
         multipart.addBodyPart(htmlBodyPart);

         message.setContent(multipart);
      // Send message
      Transport.send(message);
      System.out.println("Message sent successfully!");
   }  
    
    
}
