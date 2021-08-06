package by.karpov.webcrawler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class Config {
    @Bean
    public Scanner getScanner(){
        return new Scanner(System.in);
    }
}
