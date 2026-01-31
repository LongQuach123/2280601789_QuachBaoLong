package com.example.demo.model;

public class Book {
    private int id;
    private String title;
    private String author;

    // 1. Constructor rỗng (Bắt buộc)
    public Book() {
    }

    // 2. Constructor đầy đủ tham số
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // 3. Getter và Setter (Viết thủ công thay cho @Data)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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