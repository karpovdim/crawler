package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DepthService {
    private final Scanner scanner;

    public int getDepth() {
        int limitPages;
        System.out.println("Enter the limit of processed pages.");
        do {
            System.out.println("Please enter a positive number!");
            while (!scanner.hasNextInt()) {
                System.out.println("That not a number!\nPlease enter a positive number!");
                scanner.next(); // this is important!
            }
            limitPages = scanner.nextInt();
        } while (limitPages <= 0);
        return limitPages;
    }
}
