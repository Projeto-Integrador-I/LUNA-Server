package com.luna.core.data;

import java.util.ArrayList;

public class Book
    extends
         Media
{
    public Book( int type ) 
    {
        setType( type );
    }

    private String publisher = "";
    private String pageCount = "";
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> imageLinks = new ArrayList<>();

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getPageCount() {
        return pageCount;
    }
    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
    public ArrayList<String> getCategories() {
        return categories;
    }
    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
    public ArrayList<String>  getImageLinks() {
        return imageLinks;
    }
    public void setImageLinks(ArrayList<String>  imageLinks) {
        this.imageLinks = imageLinks;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    private String language = "";

    
}