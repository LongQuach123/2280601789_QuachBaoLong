package com.example.demo.model;

public class Book {
    private Long id;
    private String title;
    private String author;

    // 1. Constructor không tham số (Bắt buộc cho Thymeleaf)
    public Book() {
    }

    // 2. Constructor có tham số
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // 3. Getter và Setter (Giúp Service và Controller hết đỏ)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}