package by.karpov.webcrawler.service;

import by.karpov.webcrawler.entity.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor

/**
 * This class using the Jsoup library collects data at the specified URL.
 */

public class SpiderBotImpl implements SpiderBot<Page> {

    /**
     * @value - this is RegEx for filters URL
     */
    private static final String URL_REG_EX = "^(http|https)://.+\\.html$";
    /**
     * @value - this is RegEx filters empty lines
     */
    private static final String NOT_EMPTY_ROW = "^.+$";

    private int maxAmountPages;
    private String starUrl;

    /**
     * @return returns a list of {@link Page} with a limited number of URL.
     * @throws IOException - if a Document could not be created.
     */
    public List<Page> getPageList() throws IOException {
        List<Page> pageList = new ArrayList<>();
        List<String> urlList = getURLList();
        for (String url : urlList) {
            List<String> lineList = getLineList(url);
            pageList.add(new Page.Builder().setUrl(url).setLines(lineList).build());
        }
        return pageList;
    }

    /**
     *
     * @param page {@link Page}
     * @param keyWordList - search keyword
     * @return returns a dictionary where the key is URL
     * and the value is the number of matches for keywords
     */
    public Map<String, String> getShortInfo(Page page, List<String> keyWordList) {
        Map<String, String> shortInfoMap = new HashMap<>();
        List<String> wordList = getWordList(page);
        Map<String, Long> amountMatches = getAmountMatches(keyWordList, wordList);
        shortInfoMap.put(page.getUrl(), amountMatches.values().toString());
        return shortInfoMap;
    }

    /**
     *
     * @param page {@link Page}
     * @return returns list of words.
     */
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

    /**
     * @return returns a list of URL with a limited number of @see maxAmountPages.
     * @throws IOException - if a Document could not be created.
     */
    private List<String> getURLList() throws IOException {
        Document document = Jsoup.connect(starUrl)
                .userAgent("Chrome/81.0.4044.138")
                .get();
        return document
                .select("a")
                .stream()
                .map(c -> c.attr("href"))
                .filter(c -> c.matches(URL_REG_EX))
                .distinct()
                .limit(this.maxAmountPages)
                .collect(Collectors.toList());
    }

    /**
     * @param url this is parameter to get Document.
     * @return returns a list of data rows.
     * @throws IOException - if a Document could not be created.
     */
    private List<String> getLineList(String url) throws IOException {
        Document document = Jsoup.connect(url)
                .userAgent("Chrome/81.0.4044.138")
                .get();
        return document.select("a")
                .stream()
                .map(Element::text)
                .filter(c -> c.matches(NOT_EMPTY_ROW))
                .collect(Collectors.toList());
    }
}
