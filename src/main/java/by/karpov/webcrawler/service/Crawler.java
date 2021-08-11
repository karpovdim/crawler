package by.karpov.webcrawler.service;

import by.karpov.webcrawler.entity.Page;
import by.karpov.webcrawler.util.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class Crawler {
    private final Writer writer;
    private final SpiderBot<Page> spiderBot;
    private final Scanner scanner;
    private final DepthService depthService;
    private final KeywordService keywordService;
    private final FilenameService filenameService;

    public void process(String startUrl) {
        var keyWordList = keywordService.getKeyWordList();
        var depth = depthService.getDepth();
        List<String> stringsForCSV = new ArrayList<>(depth + 1);
        stringsForCSV.add("URL, [" + keyWordList.toString() + "]");
        List<Page> pageList = spiderBot.getPageList(startUrl, depth);
        Map<String, String> shortInfoMap;
        int count = 0;
        for (Page page : pageList) {
            shortInfoMap = spiderBot.getShortInfo(page, keyWordList);
            shortInfoMap.forEach((k, v) -> System.out.println(k + " " + v));
            stringsForCSV.add(page.getUrl() + ", " + shortInfoMap.values());
            count++;
        }

        System.out.printf("Choose a number from 1 to %s for clarification.  ", count);
        int number = scanner.nextInt();
        Page page = pageList.get(number - 1);
        System.out.printf("Clarification for: %s \n", page.getUrl());
        Map<String, Long> amountMatches = spiderBot.getAmountMatches(keyWordList, spiderBot.getWordList(page));
        amountMatches.forEach((k, v) -> System.out.printf("%-10s -  hits %-3d; \n", k, v));

        writer.writeCSVFile(filenameService.getFilename(), stringsForCSV);

    }
}
