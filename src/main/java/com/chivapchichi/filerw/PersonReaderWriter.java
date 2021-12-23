package com.chivapchichi.filerw;

import com.chivapchichi.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonReaderWriter {
    public static File CLASSPATH;

    static {
        try {
            CLASSPATH = new ClassPathResource("persons.csv").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Person parseCSVRecords(CSVRecord record) {
        String firstName = record.get("firstName");
        String lastName = record.get("lastName");
        String patronymic = record.get("patronymic");
        int age = Integer.parseInt(record.get("age"));
        int salary = Integer.parseInt(record.get("salary"));
        String email = record.get("email");
        String organization = record.get("organization");
        return new Person(firstName, lastName, patronymic, age, salary, email, organization);
    }

    public static void writeOne(Person person) {
        try (FileWriter out = new FileWriter(CLASSPATH, true);
             CSVPrinter printer = CSVFormat.Builder
                     .create()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setDelimiter(';')
                     .build()
                     .print(out);
        ) {
            printer.printRecord(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getPatronymic(),
                    person.getAge(),
                    person.getSalary(),
                    person.getEmail(),
                    person.getOrganization()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Person readOne(Person person) {
        try (FileReader in = new FileReader(CLASSPATH);
             CSVParser records = CSVFormat.Builder
                     .create()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setDelimiter(';')
                     .build()
                     .parse(in);
        ) {
            for (CSVRecord record: records) {
                Person p = parseCSVRecords(record);
                if (person.equals(p)) {
                    return p;
                }
            }
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    public static boolean writeFromFile(MultipartFile inFile, String outFile) {
        try {
            File file = new File(CLASSPATH.getParentFile().toString() + '/' + outFile);
            inFile.transferTo(file);
            List<Person> list = new ArrayList<>();
            try (FileReader in = new FileReader(file);
                 CSVParser records = CSVFormat.Builder
                         .create()
                         .setHeader()
                         .setSkipHeaderRecord(true)
                         .setDelimiter(';')
                         .build()
                         .parse(in);
            ) {
                for (CSVRecord record : records) {
                    Person p = parseCSVRecords(record);
                    list.add(p);
                }
            } catch (FileNotFoundException exp) {
                return false;
            } catch (IOException exp) {
                exp.printStackTrace();
            }

            try (FileWriter out = new FileWriter(CLASSPATH, true);
                 CSVPrinter printer = CSVFormat.Builder
                         .create()
                         .setHeader()
                         .setSkipHeaderRecord(true)
                         .setDelimiter(';')
                         .build()
                         .print(out)
            ) {
                for (Person p : list) {
                    printer.printRecord(
                            p.getFirstName(),
                            p.getLastName(),
                            p.getPatronymic(),
                            p.getAge(),
                            p.getSalary(),
                            p.getEmail(),
                            p.getOrganization()
                    );
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
            file.delete();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return true;
    }
}
