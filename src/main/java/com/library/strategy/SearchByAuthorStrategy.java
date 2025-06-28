package com.library.strategy;

import com.library.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByAuthorStrategy implements BookSearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        String lowerCaseQuery = query.toLowerCase();
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}