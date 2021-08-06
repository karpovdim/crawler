package by.karpov.webcrawler;

import by.karpov.webcrawler.service.Crawler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        AnnotationConfigApplicationContext cxt = new AnnotationConfigApplicationContext();
        cxt.scan("by.karpov.webcrawler");
        cxt.refresh();
        var startUrl = args[0];
        System.out.println("default URL (" + startUrl + ") will be used for demonstration \n");
        var crawler = cxt.getBean(Crawler.class);
        crawler.process(startUrl);

    }

}

