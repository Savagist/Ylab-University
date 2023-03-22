package homework.third.test;

import homework.third.orgstructure.Employee;
import homework.third.orgstructure.OrgStructureParser;
import homework.third.orgstructure.OrgStructureParserImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrgStructureTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParser parser = new OrgStructureParserImpl();
        String separator = File.separator;
        // Файл находится по пути orgstructure с названием organization.csv
        String path = "src" + separator + "homework" + separator + "third" + separator + "orgstructure" + separator + "organization.csv";
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
