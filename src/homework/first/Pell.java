package homework.first;

import java.util.Scanner;


public class Pell {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            while (n < 0 || n > 31) {
                n = scanner.nextInt(); // Проверка 0 < n < 30, и повтор ввода в случае неверного ввода
            }
            System.out.println(pellNumbersWithRecursion(n));
            System.out.println(pellNumbersDefault(n));
        }
    }

    public static long pellNumbersWithRecursion(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return 2 * pellNumbersWithRecursion(n - 1) + pellNumbersWithRecursion(n - 2);
        }
    }

    public static long pellNumbersDefault(int n) {
        long firstState = 0;
        long secondState = 1;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            long value;
            for (int i = 2; i < n+1; i++) {  // Добавляем +1 чтобы посчитать необходимый нам элемент
                value = 2 * secondState + firstState;
                firstState = secondState; // Храним только 2 последних значения для пересчета
                secondState = value;
            }
            return secondState;
        }
    }
}
