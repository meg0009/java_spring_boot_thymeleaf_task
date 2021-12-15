package com.chivapchichi.controler;

import com.chivapchichi.Main;
import com.chivapchichi.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonController {
    static String CLASSPATH;

    {
        String str = Main.class.getResource("Main.class").getPath();
        CLASSPATH = str.substring(0, str.indexOf("Main.class")) + "/person.ser";
    }

    @GetMapping("/person")
    public String personForm(Model model) {
        Person res = null;
        try (ObjectInputStream in =
                new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(CLASSPATH)))) {
            res = (Person) in.readObject();
        } catch (IOException | ClassNotFoundException exp) {
            res = new Person();
        }

        model.addAttribute("person", res);
        return "form";
    }

    @PostMapping("/person")
    public String personSubmit(@ModelAttribute @Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        List<Person> list = new ArrayList<>();
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     new FileInputStream(CLASSPATH)))) {
            Person p;
            while ((p = (Person) in.readObject()) != null) {
                list.add(p);
            }
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException | ClassNotFoundException exp) {
            exp.printStackTrace();
        }

        try (ObjectOutputStream out =
                new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(CLASSPATH)))) {
            for (Object obj: list) {
                out.writeObject(obj);
            }
            out.writeObject(person);
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return "result";
    }
}
