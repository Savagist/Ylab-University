package io.ylab.intensive.lesson03.test;

import io.ylab.intensive.lesson03.transliterator.TransliteratorUseMapImpl;
import io.ylab.intensive.lesson03.transliterator.Transliterator;
import io.ylab.intensive.lesson03.transliterator.TransliteratorUseArrayImpl;

public class TransliteratorTest {
    public static void main(String[] args) {
        // Реализация с использованием массива
        Transliterator transUseArray = new TransliteratorUseArrayImpl();
        // Реализация с использованием мапы
        Transliterator transUseMap = new TransliteratorUseMapImpl();
        String res = transUseArray.transliterate("НОВОЕ qwerty 123 новое ДОЖДЬ сообщение №!$%^ СООБЩЕНИЕ...");
        String res1 = transUseMap.transliterate("НОВОЕ qwerty 123 новое ДОЖДЬ сообщение №!$%^ СООБЩЕНИЕ...");
        System.out.println(res);
        System.out.println(res1);
    }
}

