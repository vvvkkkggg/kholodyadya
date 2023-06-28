package org.vgk.kholodyadya.exceptions;

public class InsufficientPermissions extends Exception {
    public InsufficientPermissions(String errorMessage) {
        super(errorMessage);
    }
}
