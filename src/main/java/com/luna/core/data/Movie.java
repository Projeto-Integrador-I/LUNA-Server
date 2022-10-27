package com.luna.core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movie
    extends
        Media
{
    private Boolean adult;

    private String backDropPath = "";
    private String originalLang = "";
    private String originalTitle = "";
    private String overView = "";
    private String tagLine = "";
    
    private double runtime = 0;
    private double popularity = 0;

    private int seasons = 0;
    
    private String releaseDate = null; 
    
    private Map<String,Object> belongsToCollection = new HashMap<>();
    private ArrayList<String> productionCompanies = new ArrayList<>();
    
    private ArrayList<String> genres = new ArrayList<>();
    
    public Movie(){

        setType( TYPE_MOVIE );
    }


    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public double getRuntime() {
        return runtime;
    }

    public void setRuntime( double runtime) {
        this.runtime = runtime;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate( String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Map <String,Object> getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Map <String,Object> belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public ArrayList<String> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(ArrayList<String> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
