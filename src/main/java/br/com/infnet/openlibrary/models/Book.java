package br.com.infnet.openlibrary.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Long ISBN;
    private String title;
    private String author;
    private List<String> genres;
}
