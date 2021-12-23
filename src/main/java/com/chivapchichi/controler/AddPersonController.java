package com.chivapchichi.controler;

import com.chivapchichi.filerw.PersonReaderWriter;
import com.chivapchichi.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AddPersonController {
    @GetMapping("/add-person")
    public String personForm(Model model) {
        model.addAttribute("person", new Person());
        return "add-form";
    }

    @PostMapping("/add-person")
    public String personSubmit(@ModelAttribute @Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "add-form";
        }

        PersonReaderWriter.writeOne(person);

        return "result";
    }
}
