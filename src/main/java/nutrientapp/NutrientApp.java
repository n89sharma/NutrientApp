package nutrientapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:3000")
@ComponentScan
public class NutrientApp {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NutrientApp.class, args);
    }
}
