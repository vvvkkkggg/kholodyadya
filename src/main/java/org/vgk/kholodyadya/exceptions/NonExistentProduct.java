package org.vgk.kholodyadya.exceptions;

public class NonExistentProduct extends RuntimeException {
    public NonExistentProduct(String errorMessage) {
        super(errorMessage);
    }
}
