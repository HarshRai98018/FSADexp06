package com.klu.library.controller;

import com.klu.library.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LibraryController {
    private final Map<Integer, Book> bookCatalog = new LinkedHashMap<>();
    private final List<Book> addedBooks = new ArrayList<>();

    public LibraryController() {
        bookCatalog.put(1, new Book(1, "Clean Code", "Robert C. Martin", 499.00));
        bookCatalog.put(2, new Book(2, "Effective Java", "Joshua Bloch", 699.00));
        bookCatalog.put(3, new Book(3, "Spring in Action", "Craig Walls", 799.00));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Online Library System";
    }

    @GetMapping("/count")
    public int count() {
        return bookCatalog.size();
    }

    @GetMapping("/price")
    public double samplePrice() {
        return 549.99;
    }

    @GetMapping("/books")
    public List<String> getBookTitles() {
        return bookCatalog.values().stream()
                .map(Book::getTitle)
                .toList();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        Book book = bookCatalog.get(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found with id: " + id);
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public String search(@RequestParam String title) {
        return "Search request received for title: " + title;
    }

    @GetMapping("/author/{name}")
    public String author(@PathVariable String name) {
        return "Showing books written by author: " + name;
    }

    @PostMapping("/addbook")
    public String addBook(@RequestBody Book book) {
        addedBooks.add(book);
        return "Book added successfully: " + book.getTitle();
    }

    @GetMapping("/viewbooks")
    public List<Book> viewBooks() {
        return addedBooks;
    }
}
