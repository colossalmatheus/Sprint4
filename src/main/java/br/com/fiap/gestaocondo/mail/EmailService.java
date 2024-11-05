package br.com.fiap.gestaocondo.mail;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Uso da injeção de dependência pelo construtor
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Método para enviar email para um destinatário específico com uma mensagem personalizada
    public void sendEmail(String to, String subject, String message) throws MessagingException {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Endereço de e-mail não pode ser nulo ou vazio");
        }
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Assunto do e-mail não pode ser nulo ou vazio");
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Mensagem do e-mail não pode ser nula ou vazia");
        }

        // Cria uma nova mensagem de email
        var email = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(email, true); // 'true' indica suporte para HTML

        // Define os detalhes do e-mail
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(String.format("""
                    <h1>%s</h1>
                    <p>%s</p>
                """, subject, message), true); // 'true' indica que o conteúdo é HTML

        // Envia o e-mail
        mailSender.send(email);
    }
}
