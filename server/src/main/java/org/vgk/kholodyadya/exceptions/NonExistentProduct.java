package org.vgk.kholodyadya.exceptions;

public class NonExistentProduct extends Exception {
    public NonExistentProduct(String errorMessage) {
        super(errorMessage);
    }
}
