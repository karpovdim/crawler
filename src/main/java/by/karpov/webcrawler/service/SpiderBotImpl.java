package by.karpov.webcrawler.service;

import by.karpov.webcrawler.entity.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SpiderBotImpl implements SpiderBot<Page> {

    private final DocumentService documentService;
    private final GetLinkService getLinkService;

    private static final String NOT_EMPTY_ROW = "^.+$";


    public List<Page> getPageList(String startUrl, int depth) {
        List<Page> pageList = new ArrayList<>();
        List<String> urlList = getURLList(startUrl, depth);
        for (String url : urlList) {
            List<String> lineList = getLineList(url);
            pageList.add(new Page.Builder().setUrl(url).setLines(lineList).build());
        }
        return pageList;
    }

    public Map<String, String> getShortInfo(Page page, List<String> keyWordList) {
        Map<String, String> shortInfoMap = new HashMap<>();
        List<String> wordList = getWordList(page);
        Map<String, Long> amountMatches = getAmountMatches(keyWordList, wordList);
        shortInfoMap.put(page.getUrl(), amountMatches.values().toString());
        return shortInfoMap;
    }

    public List<String> getWordList(Page page) {
        return Arrays.asList(page.getLines().toString().split(" "));
    }

    public Map<String, Long> getAmountMatches(List<String> keyWordList, List<String> wordList) {
        Map<String, Long> keyWordMap = new HashMap<>();
        for (String word : keyWordList) {
            long count = wordList.stream()
                    .filter(w -> w.matches(".*" + word + ".*"))
                    .count();
            keyWordMap.put(word, count);
        }
        return keyWordMap;
    }

    private List<String> getURLList(String startUrl, int depth) {
        ArrayList<String> listLinks = new ArrayList<>();
        return getLinkService.crawler(depth, 0, startUrl, listLinks);
    }

    private List<String> getLineList(String url) {
        Document document = this.documentService.getDocument(url);
        return document.select("div")
                .stream()
                .map(Element::text)
                .filter(c -> c.matches(NOT_EMPTY_ROW))
                .collect(Collectors.toList());
    }
    public void setStarUrl(String startUrl) {
    }
    public void setMaxAmountPages(int maxMountPage) {
    }
    public List<Page> getPageList() {
        return Collections.emptyList();
}
}
