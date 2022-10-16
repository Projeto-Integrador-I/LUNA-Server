package com.luna.core.data;

public class Media {
    
    private int id;
    private int year;
    private String title;
    private String description;

    private String coverLink;
    
    private int type;
    private String apiId;
    
    public final static int TYPE_GAME   = 0;
    public final static int TYPE_MOVIE  = 1;
    public final static int TYPE_BOOK   = 2;
    
    public String getCoverLink() {
        return this.coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getApiId() {
        return this.apiId;
    }

    public void setApiId(String APIRef) {
        this.apiId = APIRef;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
 
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
