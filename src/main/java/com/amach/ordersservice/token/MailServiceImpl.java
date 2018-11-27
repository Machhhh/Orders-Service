package com.amach.ordersservice.token;

import com.amach.ordersservice.client.Client;
import com.amach.ordersservice.client.ClientFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
class MailServiceImpl {

    private JavaMailSender javaMailSender;
    private ClientFacade clientFacade;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, ClientFacade clientFacade) {
        this.javaMailSender = javaMailSender;
        this.clientFacade = clientFacade;
    }

    public void sendNotification(String login, String token) throws MailException {

        Client client = clientFacade.getClientByLogin(login);
        SimpleMailMessage mail = new SimpleMailMessage();
        String link = "http://localhost:8090/passwords/reset/" + token;
        mail.setTo(client.getEmail());
        mail.setFrom("ordersservice123@gmail.com");
        mail.setSubject("Orders-service password reset procedure");
        mail.setText("Hello " + client.getLogin()
                + ".\n To reset your password please click link below: \n\n" + link
                + "\n\n Thank you so much for using Orders-Service!");
        javaMailSender.send(mail);
    }
}
