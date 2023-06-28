package org.vgk.kholodyadya.exceptions;

public class NonExistentGroupException extends Exception {
    public NonExistentGroupException(String errorMessage) {
        super(errorMessage);
    }
}