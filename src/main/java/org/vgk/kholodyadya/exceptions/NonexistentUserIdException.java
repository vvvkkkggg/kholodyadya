package org.vgk.kholodyadya.exceptions;

public class NonexistentUserIdException extends RuntimeException {
    public NonexistentUserIdException(String errorMessage) {
        super(errorMessage);
    }
}
