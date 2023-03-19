package homework.third.passwordvalidator;

public class PasswordValidator {
    private static final int LENGHT = 20;

    public static boolean checkLoginAndPassword(String login, String password, String confirmPassword) {
        try {
            if (!login.matches("^$|^[a-zA-Z0-9_]+$")) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            if (login.length() >= LENGHT) {
                throw new WrongLoginException("Логин слишком длинный");
            }
            if (!password.matches("^$|^[a-zA-Z0-9_]+$")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            if (password.length() >= LENGHT) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}

class WrongPasswordException extends Exception {

    public WrongPasswordException(String message) {
        super(message);
    }
}

class WrongLoginException extends Exception {

    public WrongLoginException(String message) {
        super(message);
    }
}
