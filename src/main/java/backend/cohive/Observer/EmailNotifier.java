package backend.cohive.Observer;

import backend.cohive.domain.service.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifier implements Observer {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void notify(String message, String productName, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("ALERTA DE QUANTIDADE PRODUTO");
        mailMessage.setText("A quantidade do produto: " + productName + " está em 3.");
        emailSender.send(mailMessage);
    }
}
