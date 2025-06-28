package com.library.strategy;

import com.library.model.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@link BookSearchStrategy} for searching books by ISBN.
 * Requires an exact match for the ISBN.
 */
public class SearchByIsbnStrategy implements BookSearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        // ISBN should typically be unique, so we expect at most one result
        return books.stream()
                .filter(book -> book.getIsbn().equals(query))
                .collect(Collectors.toList());
    }
}