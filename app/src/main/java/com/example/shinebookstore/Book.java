package com.example.shinebookstore;

public class Book {
    private int bookId;
    private String name;
    private String ISBN;
    private String author;
    private String price;
    private String title;
    private String imageURL;
    private int bookPurchased;


    public Book() {
    }

    public Book(int bookId, String name, String ISBN, String author, String price, String title, String imageURL, int bookPurchased) {
        this.bookId = bookId;
        this.name = name;
        this.ISBN = ISBN;
        this.author = author;
        this.price = price;
        this.title = title;
        this.imageURL = imageURL;
        this.bookPurchased =bookPurchased;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getBookPurchased() {
        return bookPurchased;
    }

    public void setBookPurchased(int bookPurchased) {
        this.bookPurchased = bookPurchased;
    }
}
