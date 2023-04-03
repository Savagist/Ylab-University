package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;

import io.ylab.intensive.lesson03.filesort.Generator;
import io.ylab.intensive.lesson04.DbUtil;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = initDb();
        int size = 10000; // Количество элементов для сортировки
        File data = new Generator().generate("src" + File.separator + "main" + File.separator + "resources" + File.separator + "unsorted.txt", size);
        FileSorter fileSorter = new FileSortImpl(dataSource);
        System.out.println("Начало сортировки с batch-processing: " + new Date());
        //Параметр batchFlag в функции sort включает или выключает сортировку с batch-processing: true - включена, false - выключена
        fileSorter.sort(data, true);
        System.out.println("Конец сортировки с batch-processing: " + new Date());
        System.out.println("Начало сортировки без batch-processing: " + new Date());
        fileSorter.sort(data, false);
        System.out.println("Конец сортировки без batch-processing: " + new Date());
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
