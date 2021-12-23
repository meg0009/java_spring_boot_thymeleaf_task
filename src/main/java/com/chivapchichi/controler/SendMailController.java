package com.chivapchichi.controler;

import com.chivapchichi.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SendMailController {

    @Autowired
    public JavaMailSender email;

    @GetMapping("/send-mail/{email}")
    public String sendMail(@PathVariable(value = "email") String email, Model model) {
        Mail mail = new Mail();
        mail.setToEmail(email);
        model.addAttribute("mail", mail);
        return "send-email";
    }

    @PostMapping("/send-mail")
    public String sendMessage(@ModelAttribute Mail mail) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mail.getToEmail());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());

        this.email.send(message);

        return "email-ok";
    }
}
