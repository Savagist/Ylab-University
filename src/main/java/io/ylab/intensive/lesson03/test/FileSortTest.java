package io.ylab.intensive.lesson03.test;

import io.ylab.intensive.lesson03.filesort.Generator;
import io.ylab.intensive.lesson03.filesort.Sorter;
import io.ylab.intensive.lesson03.filesort.Validator;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileSortTest {
    public static void main(String[] args) throws IOException {
        String unsortedFile = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "unsorted.txt";
        System.out.println("Start: " + new Date());
        File dataFile = new Generator().generate(unsortedFile, 100);
        System.out.println("Файл сгенерирован: " + new Date());
        System.out.println("Проверка после генерации: " + new Date() + " = " + new Validator(dataFile).isSorted());
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println("Отсортированный файл сгенерирован: " + new Date());
        System.out.println("Проверка после сортировки: " + new Date() + " = " + new Validator(sortedFile).isSorted());
        System.out.println("Stop: " + new Date());
    }

}
