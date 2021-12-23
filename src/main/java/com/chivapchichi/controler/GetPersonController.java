package com.chivapchichi.controler;

import com.chivapchichi.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class GetPersonController {
    static File CLASSPATH = Person.CLASSPATH;
    static final Parser UA_PARSER = new Parser();

    @GetMapping("/get-person")
    public String getPerson(Model model){
        model.addAttribute("foundPerson", new Person());
        return "found";
    }

    @PostMapping("/get-person")
    public String retPerson(@ModelAttribute Person person, Model model, HttpServletRequest req) {
        String strUserAgent = req.getHeader("User-Agent");
        Client client = UA_PARSER.parse(strUserAgent);
        String browser = client.userAgent.family + ' ' + client.userAgent.major;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd MMMMMMMMM yyyy HH:mm:ss");
        String time = df.format(date);
        model.addAttribute("browser", browser);
        model.addAttribute("time", time);
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
                if (person.equals(p)) {
                    model.addAttribute("person", p);
                    return "get-result";
                }
            }
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return "not-found";
    }
}
