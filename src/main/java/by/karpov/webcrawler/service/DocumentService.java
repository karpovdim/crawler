package by.karpov.webcrawler.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocumentService {

    public Document getDocument(String url) throws IOException {
      //  try {
            Connection connection = Jsoup.connect(url);
            Document document = connection
                    .userAgent("Chrome/81.0.4044.138")
                    .get();
     //       if (200 != connection.response().statusCode()) break;
                return document;
//            }
//            return null;
//        } catch (IOException ioException) {
//            return null;
//        }
    }
}
