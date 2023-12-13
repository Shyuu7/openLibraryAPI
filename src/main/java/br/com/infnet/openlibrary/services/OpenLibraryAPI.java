package br.com.infnet.openlibrary.services;

import br.com.infnet.openlibrary.exceptions.BookNotFoundException;
import br.com.infnet.openlibrary.models.Book;
import br.com.infnet.openlibrary.models.LibraryBook;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import lombok.extern.java.Log;

@Service @Log
public class OpenLibraryAPI {
    private final String apiURL = "https://openlibrary.org/search.json";

    public Book getBookByISBN(long isbn) {
            try {
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiURL)
                        .queryParam("isbn", isbn);

                URI uri = builder.build().toUri();

                ResponseEntity<LibraryBook> responseEntity = new RestTemplate().exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        LibraryBook.class
                );

                LibraryBook response = responseEntity.getBody();
                HttpStatusCode statusCode = responseEntity.getStatusCode();
                log.info(statusCode.toString());

                if (response != null) {
                    log.info(response.toBook().toString());
                    return response.toBook();
                }
                return null;
            } catch (Exception e) {
                throw new BookNotFoundException("Could not find the required book.");
            }
        }
    }