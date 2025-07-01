package com.library.service;

import com.library.model.Book;
import com.library.model.Patron;
import com.library.strategy.BookSearchStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The core Library management system. It handles the inventory of books and patrons,
 */
public class Library implements ILibraryService {
    // Stores all books in the library, keyed by ISBN for quick lookup
    private Map<String, Book> allBooksByIsbn;
    // Stores all patrons in the library, keyed by Patron ID
    private Map<String, Patron> patronsById;
    // Tracks which books are currently available (not borrowed), keyed by ISBN
    private Map<String, Book> availableBooksByIsbn;
    // Tracks which books are currently borrowed, keyed by ISBN
    private Map<String, Book> borrowedBooksByIsbn;


    public Library() {
        this.allBooksByIsbn = new HashMap<>();
        this.patronsById = new HashMap<>();
        this.availableBooksByIsbn = new HashMap<>();
        this.borrowedBooksByIsbn = new HashMap<>();
    }

    @Override
    public boolean addBook(Book book) {
        if (book == null || allBooksByIsbn.containsKey(book.getIsbn())) {
            return false;
        }
        allBooksByIsbn.put(book.getIsbn(), book);
        availableBooksByIsbn.put(book.getIsbn(), book); // Initially, all added books are available
        return true;
    }

    @Override
    public boolean removeBook(String isbn) {
        if (!allBooksByIsbn.containsKey(isbn)) {
            return false;
        }
        if (borrowedBooksByIsbn.containsKey(isbn)) {
            return false;
        }

        Book removedBook = allBooksByIsbn.remove(isbn);
        availableBooksByIsbn.remove(isbn); // Remove from available as well
        return true;
    }

    @Override
    public boolean updateBook(String isbn, String newTitle, String newAuthor, int newPublicationYear) {
        Book bookToUpdate = allBooksByIsbn.get(isbn);
        if (bookToUpdate == null) {
            return false;
        }

        if (newTitle != null && !newTitle.isEmpty()) {
            bookToUpdate.setTitle(newTitle);
        }
        if (newAuthor != null && !newAuthor.isEmpty()) {
            bookToUpdate.setAuthor(newAuthor);
        }
        if (newPublicationYear > 0) {
            bookToUpdate.setPublicationYear(newPublicationYear);
        }
        return true;
    }

    @Override
    public List<Book> searchBook(String query, BookSearchStrategy strategy) {
        if (strategy == null) {
            return Collections.emptyList();
        }
        List<Book> results = strategy.search(new ArrayList<>(allBooksByIsbn.values()), query);
        if (results.isEmpty()) {
            // No error, just informative that nothing was found for a search
            // logger.info("No books found for query: '" + query + "' using strategy: " + strategy.getClass().getSimpleName());
        }
        return results;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(allBooksByIsbn.values());
    }

    @Override
    public List<Book> getAvailableBooks() {
        return new ArrayList<>(availableBooksByIsbn.values());
    }

    @Override
    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooksByIsbn.values());
    }

    // --- Patron Management ---

    @Override
    public boolean addPatron(Patron patron) {
        if (patron == null || patronsById.containsKey(patron.getPatronId())) {
            return false;
        }
        patronsById.put(patron.getPatronId(), patron);
        return true;
    }

    @Override
    public boolean updatePatron(String patronId, String newName) {
        Patron patronToUpdate = patronsById.get(patronId);
        if (patronToUpdate == null) {
           return false;
        }
        if (newName != null && !newName.isEmpty()) {
            patronToUpdate.setName(newName);
            return true;
        }
        return false;
    }

    @Override
    public Patron getPatronById(String patronId) {
        return patronsById.get(patronId);
    }


    @Override
    public List<Patron> getAllPatrons() {
        return Collections.unmodifiableList(new ArrayList<>(patronsById.values()));
    }

    @Override
    public List<Book> getPatronBorrowingHistory(String patronId) {
        Patron patron = patronsById.get(patronId);
        if (patron == null) {
            return Collections.emptyList();
        }
        return patron.getBorrowedBooks();
    }

    // --- Lending Process ---

    @Override
    public boolean checkoutBook(String isbn, String patronId) {
        Book book = availableBooksByIsbn.get(isbn);
        Patron patron = patronsById.get(patronId);

        if (book == null) {
           return false;
        }
        if (patron == null) {
            return false;
        }
        if (borrowedBooksByIsbn.containsKey(isbn)) {
           return false;
        }

        // Move book from available to borrowed
        availableBooksByIsbn.remove(isbn);
        borrowedBooksByIsbn.put(isbn, book);
        patron.addBorrowedBook(book); // Add to patron's personal borrowing history

        return true;
    }


    @Override
    public boolean returnBook(String isbn, String patronId) {
        Book book = borrowedBooksByIsbn.get(isbn);
        Patron patron = patronsById.get(patronId);

        if (book == null) {
            return false;
        }
        if (patron == null) {
            return false;
        }
        // Ensure the patron actually borrowed this book
        if (!patron.getBorrowedBooks().contains(book)) {
           return false;
        }

        // Move book from borrowed to available
        borrowedBooksByIsbn.remove(isbn);
        availableBooksByIsbn.put(isbn, book);
        patron.removeBorrowedBook(book); // Remove from patron's personal borrowing history

       return true;
    }
}
