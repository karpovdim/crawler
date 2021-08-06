package by.karpov.webcrawler.service;

import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of entity
 */
public interface SpiderBot<T> {

    List<T> getPageList(String startUrl, int depth) ;

    Map<String, String> getShortInfo(T entity, List<String> stringList);

    List<String> getWordList(T entity);

    Map<String, Long> getAmountMatches(List<String> stringList, List<String> stringList1);
}
