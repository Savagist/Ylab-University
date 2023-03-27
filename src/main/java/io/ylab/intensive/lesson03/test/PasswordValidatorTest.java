package io.ylab.intensive.lesson03.test;

import io.ylab.intensive.lesson03.passwordvalidator.PasswordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        String login = "homework_login_123";
        String password = "qwerty_12345";
        String confirmPassword = "qwerty_12345";
        if (PasswordValidator.checkLoginAndPassword(login, password, confirmPassword)) {
            System.out.println("Регистрация прошла успешно!");
        }
    }
}
