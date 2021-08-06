package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class FilenameService {
    private final Scanner scanner;
    public  String getFilename(){
        String fileName = "";
        while (fileName.isBlank()) {
            fileName = scanner.nextLine();
            System.out.println("Insert file name to save: ");

        }
        return fileName;
    }
}
