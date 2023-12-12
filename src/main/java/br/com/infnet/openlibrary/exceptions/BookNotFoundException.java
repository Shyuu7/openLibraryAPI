package br.com.infnet.openlibrary.exceptions;

public class BookNotFoundException extends RuntimeException {
 public BookNotFoundException (String ex) {
     super(ex);
 }
}
