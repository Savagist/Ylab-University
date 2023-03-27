package io.ylab.intensive.lesson02.test;

import io.ylab.intensive.lesson02.complexnumbers.ComplexNumbers;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumbers one = new ComplexNumbers(6.3);
        ComplexNumbers two = new ComplexNumbers(1.2, -2.5);
        System.out.println("Первое число: " + one);
        System.out.println("Второе число: " + two);
        System.out.println("Сложение: " + one.sum(two));
        System.out.println("Вычитание: " + one.sub(two));
        System.out.println("Умножение: " + one.mul(two));
        System.out.println("Операция получения модуля: " + two.getModule());
    }
}
