package com.library.service;

import com.library.model.Book;
import com.library.model.Patron;
import com.library.strategy.BookSearchStrategy;

import java.util.List;

/**
 * Interface defining the core operations of a Library Management System.
 * This promotes the Dependency Inversion Principle, allowing the high-level
 * 'Main' module to depend on an abstraction rather than a concrete implementation.
 */
public interface ILibraryService {
    // Book Management
    boolean addBook(Book book);
    boolean removeBook(String isbn);
    boolean updateBook(String isbn, String newTitle, String newAuthor, int newPublicationYear);
    List<Book> searchBook(String query, BookSearchStrategy strategy);
    List<Book> getAllBooks();
    List<Book> getAvailableBooks();
    List<Book> getBorrowedBooks();

    // Patron Management
    boolean addPatron(Patron patron);
    boolean updatePatron(String patronId, String newName);
    Patron getPatronById(String patronId);
    List<Patron> getAllPatrons();
    List<Book> getPatronBorrowingHistory(String patronId);

    // Lending Process
    boolean checkoutBook(String isbn, String patronId);
    boolean returnBook(String isbn, String patronId);
}