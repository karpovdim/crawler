package by.karpov.webcrawler;

import by.karpov.webcrawler.entity.Page;
import by.karpov.webcrawler.service.SpiderBotImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(value = JUnit4.class)
public class SpiderBotImplTest {

    private final static String START_URL = "https://news.tut.by/";
    private final static int MAX_MOUNT_PAGE = 5;
    private final SpiderBotImpl spiderBot = new SpiderBotImpl();
    private final List<String> keyWords = new ArrayList<>();
    private final List<String> wordList = new ArrayList<>();

    @Before
    public void setUp() {
        spiderBot.setStarUrl(START_URL);
        spiderBot.setMaxAmountPages(MAX_MOUNT_PAGE);

        keyWords.add("TEST");

        wordList.add("A");
        wordList.add("TEST");
        wordList.add("TEST");
        wordList.add("B");
    }

    @Test
    public void getPageListTest() throws IOException {
        List<Page> pageList = spiderBot.getPageList();
        int exceptedSize = 5;
        int actualSize = pageList.size();

        Assert.assertNotNull(pageList);
        Assert.assertFalse(pageList.isEmpty());
        Assert.assertEquals(exceptedSize, actualSize);
    }

    @Test
    public void getShortInfoTest() throws IOException {
        Page page = spiderBot.getPageList().get(1);
        Map<String, String> shortInfoMap = spiderBot.getShortInfo(page, keyWords);
        int exceptedSize = 1;
        int actualSize = shortInfoMap.size();
        boolean containsKey = shortInfoMap.containsKey(page.getUrl());

        Assert.assertTrue(containsKey);
        Assert.assertNotNull(shortInfoMap);
        Assert.assertEquals(exceptedSize, actualSize);
    }

    @Test
    public void getWordListTest() throws IOException {
        Page page = spiderBot.getPageList().get(1);
        List<String> wordList = spiderBot.getWordList(page);

        Assert.assertNotNull(wordList);
        Assert.assertFalse(wordList.isEmpty());
    }

    @Test
    public void getAmountMatchesTest() {
        Map<String, Long> amountMatches = spiderBot.getAmountMatches(keyWords, wordList);
        String exceptedKeyAndValue = "TEST 2";
        String actualKeyAndValue = "";
        for (Map.Entry<String, Long> entry : amountMatches.entrySet()) {
            actualKeyAndValue = entry.getKey() + " " + entry.getValue();
        }

        Assert.assertNotNull(amountMatches);
        Assert.assertFalse(amountMatches.isEmpty());
        Assert.assertEquals(exceptedKeyAndValue, actualKeyAndValue);
    }
}
