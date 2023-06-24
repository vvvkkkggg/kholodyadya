package com.vkg.kholodyadya.models;

public class ProductInfo {
    private int imgid;
    private String category;
    private int calories;
    private int protein;
    private int fat;
    private int carbon;

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbon() {
        return carbon;
    }

    public void setCarbon(int carbon) {
        this.carbon = carbon;
    }

    public ProductInfo(int imgid, String category, int calories, int protein, int fat, int carbon) {
        this.imgid = imgid;
        this.category = category;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbon = carbon;
    }
}
