package com.vkg.kholodyadya.models;

import com.vkg.kholodyadya.R;

import org.json.JSONObject;

public class Group {
    private int id;
    private String name;
    private String description;

    public Group(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Group(JSONObject json) throws org.json.JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("groupName");
        this.description = json.getString("description");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
