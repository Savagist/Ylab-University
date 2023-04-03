package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        System.out.println("Инициализация первой мапы");
        persistentMap.init("Map1");
        System.out.println("Ключи в первой мапе: " + persistentMap.getKeys());
        System.out.println("Инициализация второй мапы");
        persistentMap.init("Map2");
        System.out.println("Ключи во второй мапе: " + persistentMap.getKeys());
        System.out.println("Переключение на первую мапу");
        persistentMap.init("Map1");
        System.out.println("Добавление в первую мапу ключа 'first' со значением '1'");
        persistentMap.put("first", "1");
        System.out.println("Добавление в первую мапу ключа 'second' со значением '2'");
        persistentMap.put("second", "2");
        System.out.println("Ключи в первой мапе: " + persistentMap.getKeys());
        persistentMap.put("third", "3");
        System.out.println("Добавление в первую мапу ключа 'third' со значением '3'");
        System.out.println("Переключение на вторую мапу");
        persistentMap.init("Map2");
        persistentMap.put("fourth", "4");
        System.out.println("Добавление во вторую мапу ключа 'fourth' со значением '4'");
        persistentMap.put("fifth", "5");
        System.out.println("Добавление во вторую мапу ключа 'fifth' со значением '5'");
        System.out.println("Ключи во второй мапе: " + persistentMap.getKeys());
        System.out.println("Добавление во вторую мапу ключа 'sixth' со значением '6'");
        persistentMap.put("sixth", "6");
        System.out.println("Переключение на первую мапу");
        persistentMap.init("Map1");
        System.out.println("Содержит ли первая мапа ключ 'third': " + persistentMap.containsKey("third"));
        System.out.println("Значение первой мапа по ключу 'third': " + persistentMap.get("third"));
        System.out.println("Ключи в первой мапе: " + persistentMap.getKeys());
        System.out.println("Удаление ключа 'first' из первой мапы");
        persistentMap.remove("first");
        System.out.println("Содержит ли первая мапа ключ 'first': " + persistentMap.containsKey("first"));
        System.out.println("Переключение на вторую мапу");
        persistentMap.init("Map2");
        System.out.println("Добавление во вторую мапу ключа 'first' со значением '1 in second map'");
        persistentMap.put("first", "1 in second map");
        System.out.println("Значение второй мапа по ключу 'first': " + persistentMap.get("first"));
        System.out.println("Ключи во второй мапе: " + persistentMap.getKeys());
        System.out.println("Удаляем все ключи из второй мапы");
        persistentMap.clear();
        System.out.println("Ключи во второй мапе: " + persistentMap.getKeys());
        System.out.println("Переключение на первую мапу");
        persistentMap.init("Map1");
        System.out.println("Добавление в первую мапу ключа 'third' со значением 'new 3'");
        persistentMap.put("third", "new 3");
        System.out.println("Ключи в первой мапе: " + persistentMap.getKeys());
        System.out.println("Значение первой мапа по ключу 'second': " + persistentMap.get("second"));
        System.out.println("Значение первой мапа по ключу 'third': " + persistentMap.get("third"));
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
