package by.karpov.webcrawler.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor

/**
 * This is simple entity
 * */
public class Page {

    /**
     * Html page URL from which the data is collected.
     */
    private String url;

    /**
     * Array of data rows.
     */
    private List<String> lines;

    public String getUrl() {
        return this.url;
    }

    public List<String> getLines() {
        return this.lines;
    }

    /**
     * Builder pattern implementation.
     */
    public static class Builder {

        private final Page page;

        public Builder() {
            page = new Page();
        }

        public Builder setUrl(String url) {
            page.url = url;
            return this;
        }

        public Builder setLines(List<String> lines) {
            page.lines = lines;
            return this;
        }

        public Page build() {
            return page;
        }
    }
}
