package br.com.infnet.openlibrary.services;

import br.com.infnet.openlibrary.exceptions.BookNotFoundException;
import br.com.infnet.openlibrary.models.Book;
import com.github.javafaker.Faker;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service @Data
public class BookServices {
    private Map<Long, Book> bookArchive = populateBooks();

    private Map<Long, Book> populateBooks() {
        List<String> genresPriory = Arrays.asList("Fantasy", "Fiction");
        List<String> genresHaley = Arrays.asList("Romance", "Contemporary");
        List<String> genresHouseUsher = Arrays.asList("Horror", "Historical");

        Book priory = new Book(9781526612861L, "The Priory Of The Orange Tree", "Samantha Shannon", genresPriory);
        Book whoWait = new Book(9798631156395L, "Those Who Wait", "Haley Cass", genresHaley);
        Book leastExpectIt = new Book(9798565966213L, "When You Least Expect It", "Haley Cass", genresHaley);
        Book houseUsher = new Book(9781713091226L, "The Fall Of The House Of Usher", "Edgar Allan Poe", genresHouseUsher);

        Map<Long, Book> bookArchive = new HashMap<>();
        bookArchive.put(9781526612861L, priory);
        bookArchive.put(9798631156395L, whoWait);
        bookArchive.put(9798565966213L, leastExpectIt);
        bookArchive.put(9781713091226L, houseUsher);

        for (int i = 5; i <= 20; i++) {
            Faker faker = new Faker();
            long isbn = Long.parseLong(faker.code().isbn13());
            List<String> randomGenre = Arrays.asList(faker.book().genre());
            Book book = new Book(isbn, faker.book().title(), faker.book().author(), randomGenre);
            bookArchive.put(isbn, book);
        }
        return bookArchive;
    }

    public List<Book> listAllBooks() {
        return new ArrayList<>(bookArchive.values());
    }

    public Book findBookByISBN(long isbn) {
        Book book = bookArchive.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book not found. Please recheck the ISBN");
        }
        return book;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> selectedBooks = bookArchive.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(toList());

        if (selectedBooks.isEmpty()) {
            throw new BookNotFoundException("No books found with the author: " + author);
        }
        return selectedBooks;
    }

    public void createBook(Book newBook) {
        if (bookArchive.containsKey(newBook.getISBN())) {
            throw new BookNotFoundException("This book is already in the database. Please register a new book");
        } else {
            bookArchive.put(newBook.getISBN(), newBook);
        }
    }

    public void deleteBook(long isbn) {
        if (bookArchive.containsKey(isbn)) {
            bookArchive.remove(isbn);
        } else {
            throw new BookNotFoundException("Book not found. Please recheck the ISBN");
        }
    }

    public void updateBook(long isbn, Book updatedBook) {
        if (bookArchive.containsKey(isbn)) {
            bookArchive.replace(isbn, updatedBook);
        } else {
            throw new BookNotFoundException("Book could not be updated. Please recheck the ISBN");
        }
    }


}
