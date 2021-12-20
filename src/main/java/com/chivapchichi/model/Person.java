package com.chivapchichi.model;

import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;

public class Person {
    public static File CLASSPATH;

    static {
        try {
            CLASSPATH = new ClassPathResource("persons.csv").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Size(min = 3, max = 30)
    private String firstName = "";

    @NonNull
    @Size(min = 3, max = 30)
    private String lastName = "";

    @Size(min = 3, max = 30)
    private String patronymic = ""; // отчество

    @NonNull
    private int age = 0;

    @NonNull
    @Min(0)
    private int salary = 0;

    @NonNull
    @Email
    private String email = "";

    @NonNull
    private String organization = "";

    public Person() {
    }

    public Person(@NonNull String firstName, @NonNull String lastName, String patronymic, int age, int salary, @NonNull String email, @NonNull String organization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.age = age;
        this.salary = salary;
        this.email = email;
        this.organization = organization;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return /*age == person.age && salary == person.salary && */firstName.equals(person.firstName) && lastName.equals(person.lastName)/* && Objects.equals(patronymic, person.patronymic) && email.equals(person.email) && organization.equals(person.organization)*/;
    }
}
