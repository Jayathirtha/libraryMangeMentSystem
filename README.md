Library Management System
This project implements a simple Library Management System in Java, demonstrating Object-Oriented Programming (OOP) principles, SOLID principles, and relevant design patterns.

Core Features
Book Management: Add, remove, update, and search for books by title, author, or ISBN.

Patron Management: Add new library members and update their information.

Lending Process: Handle book checkout and return functionalities.

Inventory Management: Keep track of available and borrowed books.

Flexible Search: Employs the Strategy pattern for extensible book search capabilities.

Design Principles and Patterns Applied
Object-Oriented Programming (OOP):

Encapsulation: Data and methods are bundled within classes (Book, Patron, Library), with controlled access via getters and setters.

Abstraction: The ILibraryService interface defines the core library operations, hiding the internal implementation details from the client (Main class).

Polymorphism: Demonstrated through the BookSearchStrategy interface, where different concrete search strategies (SearchByTitleStrategy, SearchByAuthorStrategy, SearchByIsbnStrategy) can be used interchangeably.

SOLID Principles:

Single Responsibility Principle (SRP): Each class has a single responsibility (e.g., Book manages book data, Patron manages patron data, Library manages core library operations).

Open/Closed Principle (OCP): The system is open for extension but closed for modification. New search strategies can be added by implementing BookSearchStrategy without changing the Library class.

Dependency Inversion Principle (DIP): High-level modules (Main) depend on abstractions (ILibraryService, BookSearchStrategy) rather than concretions.

Design Patterns:

Library acts as the Subject.

Strategy Pattern: Used for book search functionality.

BookSearchStrategy is the Strategy interface.

SearchByTitleStrategy, SearchByAuthorStrategy, SearchByIsbnStrategy are concrete Strategies.

The Library class acts as the Context, which uses a BookSearchStrategy to perform searches.

Run:
After successful compilation, run the Main class:

java com.library.Main

This will execute the main method in Main.java, demonstrating the library system's functionalities and different search methods.

Class Diagram

    class Book {
        -String title
        -String author
        -String isbn
        -int publicationYear
        +Book(title, author, isbn, year)
        +getTitle()
        +getAuthor()
        +getIsbn()
        +getPublicationYear()
        +setTitle(title)
        +setAuthor(author)
        +setPublicationYear(year)
        +equals(Object)
        +hashCode()
        +toString()
    }

    class Patron {
        -String name
        -String patronId
        -List<Book> borrowedBooks
        +Patron(name, patronId)
        +getName()
        +getPatronId()
        +getBorrowedBooks()
        +setName(name)
        +addBorrowedBook(Book)
        +removeBorrowedBook(Book)
        +equals(Object)
        +hashCode()
        +toString()
    }

    interface ILibraryService {
        +addBook(Book): boolean
        +removeBook(String): boolean
        +updateBook(String, String, String, int): boolean
        +searchBook(String, BookSearchStrategy): List<Book>
        +getAllBooks(): List<Book>
        +getAvailableBooks(): List<Book>
        +getBorrowedBooks(): List<Book>
        +addPatron(Patron): boolean
        +updatePatron(String, String): boolean
        +getPatronById(String): Patron
        +getAllPatrons(): List~Patron~
        +getPatronBorrowingHistory(String): List<Book>
        +checkoutBook(String, String): boolean
        +returnBook(String, String): boolean
    }

    class Library {
        -Map<String, Book> allBooksByIsbn
        -Map<String, Patron> patronsById
        -Map<String, Book> availableBooksByIsbn
        -Map<String, Book> borrowedBooksByIsbn

        +Library()
        +addBook(Book): boolean
        +removeBook(String): boolean
        +updateBook(String, String, String, int): boolean
        +searchBook(String, BookSearchStrategy): List<Book>
        +checkoutBook(String, String): boolean
        +returnBook(String, String): boolean
    }
