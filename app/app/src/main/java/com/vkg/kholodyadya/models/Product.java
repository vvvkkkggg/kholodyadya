package com.vkg.kholodyadya.models;

public class Product {
    int id;
    String title, dueDate;
    private int imgid;

    int category;

    public Product(int id, String title, String dueDate, int imgid, int category) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.imgid = imgid;
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


}
