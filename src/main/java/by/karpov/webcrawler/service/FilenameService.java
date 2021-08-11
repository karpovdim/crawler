package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
@Component
@RequiredArgsConstructor
public class FilenameService {
    private final Scanner scanner;
    public  String getFilename() {
        String fileName = "";
        while (fileName.isBlank()) {
            fileName = scanner.nextLine().toLowerCase();
            System.out.println("Insert file name to save: ");
        }
        while (Files.exists(Paths.get(fileName))) {
             fileName = fileName + "1";
        }
        return fileName;
    }
}
