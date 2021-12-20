package com.chivapchichi.controler;

import com.chivapchichi.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class AddPersonController {
    static File CLASSPATH = Person.CLASSPATH;

    @GetMapping("/add-person")
    public String personForm(Model model) {
        /*Person res = null;
        try (FileReader in = new FileReader(CLASSPATH)) {
            Iterator<CSVRecord> records = CSVFormat.Builder
                    .create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setDelimiter(';')
                    .build()
                    .parse(in)
                    .iterator();
            if (records.hasNext()) {
                CSVRecord record = records.next();
                String firstName = record.get("firstName");
                String lastName = record.get("lastName");
                String patronymic = record.get("patronymic");
                int age = Integer.parseInt(record.get("age"));
                int salary = Integer.parseInt(record.get("salary"));
                String email = record.get("email");
                String organization = record.get("organization");
                res = new Person(firstName, lastName, patronymic, age, salary, email, organization);
            } else {
                res = new Person();
            }
        } catch (IOException exp) {
            res = new Person();
        }
*/
        model.addAttribute("person", new Person());
        return "add-form";
    }

    @PostMapping("/add-person")
    public String personSubmit(@ModelAttribute @Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "add-form";
        }

        /*List<Person> list = new ArrayList<>();
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
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException exp) {
            exp.printStackTrace();
        }*/

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

        return "result";
    }
}
