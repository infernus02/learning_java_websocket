package com.project.webchatbe.service.interfaces;

public interface IEmailService {
    public void sendEmailResetPass(String toEmail, String linkReset);
}
