package com.vkg.kholodyadya.models;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.vkg.kholodyadya.R;

import org.json.JSONObject;

public class Product {
    int localId;
    int productId;
    String title, dueDate, category;

    public Drawable drawable;
    private int imgid;
    private String image;

    public Product(int id, String title, String dueDate, int imgid, String category) {
        this.localId = id;
        this.title = title;
        this.dueDate = dueDate;
        this.imgid = imgid;
        this.category = category;
    }

    public Product(JSONObject json) throws org.json.JSONException {
        JSONObject productJSON = json.getJSONObject("product");
        this.title = productJSON.getString("productName");
        this.category = productJSON.getString("category");
        this.productId = productJSON.getInt("productId");
        this.image = json.getString("image");
        this.drawable = getDrawable(this.image);
        this.imgid = R.drawable.ic_product_banana;
        this.dueDate = "5 days";
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLocalId() {
        return localId;
    }

    public int getProductId() {
        return productId;
    }


    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setLocalId(int id) {
        this.localId = id;
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

    private Drawable getDrawable(String image) {
        try {
            Drawable d =new BitmapDrawable(image);
            return d;
        }
        catch(Exception ex) {
            return null;
        }
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
