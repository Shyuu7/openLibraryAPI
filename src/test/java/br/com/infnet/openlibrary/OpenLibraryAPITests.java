package br.com.infnet.openlibrary;

import br.com.infnet.openlibrary.models.Book;
import br.com.infnet.openlibrary.services.OpenLibraryAPI;
import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest @Log
public class OpenLibraryAPITests {
    @Autowired
    OpenLibraryAPI openLibraryAPI;
    private static Logger LOGGER = LoggerFactory.getLogger(OpenLibraryAPITests.class);

    @Test
    @DisplayName("Deve converter um livro procurado em objeto Book")
    public void convertingBook() {
        Book book = openLibraryAPI.getBookByISBN(9788445072967L);
        LOGGER.info(book.getAuthor());
        assertEquals("J.R.R. Tolkien",book.getAuthor());
    }
}
