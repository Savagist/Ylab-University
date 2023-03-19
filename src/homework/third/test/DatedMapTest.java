package homework.third.test;

import homework.third.datedmap.DatedMap;
import homework.third.datedmap.DatedMapImpl;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap map = new DatedMapImpl();
        System.out.println("Множество ключей = "+ map.keySet());
        System.out.println("Cоздание ключа 'first' со значением '1'");
        map.put("first", "1");
        System.out.println("Спим 5 секунд");
        Thread.sleep(5000);
        System.out.println("Cоздание ключа 'second' со значением '2'");
        map.put("second", "2");
        System.out.println("Спим 5 секунд");
        Thread.sleep(5000);
        System.out.println("Cоздание ключа 'third' со значением '3'");
        map.put("third", "3");
        System.out.println("Множество ключей = "+ map.keySet());
        System.out.println("Значение ключа 'first' = " + map.get("first"));
        System.out.println("Значение ключа 'second' = " + map.get("second"));
        System.out.println("Дата создания 'first' = " + map.getKeyLastInsertionDate("first"));
        System.out.println("Дата создания 'third' = " + map.getKeyLastInsertionDate("third"));
        System.out.println("Содержит ли мап ключ: 'first' = " + map.containsKey("first"));
        System.out.println("Удаление ключа 'first'");
        map.remove("first");
        System.out.println("Содержит ли мап ключ: 'first' = " + map.containsKey("first"));
        System.out.println("Значение ключа 'first' = " + map.get("first"));
        System.out.println("Дата создания 'first' = " + map.getKeyLastInsertionDate("first"));
        System.out.println("Дата создания 'second' = " + map.getKeyLastInsertionDate("second"));
        System.out.println("Дата создания 'third' = " + map.getKeyLastInsertionDate("third"));
        System.out.println("Спим 5 секунд");
        Thread.sleep(5000);
        System.out.println("Cоздание ключа 'third' со значением '4'");
        map.put("third","4");
        System.out.println("Значение ключа 'third' = " + map.get("third"));
        System.out.println("Дата создания 'third' = " + map.getKeyLastInsertionDate("third"));
        System.out.println("Множество ключей = "+ map.keySet());

    }
}
