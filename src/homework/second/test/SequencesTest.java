package homework.second.test;

import homework.second.sequences.Sequences;
import homework.second.sequences.SequencesImpl;

import java.util.Scanner;

public class SequencesTest {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите значение для N:");
            int n = scanner.nextInt();
            while (n < 1 || n > 16) {
                System.out.println("Значение не корректно, введите значение больше 0, но меньше 17");
                System.out.print("  Введите значение для N:");
                n = scanner.nextInt();
            }
            scanner.close();
            Sequences sequences = new SequencesImpl();
            sequences.a(n);
            sequences.b(n);
            sequences.c(n);
            sequences.d(n);
            sequences.e(n);
            sequences.f(n);
            sequences.g(n);
            sequences.h(n);
            sequences.i(n);
            sequences.j(n);
        }
    }
}
