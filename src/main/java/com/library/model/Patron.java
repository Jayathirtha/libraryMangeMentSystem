package com.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patron {
    private String name;
    private String patronId; // Unique identifier for a patron
    private List<Book> borrowedBooks; // Books currently borrowed by this patron

    public Patron(String name, String patronId) {
        this.name = name;
        this.patronId = patronId;
        this.borrowedBooks = new ArrayList<>(); // Initialize an empty list for borrowed books
    }

    public String getName() {
        return name;
    }

    public String getPatronId() {
        return patronId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public boolean removeBorrowedBook(Book book) {
        if (book != null) {
            return borrowedBooks.remove(book);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        return Objects.equals(patronId, patron.patronId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patronId);
    }

    @Override
    public String toString() {
        return "Patron{" +
                "name='" + name + '\'' +
                ", patronId='" + patronId + '\'' +
                ", borrowedBooksCount=" + borrowedBooks.size() +
                '}';
    }
}