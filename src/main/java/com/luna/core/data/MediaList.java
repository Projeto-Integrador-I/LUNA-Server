package com.luna.core.data;

public class MediaList 
{
    private int id;
    private String description;
    private String name;
    private int userId;

    public final static int CATEGORY_INACTIVE   = 0;
    public final static int CATEGORY_USER       = 1;
    public final static int CATEGORY_ADMIN      = 2;

    public MediaList() {
    }

    public MediaList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }    
}
