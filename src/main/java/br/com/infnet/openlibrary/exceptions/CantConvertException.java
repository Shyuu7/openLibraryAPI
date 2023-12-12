package br.com.infnet.openlibrary.exceptions;

public class CantConvertException extends RuntimeException {
    public CantConvertException (String message) {
        super(message);
    }
}
