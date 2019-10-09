package sv.edu.udb.www.util;

import java.security.SecureRandom;
import java.util.Properties;  
import java.util.Random;
  
import javax.mail.*;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
  
public class Mailer {  
    
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
    public static String generatePassword(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
	}
    
    public static void send(String to,String subject,String msg){  

        final String user="noreplydwf01@gmail.com";//change accordingly  
        final String pass="Passw0rd#01";  

        Properties props = new Properties();  

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,  
         new javax.mail.Authenticator() {  
          protected PasswordAuthentication getPasswordAuthentication() {  
           return new PasswordAuthentication(user,pass);  
           }  
        });  

            try {  
                     MimeMessage message = new MimeMessage(session);  
                     message.setFrom(new InternetAddress(user));  
                     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
                     message.setSubject(subject);  
                     message.setText(msg);  

                     Transport.send(message);  

             } catch (MessagingException e) {  
                throw new RuntimeException(e);  
             }  
        }  
} 