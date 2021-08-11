package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DocumentService {

    public Document getDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            return connection
                    .userAgent("Chrome/81.0.4044.138")
                    .get();
        } catch (UnsupportedMimeTypeException exception) {
            return null;
        } catch (IOException exception) {
            System.out.println("bad connection");
            return null;
        }
    }
}
