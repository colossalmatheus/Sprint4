package br.com.fiap.gestaocondo.mail;

import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    // Construtor para injeção do serviço de e-mail
    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email-queue")
    public void sendEmail(String message) {
        try {
            String to = "matheuscolossal1803@gmail.com";  // Altere para o endereço de e-mail desejado
            String subject = "Notificação de Nova Tarefa";

            emailService.sendEmail(to, subject, message);
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
