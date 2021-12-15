package com.chivapchichi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Main {

    public static void main(String[] args) {
	// write your code here
        SpringApplication.run(Main.class, args);
    }
}
