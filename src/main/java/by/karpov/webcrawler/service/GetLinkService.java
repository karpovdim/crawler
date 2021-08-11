package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetLinkService {
    private final DocumentService documentService;

    public List<String> crawler(int maxLevel, int curLevel, String url, List<String> visitedLink) {
        if (curLevel >= maxLevel || visitedLink.size() >= 10000) {
            return visitedLink;
        }
        Document document = documentService.getDocument(url);
        if (document == null) {
            return visitedLink;
        }
        visitedLink.add(url);
        Elements select = document.select("a[href]");
        for (Element link : select) {
            String nextLink = link.absUrl("href");
            if (!visitedLink.contains(nextLink)) {
                crawler(maxLevel, curLevel + 1, nextLink, visitedLink);
            }
        }
        return visitedLink;
    }
}
