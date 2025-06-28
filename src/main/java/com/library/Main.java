package com.library;

import com.library.model.Book;
import com.library.model.Patron;
import com.library.service.Library;
import com.library.strategy.SearchByAuthorStrategy;
import com.library.strategy.SearchByIsbnStrategy;
import com.library.strategy.SearchByTitleStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();


        System.out.println("Library Management System Initialized.");
        System.out.println("-------------------------------------\n");

        // --- Book Management ---
        System.out.println("--- Book Management ---");
        Book book1 = new Book("UNSCRIPTED", "MJ DeMarco", "B06XBRLXJC", 2017);
        Book book2 = new Book("Neuro-Discipline", "Peter Hollins", "B07Z8J5LFJ", 2019);
        Book book3 = new Book("Change Your Brain Every Day", "Daniel G. Amen, MD", "B0B57GNPFV", 2023);
        Book book4 = new Book("Money Works: The Guide to Financial Literacy", "Abhijeet Kolapkar", "B0CD81VNTV", 2023);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        System.out.println("Books in inventory:");
        library.getAllBooks().forEach(System.out::println);
        System.out.println("Available books after adding: " + library.getAvailableBooks().size() + "\n");

        // Search for a book
        System.out.println("Searching for 'UNSCRIPTED' by title using SearchByTitleStrategy:");
        List<Book> foundBooksByTitle = library.searchBook("UNSCRIPTED", new SearchByTitleStrategy());
        foundBooksByTitle.forEach(System.out::println);
        System.out.println();

        System.out.println("Searching for books by 'Peter Hollins' using SearchByAuthorStrategy:");
        List<Book> foundBooksByAuthor = library.searchBook("Peter Hollins", new SearchByAuthorStrategy());
        foundBooksByAuthor.forEach(System.out::println);
        System.out.println();

        System.out.println("Searching for book by ISBN 'B0B57GNPFV' using SearchByIsbnStrategy:");
        List<Book> foundBookByIsbn = library.searchBook("B0B57GNPFV", new SearchByIsbnStrategy());
        foundBookByIsbn.forEach(System.out::println);
        System.out.println();

        // Update a book
        System.out.println("Updating publication year for 'Change Your Brain Every Day' (ISBN: B0B57GNPFV):");
        library.updateBook("B0B57GNPFV", "Change Your Brain Every Day", "Daniel G. Amen, MD", 1984);
        System.out.println("Updated book details: " + library.searchBook("B0B57GNPFV", new SearchByIsbnStrategy()).get(0) + "\n");

        // Remove a book
        System.out.println("Removing 'UNSCRIPTED' (ISBN: B06XBRLXJC):");
        library.removeBook("B06XBRLXJC");
        System.out.println("Books in inventory after removal:");
        library.getAllBooks().forEach(System.out::println);
        System.out.println("Available books after removing: " + library.getAvailableBooks().size() + "\n");

        // --- Patron Management ---
        System.out.println("--- Patron Management ---");
        Patron patron1 = new Patron("Ajay", "P001");
        Patron patron2 = new Patron("Sunil", "P002");

        library.addPatron(patron1);
        library.addPatron(patron2);

        System.out.println("Added patrons:");
        library.getAllPatrons().forEach(System.out::println);
        System.out.println();

        // Update patron information
        System.out.println("Updating name for Patron P001:");
        library.updatePatron("P001", "Anil W");
        System.out.println("Updated patron details: " + library.getPatronById("P001") + "\n");

        // --- Lending Process ---
        System.out.println("--- Lending Process ---");

        // Checkout books
        System.out.println("Anil W (P001) checking out 'Change Your Brain Every Day':");
        if (library.checkoutBook("B0B57GNPFV", "P001")) {
            System.out.println("Checkout successful!");
        } else {
            System.out.println("Checkout failed. Book might be unavailable or patron/book not found.");
        }
        System.out.println("Available books after Anil W's checkout: " + library.getAvailableBooks().size());
        System.out.println("Borrowed books after Anil W's checkout: " + library.getBorrowedBooks().size() + "\n");


        System.out.println("Sunil (P002) checking out 'Neuro-Discipline':");
        if (library.checkoutBook("B07Z8J5LFJ", "P002")) {
            System.out.println("Checkout successful!");
        } else {
            System.out.println("Checkout failed. Book might be unavailable or patron/book not found.");
        }
        System.out.println("Available books after Bob's checkout: " + library.getAvailableBooks().size());
        System.out.println("Borrowed books after Bob's checkout: " + library.getBorrowedBooks().size() + "\n");

        System.out.println("Attempting to checkout 'Neuro-Discipline' again (should fail):");
        if (library.checkoutBook("B07Z8J5LFJ", "P002")) {
            System.out.println("Checkout successful!");
        } else {
            System.out.println("Checkout failed as expected. Book is already borrowed.\n");
        }

        // Display borrowing history
        System.out.println("Anil W's borrowing history:");
        library.getPatronBorrowingHistory("P001").forEach(System.out::println);
        System.out.println();

        System.out.println("Sunil's borrowing history:");
        library.getPatronBorrowingHistory("P002").forEach(System.out::println);
        System.out.println();

        // Return a book
        System.out.println("Anil W (P001) returning 'Change Your Brain Every Day':");
        if (library.returnBook("B0B57GNPFV", "P001")) {
            System.out.println("Return successful!");
        } else {
            System.out.println("Return failed. Book might not be borrowed by this patron or not found.");
        }
        System.out.println("Available books after Anil W's return: " + library.getAvailableBooks().size());
        System.out.println("Borrowed books after Anil W's return: " + library.getBorrowedBooks().size() + "\n");

        System.out.println("Anil W's borrowing history after return:");
        library.getPatronBorrowingHistory("P001").forEach(System.out::println);
        System.out.println();

        System.out.println("--- Final Inventory Status ---");
        System.out.println("All books in library:");
        library.getAllBooks().forEach(System.out::println);
        System.out.println("\nAvailable books:");
        library.getAvailableBooks().forEach(System.out::println);
        System.out.println("\nBorrowed books:");
        library.getBorrowedBooks().forEach(System.out::println);
    }
}