package Krayadev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={"Krayadev"})
public class TaskitoryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TaskitoryApplication.class, args);
    }

}
