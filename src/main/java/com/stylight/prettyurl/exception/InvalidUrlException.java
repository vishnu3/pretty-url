package com.stylight.prettyurl.exception;

public class InvalidUrlException extends RuntimeException {
    private static final long serialVersionUID = -6016393695850320660L;

    private static final String MESSAGE = "The given url is invalid";

    public InvalidUrlException() {
        super(MESSAGE);
    }
}
