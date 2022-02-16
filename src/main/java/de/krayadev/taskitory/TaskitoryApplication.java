package de.krayadev.taskitory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={"de.krayadev.taskitory"})
public class TaskitoryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TaskitoryApplication.class, args);
    }

}
