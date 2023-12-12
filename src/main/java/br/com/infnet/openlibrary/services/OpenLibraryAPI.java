package br.com.infnet.openlibrary.services;

import br.com.infnet.openlibrary.exceptions.BookNotFoundException;
import br.com.infnet.openlibrary.models.Book;
import br.com.infnet.openlibrary.models.LibraryBook;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service @Log
public class OpenLibraryAPI {
    private static Logger LOGGER = LoggerFactory.getLogger(OpenLibraryAPI.class);
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
                LOGGER.info("Response Status Code: {}", statusCode);

                if (response != null) {
                    LOGGER.info(response.toBook().toString());
                    return response.toBook();
                }
                return null;
            } catch (Exception e) {
                throw new BookNotFoundException("Could not find the required book.");
            }
        }
    }