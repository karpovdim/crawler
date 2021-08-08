package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Component
@RequiredArgsConstructor
public class KeywordService {
    private  String YES= "^[y|Y]$"; //name
    private final static String TEST = "test";
    private  final Scanner scanner;

    public List<String> getKeyWordList(){
        List<String> keyWordList = new ArrayList<>();
        System.out.println("Insert keyword (for example 'Афиша', 'июнь', 'выборы', 'лето', 'вода')," +
                "\n or enter a 'test' to automatically fill a list of keywords.");
        String keyWord = "";
        while (!keyWord.matches(YES) && !keyWord.equals(TEST)) {
            keyWord = scanner.nextLine().toLowerCase();
            // automatically fill a list of keywords
            if (keyWord.equals(TEST)) {
                keyWordList.add("кино");
                keyWordList.add("лето");
                keyWordList.add("август");
                keyWordList.add("end");
                keyWordList.add("home");
            }
            // fill a list of keywords
            if (!keyWord.matches(YES) & !keyWord.equals(TEST)) {
                if (!keyWord.equals("")) {
                    keyWordList.add(keyWord);
                }
                System.out.println("Insert key word or 'Y' to continue.");
            }

        }
return keyWordList;
    }

}
