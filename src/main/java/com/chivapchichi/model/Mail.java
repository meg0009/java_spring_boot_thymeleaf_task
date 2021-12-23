package com.chivapchichi.model;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;

public class Mail {

    @Email
    private String toEmail = "";

    private String subject = "";

    private String text = "";

    @NonNull
    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(@NonNull String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }
}
