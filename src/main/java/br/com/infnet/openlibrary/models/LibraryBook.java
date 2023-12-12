package br.com.infnet.openlibrary.models;

import br.com.infnet.openlibrary.exceptions.CantConvertException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Data @JsonIgnoreProperties(ignoreUnknown = true) @Builder
public class LibraryBook {

    @JsonProperty("docs")
    private List<BookDetails> docs;

    public static class BookDetails {
        @JsonProperty("title")
        private String title;

        @JsonProperty("author_name")
        private List<String> authorName;

        @JsonProperty("isbn")
        private List<String> isbn;

        @JsonProperty("subject")
        private List<String> genres;

        public String getTitle() {
            return title;
        }

        public List<String> getAuthorName() {
            return authorName;
        }

        public List<String> getIsbn() {
            return isbn;
        }

        public List<String> getGenres() {
            return genres;
        }
    }

    public Book toBook() {
        if (docs != null && !docs.isEmpty()) {
            BookDetails bookDetails = docs.getFirst();
            return new Book(Long.parseLong(bookDetails.getIsbn().getFirst()), bookDetails.getTitle(),
                    bookDetails.getAuthorName().getFirst(), bookDetails.getGenres());
        }
        throw new CantConvertException("This book couldn't be converted.");
    }
}
