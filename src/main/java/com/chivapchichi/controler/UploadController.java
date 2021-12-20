package com.chivapchichi.controler;

import com.chivapchichi.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {
    @GetMapping("/upload-from-file")
    public String uploadFromFile(Model model) {
        model.addAttribute("file", new StringBuilder());
        return "upload-from-file";
    }

    @PostMapping("/upload-from-file")
    public String uploadSubmit(@ModelAttribute StringBuilder sss) {
        String file = sss.toString();
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
            for (CSVRecord record: records) {
                String firstName = record.get("firstName");
                String lastName = record.get("lastName");
                String patronymic = record.get("patronymic");
                int age = Integer.parseInt(record.get("age"));
                int salary = Integer.parseInt(record.get("salary"));
                String email = record.get("email");
                String organization = record.get("organization");
                Person p = new Person(firstName, lastName, patronymic, age, salary, email, organization);
                list.add(p);
            }
        } catch (FileNotFoundException exp) {
            return "not-found";
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        try(FileWriter out = new FileWriter(Person.CLASSPATH, true);
            CSVPrinter printer = CSVFormat.Builder
                    .create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setDelimiter(';')
                    .build()
                    .print(out)
        ){
            for (Person p: list) {
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
        return "ok";
    }
}
