package br.com.fiap.gestaocondo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Ajuste conforme necessário
        mailSender.setPort(587); // Porta padrão para SMTP com STARTTLS
        mailSender.setUsername("seu-email@gmail.com"); // Substitua pelo seu e-mail
        mailSender.setPassword("sua-senha"); // Substitua pela sua senha

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Opcional: define o modo de debug para acompanhar problemas

        return mailSender;
    }
}
