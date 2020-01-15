package io.thorntail.openshift.ts.perf.model;

public class Article {

    private int id;

    private String text;

    private String published;

    private Person author;

    private Company writtenFor;

    public Article() {
    }

    public Article(String text, String published, Person author, Company writtenFor) {
        this.text = text;
        this.published = published;
        this.author = author;
        this.writtenFor = writtenFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Company getWrittenFor() {
        return writtenFor;
    }

    public void setWrittenFor(Company writtenFor) {
        this.writtenFor = writtenFor;
    }
}

