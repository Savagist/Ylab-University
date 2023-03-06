package homework.first;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
        guessNumber();
    }

    public static void guessNumber() {
        int number = new Random().nextInt(99) + 1; // Здесь загадывается число от 1 до 99
        int maxAttempts = 10; // Здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            int input;
            for (int attempt = 1; attempt < maxAttempts + 1; attempt++) {
                input = scanner.nextInt();
                if (input == number) {
                    System.out.println("Ты угадал с " + attempt + " попытки");
                    return;
                } else if (input < number) {
                    System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                } else {
                    System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                }
            }
            System.out.println("У пользователя закончились попытки и число не было угадано");

        }
    }
}
