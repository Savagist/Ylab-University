package io.ylab.intensive.lesson03.orgstructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {
    private static final String DELIMITER = ";";

    @Override
    public Employee parseStructure(File csvFile) {
        List<Employee> employees = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(csvFile)) {
            Scanner scanner = new Scanner(fileInputStream);
            String stingFromFile;
            while (scanner.hasNextLine()) {
                stingFromFile = scanner.nextLine();
                //Пропускаем первую строку
                if (stingFromFile.contains("id")) {
                    continue;
                }
                String[] fields = stingFromFile.split(DELIMITER);
                Long id = Long.parseLong(fields[0]);
                Long bossId = fields[1].isEmpty() ? null : Long.parseLong(fields[1]); // Если поле пустое вернет null или значение
                String name = fields[2];
                String position = fields[3];

                Employee employee = new Employee();
                employee.setId(id);
                employee.setBossId(bossId);
                employee.setName(name);
                employee.setPosition(position);
                employees.add(employee);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Employee mainBoss = null;
        for (Employee boss : employees) {
            if (boss.getBossId() == null) {
                mainBoss = boss;
            }
            for (Employee employee : employees) {
                if (Objects.equals(boss.getId(), employee.getBossId())) {
                    boss.getSubordinate().add(employee);
                    employee.setBoss(boss);
                }
            }
        }
        return mainBoss;
    }
}
