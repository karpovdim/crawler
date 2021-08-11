package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class KeywordService {
    private final Scanner scanner;

    public List<String> getKeyWordList() {
        List<String> keyWordList = new ArrayList<>();
        System.out.println("Insert keyword (for example 'Афиша', 'июнь', 'выборы', 'лето', 'вода')," +
                "\n or enter a 'test' to automatically fill a list of keywords.");
        String keyWord = "";
        String yes = "y";
        String test = "test";
        while (!keyWord.equals(yes) && !keyWord.equals(test)) {
            keyWord = scanner.nextLine().toLowerCase();
            if (keyWord.equals(test)) {
                keyWordList.add("кино");
                keyWordList.add("лето");
                keyWordList.add("август");
                keyWordList.add("end");
                keyWordList.add("home");
            }
            // fill a list of keywords
            if (!keyWord.matches(yes) & !keyWord.equals(test)) {
                if (!keyWord.equals("")) {
                    keyWordList.add(keyWord);
                }
                System.out.println("Insert key word or 'Y' to continue.");
            }
        }
        return keyWordList;
    }
}
