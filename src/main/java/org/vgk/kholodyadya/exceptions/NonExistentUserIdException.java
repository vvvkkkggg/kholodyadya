package org.vgk.kholodyadya.exceptions;

public class NonExistentUserIdException extends RuntimeException {
    public NonExistentUserIdException(String errorMessage) {
        super(errorMessage);
    }
}
