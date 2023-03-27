package io.ylab.intensive.lesson02.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        // Проверка валидности введенной строки для проверки
        if (!snils.matches("\\d+") || snils.length() != LENGHT) {
            return false;
        }
        int snilsCheckNumber = Integer.parseInt(snils.substring(9)); // Часть для получение контрольной суммы
        String valueForSum = snils.substring(0, 9); // Контрольный номер
        int checkSum = 0;
        for (int i = 0, coefficient = valueForSum.length(); i < valueForSum.length() && coefficient > 0; i++, coefficient--) {
            checkSum += coefficient * Character.getNumericValue(valueForSum.charAt(i));
        }
        int checkNumber;
        if (checkSum < 100) {
            checkNumber = checkSum;
        } else if (checkSum == 100) {
            checkNumber = 0;
        } else {
            int modulo = checkSum % 101;
            if (modulo == 100) {
                checkNumber = 0;
            } else {
                checkNumber = modulo;
            }
        }
        return snilsCheckNumber == checkNumber;
    }
}
