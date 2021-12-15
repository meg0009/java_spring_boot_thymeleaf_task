package com.chivapchichi.model;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Person implements Serializable {

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
    private int salary = 0;

    @NonNull
    @Email
    private String email = "";

    @NonNull
    private String organization = "";

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
}
