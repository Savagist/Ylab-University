package homework.second.snilsvalidator;

public interface SnilsValidator {
    int LENGHT = 11; // Размер номера снилса
    /**
     * Проверяет, что в строке содержится валидный номер СНИЛС
     * @param snils снилс
     * @return результат проверки
     */
    boolean validate(String snils);
}
