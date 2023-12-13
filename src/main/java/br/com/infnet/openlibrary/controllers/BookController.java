package br.com.infnet.openlibrary.controllers;

import br.com.infnet.openlibrary.exceptions.BookNotFoundException;
import br.com.infnet.openlibrary.models.Book;
import br.com.infnet.openlibrary.services.BookServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookServices bookServices;

    @GetMapping("/{ISBN}")
    public Book getByISBN(@PathVariable long ISBN) {
        return bookServices.findBookByISBN(ISBN);
    }

    @GetMapping("/list")
    public List<Book> listAll(@RequestParam(required = false, defaultValue = "20") int size,
                              @RequestParam(required = false) Optional<String> author) {
        List<Book> books = bookServices.listAllBooks();

        if (author.isPresent()) {
            return bookServices.findBooksByAuthor(author.get());
        } else {
            return books.subList(0, size);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> handlePostRequest(@RequestBody Book newBook) {
        try {
            bookServices.createBook(newBook);
            return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>("Failed to add book: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateBook/{isbn}")
    public ResponseEntity<String> handleUpdateRequest(@PathVariable Long isbn, @RequestBody Book updatedBook) {
        try {
            bookServices.updateBook(isbn, updatedBook);
            return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("Failed to update book: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteBook/{ISBN}")
    public ResponseEntity<String> handleDeleteRequest(@PathVariable long ISBN) {
        try {
            bookServices.deleteBook(ISBN);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("Failed to find book.", HttpStatus.NOT_FOUND);
        }
    }
}

