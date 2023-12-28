package com.group.brain_web.service.password.reset;

public interface PasswordResetService {

    String passwordReset(String email);

    String passwordResetConfirm(String password, int id);
}
