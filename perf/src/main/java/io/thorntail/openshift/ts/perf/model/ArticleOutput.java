package io.thorntail.openshift.ts.perf.model;

public class ArticleOutput {
    private final int id;

    private final String published;

    private final String text;

    private final String author;

    private final String writtenFor;

    public ArticleOutput(int id, String published, String text, String author, String writtenFor) {
        this.id = id;
        this.published = published;
        this.text = text;
        this.author = author;
        this.writtenFor = writtenFor;
    }

    public static ArticleOutput fromArticle(Article article) {
        return new ArticleOutput(
                article.getId(),
                article.getPublished(),
                article.getText().substring(0, 100) + "...",
                article.getAuthor().getFullName(),
                article.getWrittenFor().getName()
        );
    }

    public int getId() {
        return id;
    }

    public String getPublished() {
        return published;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getWrittenFor() {
        return writtenFor;
    }
}
