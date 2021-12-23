package com.chivapchichi.controler;

import com.chivapchichi.filerw.PersonReaderWriter;
import com.chivapchichi.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GetPersonController {
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
        Person p = PersonReaderWriter.readOne(person);
        if (p != null) {
            model.addAttribute("person", p);
            return "get-result";
        }

        return "not-found";
    }
}
