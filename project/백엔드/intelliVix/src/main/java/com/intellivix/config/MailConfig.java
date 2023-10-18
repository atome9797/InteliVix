package com.intellivix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import java.util.Properties;

@Component
@PropertySource("classpath:mail.properties")
public class MailConfig {

    Properties pt = new Properties();

    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.socketFactory.port}")
    private int socketPort;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.smtp.starttls.required}")
    private boolean startlls_required;
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;


    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtps.hiworks.com");
        javaMailSender.setUsername("cs@intellivix.com");
        javaMailSender.setPassword("dlsxpfflqlrtm@!");
        javaMailSender.setPort(port);

        pt.put("mail.smtp.host", "smtps.hiworks.com");
        pt.put("mail.smtp.port", port);
        pt.put("mail.smtp.auth", "true");
        pt.put("mail.smtp.ssl.enable", "true");
        pt.put("mail.smtp.ssl.trust", "smtps.hiworks.com");

        Session session = Session.getDefaultInstance(pt, new javax.mail.Authenticator() {
           String un = "cs@intellivix.com" ;
           String pw = "dlsxpfflqlrtm@!";
           protected javax.mail.PasswordAuthentication getPA() {
               return new javax.mail.PasswordAuthentication(un,pw);
           }
        });
        session.setDebug(true);

        javaMailSender.setSession(session);


//        pt.put("mail.smtp.auth",true);
//        pt.put("mail.smtp.starttls.enable", true);
//        pt.put("mail.smtps.checkserveridentity",true);
//        pt.put("mail.smtps.ssl.enable", true);
//        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        pt.put("mail.smtp.debug",true);
//        pt.put("mail.debut",true)
////        pt.put("mail.smtp.socketFactory.port", socketPort);
////        pt.put("mail.smtp.auth", auth);
////        pt.put("mail.smtp.starttls.enable", starttls);
////        pt.put("mail.smtp.starttls.required", startlls_required);
////        pt.put("mail.smtp.socketFactory.fallback",fallback);
////        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
////        pt.put("mail.smtp.ssl.enable", "true");
////        pt.put("mail.smtp.debug","true");
//
//        javaMailSender.setJavaMailProperties(pt);
//        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }


}
