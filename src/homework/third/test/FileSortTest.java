package homework.third.test;

import homework.third.filesort.Generator;
import homework.third.filesort.Sorter;
import homework.third.filesort.Validator;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileSortTest {
    public static void main(String[] args) throws IOException {
        // Файл с сгенерированными значениями находится в папке filesort c названием unsorted.txt
        String unsortedFile = "src" + File.separator + "homework" + File.separator + "third" + File.separator + "filesort" + File.separator + "unsorted.txt";
        System.out.println("Start: " + new Date());
        File dataFile = new Generator().generate(unsortedFile, 100);
        System.out.println("Файл сгенерирован: " + new Date());
        System.out.println("Проверка после генерации: " + new Date() + " = " + new Validator(dataFile).isSorted());
        // Файл с отсортированными значениями находится в папке filesort c названием sorted.txt
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println("Отсортированный файл сгенерирован: " + new Date());
        System.out.println("Проверка после сортировки: " + new Date() + " = " + new Validator(sortedFile).isSorted());
        System.out.println("Stop: " + new Date());
    }

}
