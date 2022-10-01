package com.luna.core.data;

import java.util.ArrayList;
import java.util.HashMap;

public class Game 
    extends 
        Media
{
    private double appId = 0;

    private String name = "";
    private double required_age = 0;
    private boolean is_free = false;
    private ArrayList<Object> dlcs = new ArrayList<>();
    private String desc = "";
    private String aboutGame = "";
    private HashMap<String, String> images = new HashMap<>();
    private String officialWebSite = "";
    private ArrayList<String> developers = new ArrayList<>();
    private ArrayList<String> publishers = new ArrayList<>();
    private double inicialPrice = 0;
    private double finalPrice = 0;
    private double discountPercent = 0;
    private String finalPriceFormatted = "";
    private HashMap<String, Boolean> supportedPlataforms = new HashMap<>();
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();
    private String releaseDate = "";

    public Game() {
    }

    public String getName()
    {
        return name;
    }
 
    public void setName(String name)
    {
        this.name = name;
    }
 
    public double getAppId()
    {
        return appId;
    }
 
    public void setAppId(double appId)
    {
        this.appId = appId;
    }
 
    public double getRequired_age()
    {
        return required_age;
    }
 
    public void setRequired_age(double required_age)
    {
        this.required_age = required_age;
    }
 
    public boolean isIs_free()
    {
        return is_free;
    }
 
    public void setIs_free(boolean is_free)
    {
        this.is_free = is_free;
    }
 
    public ArrayList<Object>  getDlcs()
    {
        return dlcs;
    }
 
    public void setDlcs(ArrayList<Object> dlcs)
    {
        this.dlcs = dlcs;
    }
 
    public String getDesc()
    {
        return desc;
    }
 
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
 
    public String getAboutGame()
    {
        return aboutGame;
    }
 
    public void setAboutGame(String aboutGame)
    {
        this.aboutGame = aboutGame;
    }
 
    public HashMap<String, String> getImages()
    {
        return images;
    }
 
    public void setImages(HashMap<String, String> images)
    {
        this.images = images;
    }
 
    public String getOfficialWebSite()
    {
        return officialWebSite;
    }
 
    public void setOfficialWebSite(String officialWebSite)
    {
        this.officialWebSite = officialWebSite;
    }
 
    public ArrayList<String> getDevelopers()
    {
        return developers;
    }
 
    public void setDevelopers(ArrayList<String> developers)
    {
        this.developers = developers;
    }
 
    public ArrayList<String> getPublishers()
    {
        return publishers;
    }
 
    public void setPublishers(ArrayList<String> publishers)
    {
        this.publishers = publishers;
    }
 
    public double getInicialPrice()
    {
        return inicialPrice;
    }
 
    public void setInicialPrice(double inicialPrice)
    {
        this.inicialPrice = inicialPrice;
    }
 
    public double getFinalPrice()
    {
        return finalPrice;
    }
 
    public void setFinalPrice(double finalPrice)
    {
        this.finalPrice = finalPrice;
    }
 
    public double getDiscountPercent()
    {
        return discountPercent;
    }
 
    public void setDiscountPercent(double discountPercent)
    {
        this.discountPercent = discountPercent;
    }
 
    public String getFinalPriceFormatted()
    {
        return finalPriceFormatted;
    }
 
    public void setFinalPriceFormatted(String finalPriceFormatted)
    {
        this.finalPriceFormatted = finalPriceFormatted;
    }
 
    public HashMap<String, Boolean> getSupportedPlataforms() 
{
        return supportedPlataforms;
    }

    public void setSupportedPlataforms(HashMap<String, Boolean> supportedPlataforms)
    {
        this.supportedPlataforms = supportedPlataforms;
    }

    public ArrayList<String> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<String> categories)
    {
        this.categories = categories;
    }

    public ArrayList<String> getGenres()
    {
        return genres;
    }

    public void setGenres(ArrayList<String> genres)
    {
        this.genres = genres;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }
}
