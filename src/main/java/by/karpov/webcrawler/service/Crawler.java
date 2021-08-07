package by.karpov.webcrawler.service;

import by.karpov.webcrawler.entity.Page;
import by.karpov.webcrawler.util.Writer;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class Crawler {
    private  final Writer writer;
    private  final SpiderBot spiderBot;
    private  final Scanner scanner;
    private  final DepthService depthService;
    private  final KeywordService keywordService;
    private  final FilenameService filenameService;

    public void process(String startUrl){
        var keyWordList = keywordService.getKeyWordList();
        var depth = depthService.getDepth();
        List<String> stringsForCSV = new ArrayList<>(depth + 1);
        stringsForCSV.add("URL, [" + keyWordList.toString() + "]");
        List<Page> pageList = spiderBot.getPageList(startUrl,depth);
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
        int number = scanner.nextInt();
        // get content for selected page and prepare file to save
        Page page = pageList.get(number - 1);
        System.out.println("Clarification for: " + page.getUrl());
        Map<String, Long> amountMatches = spiderBot.getAmountMatches(keyWordList, spiderBot.getWordList(page));
        amountMatches.forEach((k, v) -> System.out.println(k + " - " + v + " hits"));


        // save file
        writer.writeCSVFile(filenameService.getFilename(), stringsForCSV);

    }
}
