package org.vgk.kholodyadya.exceptions;

public class NonExistentGroupException extends RuntimeException {
    public NonExistentGroupException(String errorMessage) {
        super(errorMessage);
    }
}