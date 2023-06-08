package org.vgk.kholodyadya.exceptions;

public class InvalidQrException extends RuntimeException {
    public InvalidQrException(String errorMessage) {
        super(errorMessage);
    }
}
