package org.vgk.kholodyadya.exceptions;

public class InsufficientPermissions extends RuntimeException {
    public InsufficientPermissions(String errorMessage) {
        super(errorMessage);
    }
}
