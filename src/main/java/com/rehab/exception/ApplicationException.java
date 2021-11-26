package com.rehab.exception;

/**
 * Custom exception class for those exceptions that will be thrown to indicate that something went wrong.
 */
public class ApplicationException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message detail message.
     */
    public ApplicationException(String message) {
        super(message);
    }
}
