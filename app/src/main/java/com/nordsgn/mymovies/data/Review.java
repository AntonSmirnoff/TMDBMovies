package com.nordsgn.mymovies.data;

public class Review {

    private String author;
    private String content;

    //добавляем конструктор alt+insert
    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    //добавляем гетер и сетеры

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
