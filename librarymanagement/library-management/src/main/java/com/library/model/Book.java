package com.library.model;

public class Book {

  private int bookId;
  private String title;
  private String isbn;
  private String publisher;
  private int publicationYear;
  private int totalCopies;
  private int availableCopies;
  private int categoryId;

  public Book() {
  }

  public Book(int bookId, String title, String isbn,
      String publisher, int publicationYear,
      int totalCopies, int availableCopies,
      int categoryId) {

    this.bookId = bookId;
    this.title = title;
    this.isbn = isbn;
    this.publisher = publisher;
    this.publicationYear = publicationYear;
    this.totalCopies = totalCopies;
    this.availableCopies = availableCopies;
    this.categoryId = categoryId;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public int getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
  }

  public int getTotalCopies() {
    return totalCopies;
  }

  public void setTotalCopies(int totalCopies) {
    this.totalCopies = totalCopies;
  }

  public int getAvailableCopies() {
    return availableCopies;
  }

  public void setAvailableCopies(int availableCopies) {
    this.availableCopies = availableCopies;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }
}