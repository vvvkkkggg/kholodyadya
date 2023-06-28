package org.vgk.kholodyadya.exceptions;

public class NonExistentUserIdException extends Exception {
    public NonExistentUserIdException(String errorMessage) {
        super(errorMessage);
    }
}
