package io.ylab.intensive.lesson01;

import java.util.Scanner;

public class Stars {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            char template = scanner.next().charAt(0); // Изменил условие, чтобы брать лишь первый вводимый символ для шаблона
            scanner.close();
            showFigure(n, m, template);
        }
    }

    public static void showFigure(int row, int column, char template) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(template);
            }
            System.out.println();
        }
    }
}


