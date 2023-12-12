package br.com.infnet.openlibrary.controllers;

import br.com.infnet.openlibrary.models.Book;
import br.com.infnet.openlibrary.services.OpenLibraryAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openlibrary")
public class OpenLibraryController {

    @Autowired
    private OpenLibraryAPI openLibraryAPI;

    @GetMapping("/{isbn}")
    public Book getBookByISBN(@PathVariable long isbn) {
        return openLibraryAPI.getBookByISBN(isbn);
    }
}

