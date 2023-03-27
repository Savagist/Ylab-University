package io.ylab.intensive.lesson03.test;

import io.ylab.intensive.lesson03.orgstructure.OrgStructureParserImpl;
import io.ylab.intensive.lesson03.orgstructure.Employee;
import io.ylab.intensive.lesson03.orgstructure.OrgStructureParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrgStructureTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParser parser = new OrgStructureParserImpl();
        String separator = File.separator;
        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "organization.csv";
        Employee boss = parser.parseStructure(new File(path));

        // Вывод генерального директора
        System.out.println("БОСС: " + boss.getName() + " (" + boss.getPosition() + ")");

        // Вывод подчиненых генерального директора
        List<Employee> subordinates = boss.getSubordinate();
        for (Employee subordinate : subordinates) {
            System.out.println("ПРЯМОЙ ПОДЧИНЕННЫЙ: " + subordinate.getName() + " (" + subordinate.getPosition() + ")");
            // Вывод второстепенного подчиненого генерального директора
            System.out.print("      ВТОРОСТЕПЕННОЕ ПОДЧИНЕНИЕ: ");
            for (Employee e : subordinate.getSubordinate()) {
                System.out.print("  " + e.getName() + " (" + e.getPosition() + ")");
            }
            System.out.println();
        }
    }
}
