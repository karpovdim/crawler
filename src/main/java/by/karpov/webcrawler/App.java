package by.karpov.webcrawler;

import by.karpov.webcrawler.entity.Page;
import by.karpov.webcrawler.service.SpiderBotImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static by.karpov.webcrawler.util.WriterUtil.writeCSVFile;

public class App {

    private final static String START_URL = "https://news.tut.by/";
    private final static String YES = "^[y|Y]$";
    private final static String TEST = "test";

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        SpiderBotImpl spiderBot = new SpiderBotImpl();
        spiderBot.setStarUrl(START_URL);

        System.out.println("default URL (" + START_URL + ") will be used for demonstration \n");
        List<String> keyWordList = new ArrayList<>();
        System.out.println("Insert keyword (for example 'Афиша', 'июнь', 'выборы', 'лето', 'вода')," +
                "\n or enter a 'test' to automatically fill a list of keywords.");
        String keyWord = "";
        while (!keyWord.matches(YES) && !keyWord.equals(TEST)) {
            keyWord = in.nextLine();
            // automatically fill a list of keywords
            if (keyWord.equals(TEST)) {
                keyWordList.add("Афиша");
                keyWordList.add("июнь");
                keyWordList.add("выборы");
                keyWordList.add("лето");
                keyWordList.add("вода");
            }
            // fill a list of keywords
            if (!keyWord.matches(YES) & !keyWord.equals(TEST)) {
                if (!keyWord.equals("")) {
                    keyWordList.add(keyWord);
                }
                System.out.println("Insert key word or 'Y' to continue.");
            }

        }
        System.out.println("Enter the limit of processed pages.");
        int maxAmountPages = in.nextInt();
        // set max amount pages
        spiderBot.setMaxAmountPages(maxAmountPages);

        List<String> stringsForCSV = new ArrayList<>(maxAmountPages + 1);
        stringsForCSV.add("URL, [" + keyWordList.toString() + "]");
        List<Page> pageList = spiderBot.getPageList();
        Map<String, String> shortInfoMap;
        int count = 0;
        // fill short info map
        for (Page page : pageList) {
            shortInfoMap = spiderBot.getShortInfo(page, keyWordList);
            shortInfoMap.forEach((k, v) -> System.out.println(k + " " + v));
            stringsForCSV.add(page.getUrl() + ", " + shortInfoMap.values());
            count++;
        }

        System.out.println("Choose a number from 1 to " + count + " for clarification.");
        int number = in.nextInt();
        // get content for selected page and prepare file to save
        Page page = pageList.get(number - 1);
        System.out.println("Clarification for: " + page.getUrl());
        Map<String, Long> amountMatches = spiderBot.getAmountMatches(keyWordList, spiderBot.getWordList(page));
        amountMatches.forEach((k, v) -> System.out.println(k + " - " + v + " hits"));

        String fileName = "";
        while (fileName.isBlank()) {
            fileName = in.nextLine();
            System.out.println("Insert file name to save: ");
        }
        // save file
        writeCSVFile(fileName, stringsForCSV);
        System.out.println("File save as - " + "\"" + fileName + ".txt" + "\"");
        in.close();
    }

}

