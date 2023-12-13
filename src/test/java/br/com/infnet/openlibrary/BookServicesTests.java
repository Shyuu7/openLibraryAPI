package br.com.infnet.openlibrary;

import br.com.infnet.openlibrary.exceptions.BookNotFoundException;
import br.com.infnet.openlibrary.models.Book;
import br.com.infnet.openlibrary.services.BookServices;
import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
public class BookServicesTests {

    @Test
    @DisplayName("Pegando livro pelo ISBN")
    public void getBookByISBN() {
        BookServices bookServices = new BookServices();
        Book searchedBook = bookServices.findBookByISBN(9798631156395L);
        log.info(searchedBook.getTitle());
        assertEquals("Those Who Wait", searchedBook.getTitle());
    }

    @Test
    @DisplayName("Testando inclusão do array de gêneros a um objeto livro")
    public void getGenres() {
        List<String> genres = Arrays.asList("Comics", "HQ", "Toons");
        Book newBook = new Book(1234567891235L, "Spider-Man beats Venom", "Peter Parker", genres);
        log.info(String.valueOf(newBook.getGenres()));
        assertEquals(genres, newBook.getGenres());
    }

    @Test
    @DisplayName("Testando atualização de livro no map")
    public void updatingBook() {
        BookServices bookServices = new BookServices();
        List<String> genres = Arrays.asList("Historical", "Police", "Mystery");
        Book newBook = new Book(1234567891235L, "Spider-Man beats Venom", "Peter Parker", genres);
        bookServices.updateBook(9798631156395L, newBook);
        log.info(newBook.getTitle());
        assertEquals("Spider-Man beats Venom", newBook.getTitle());
        assertEquals("Peter Parker", newBook.getAuthor());
    }

    @Test
    @DisplayName("Testando se há adição de livro no map")
    public void addingBook() {
        BookServices bookServices = new BookServices();
        Map<Long, Book> bookArchive = bookServices.getBookArchive();
        List<String> genres = Arrays.asList("Historical", "Police", "Mystery");
        Book newBook = new Book(1234567891235L, "Spider-Man beats Venom", "Peter Parker", genres);
        bookServices.createBook(newBook);
        assertTrue(bookArchive.containsValue(newBook));
    }

    @Test
    @DisplayName("Testando se há remoção de livro do map")
    public void removingBook() {
        BookServices bookServices = new BookServices();
        Map<Long, Book> bookArchive = bookServices.getBookArchive();
        List<String> genres = Arrays.asList("Historical", "Police", "Mystery");
        Book newBook = new Book(1234567891235L, "Spider-Man beats Venom", "Peter Parker", genres);
        bookServices.createBook(newBook);
        bookServices.deleteBook(newBook.getISBN());
        assertFalse(bookArchive.containsValue(newBook));
    }

    @Test
    @DisplayName("Testando remoção de livro que não foi incluso previamente no map")
    public void bookException() {
        BookServices bookServices = new BookServices();
        Map<Long, Book> bookArchive = bookServices.getBookArchive();
        assertThrows(BookNotFoundException.class, () -> bookServices.deleteBook(123456789L));
    }
}
