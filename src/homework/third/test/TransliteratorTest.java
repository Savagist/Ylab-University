package homework.third.test;

import homework.third.transliterator.Transliterator;
import homework.third.transliterator.TransliteratorUseArrayImpl;
import homework.third.transliterator.TransliteratorUseMapImpl;

public class TransliteratorTest {
    public static void main(String[] args) {
        // Реализация с использованием массива
        Transliterator transUseArray = new TransliteratorUseArrayImpl();
        // Реализация с использованием мапы
        Transliterator transUseMap = new TransliteratorUseMapImpl();
        String res = transUseArray.transliterate("НОВОЕ qwerty  123 новое ДОЖДЬ  сообщение №!$%^ СООБЩЕНИЕ...");
        String res1 = transUseMap.transliterate("НОВОЕ qwerty 123 новое ДОЖДЬ сообщение №!$%^ СООБЩЕНИЕ...");
        System.out.println(res);
        System.out.println(res1);
    }
}

