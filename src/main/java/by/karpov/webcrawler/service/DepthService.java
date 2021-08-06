package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DepthService {
    private  final Scanner scanner;
    public int getDepth(){
        System.out.println("Enter the limit of processed pages.");
        return scanner.nextInt();
    }
}
