package com.example.details;

public class BookDetailsDTO {
    private Long id;
    private String author;
    private String title;
    private String type;
    private String publisher;
    private int year;
    private String language;
    private String isbn;
    private int pages;

    public BookDetailsDTO(Long id, String author, String title, String type, String publisher, int year, String language, String isbn, int pages) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.type = type;
        this.publisher = publisher;
        this.year = year;
        this.language = language;
        this.isbn = isbn;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
