package com.library.strategy;

import com.library.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByTitleStrategy implements BookSearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        String lowerCaseQuery = query.toLowerCase();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}
