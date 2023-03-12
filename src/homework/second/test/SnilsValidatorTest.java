package homework.second.test;

import homework.second.snilsvalidator.SnilsValidatorImpl;

import java.util.Scanner;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)){
            System.out.print("Введите номер снилса: ");
            String snils = scanner.nextLine();
            scanner.close();
            System.out.println("Проверяемый снилс: " + snils + " - " + new SnilsValidatorImpl().validate(snils));
        }
    }
}