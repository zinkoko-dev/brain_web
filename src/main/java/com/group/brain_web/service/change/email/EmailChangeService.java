package com.group.brain_web.service.change.email;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailChangeService {

    String changeEmail(String email) throws UnsupportedEncodingException, MessagingException;

    public String changeEmailSave(String email, int id);
}
