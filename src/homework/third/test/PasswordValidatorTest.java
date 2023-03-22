package homework.third.test;

import static homework.third.passwordvalidator.PasswordValidator.checkLoginAndPassword;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        String login = "homework_login_123";
        String password = "qwerty_12345";
        String confirmPassword = "qwerty_12345";
        if (checkLoginAndPassword(login, password, confirmPassword)) {
            System.out.println("Регистрация прошла успешно!");
        }
    }
}
