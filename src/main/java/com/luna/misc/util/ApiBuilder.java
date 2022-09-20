package com.luna.misc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.luna.core.data.Game;
import com.luna.core.data.Movie;

public class ApiBuilder 
{
    private static final String IMAGEPATH = "https://image.tmdb.org/t/p/w500";

    public static Game buildGame( Map<String, Object> json, String appId )
     {
        Game game = null;

        Map <String, Object> app = (Map <String,Object>)json.get( appId );
        boolean sucess = (boolean)app.get("success" );

        if ( sucess ) 
        {
            Map<String, Object> appData = (Map <String,Object>)app.get( "data" );

            if ( appData.get("type").toString().equals("game") )
            {
                game = new Game();

                Map<String, Object> priceOverview = (Map <String,Object>)appData.get( "price_overview" );
                Map<String, Object> releaseDateMap = (Map <String,Object>)appData.get( "release_date" );
    
                HashMap<String, String> images = new HashMap<>();
                images.put( "headerImage", appData.get( "header_image" ).toString() );
    
                ArrayList<Map<String, Object>>  screenShotsList= (ArrayList< Map<String, Object> >)appData.get( "screenshots" );
    
                if ( screenShotsList != null )
                {
                    for ( Map<String, Object> screenShotsMap : screenShotsList ) 
                    {
                        images.put( "path_thumbnail:" + screenShotsMap.get( "id" ).toString(), screenShotsMap.get( "path_thumbnail" ).toString() );
                        images.put( "full_thumbnail:" + screenShotsMap.get( "id" ).toString(), screenShotsMap.get( "path_full" ).toString() );
                    }
                }
    
                game.setName( appData.get( "name" ).toString() != null ? appData.get( "name" ).toString() : "data not found");
                game.setAppId( (double)appData.get( "steam_appid" ) ); 
                game.setIs_free( (boolean)appData.get( "is_free" ) );
                game.setRequired_age( Double.parseDouble( appData.get( "required_age" ).toString() ) ); 
                game.setDlcs( (ArrayList<Object>)appData.get( "dlc" ) );
                game.setDesc( appData.get( "detailed_description" ).toString() != null ? appData.get( "detailed_description" ).toString() : "data not found" );
                game.setAboutGame( appData.get( "about_the_game" ).toString() != null ? appData.get( "about_the_game" ).toString() : "data not found" );
                game.setShortDesc( appData.get( "short_description" ).toString() != null ? appData.get( "short_description" ).toString() : "data not found" );
                game.setImages(images);
                
                game.setOfficialWebSite( appData.get("website") != null ? appData.get("website").toString() : "data not found" );
                game.setDevelopers( (ArrayList<String>)appData.get( "developers" ) );
                game.setPublishers( (ArrayList<String>)appData.get( "publishers" ) );
                game.setInicialPrice( priceOverview != null ? (double)priceOverview.get( "initial" ) : 0 );
                game.setFinalPrice( priceOverview != null ? (double)priceOverview.get( "final" ) : 0 );
                game.setDiscountPercent( priceOverview != null ? (double)priceOverview.get( "discount_percent" ) : 0 );
                game.setFinalPriceFormatted( priceOverview != null ? priceOverview.get( "final_formatted" ).toString() : "data not found" );
                game.setCategories( (ArrayList<String>)appData.get( "categories" ) );
                game.setGenres( (ArrayList<String>)appData.get( "genres" ) );
                game.setReleaseDate( ( releaseDateMap != null && !releaseDateMap.isEmpty() ) ? releaseDateMap.get("date").toString() : "data not found" );
            }
        }

        return game;
    }

    public static Movie buildMovie( Map<String, Object> json, String id ) throws Exception
    {   
        Movie movie = new Movie();

        movie.setAdult( Boolean.parseBoolean( json.get("adult").toString() ) );
        movie.setBackDropPath( json.get( "backdrop_path" ) != null ? IMAGEPATH + json.get( "backdrop_path" ).toString() : "" );

        Map <String,Object> belongsToCollection = (Map <String,Object>)json.get( "belongs_to_collection" );

        if ( belongsToCollection != null )
        {
            for ( String key : belongsToCollection.keySet() )  
            {
                if ( key.equals( "backdrop_path" ) || key.equals( "poster_path" )  )
                {
                    belongsToCollection.replace( key, IMAGEPATH + belongsToCollection.get( key ).toString() );
                }
            }

            movie.setBelongsToCollection( belongsToCollection );
        }
        
        ArrayList<Map<String, Object>>  genresMap = (ArrayList< Map<String, Object> >)json.get( "genres" );
        ArrayList<String> genres = new ArrayList<>();

        if ( genresMap != null  )
        {
            for ( Map<String,Object> g : genresMap )
            {
                genres.add( g.get("name").toString() );
            }
        }

        movie.setGenres( genres );
        movie.setId( (double)json.get( "id" ) );
        movie.setOriginalLang( json.get( "original_language" ).toString() );
        movie.setOriginalTitle( json.get( "original_title" ).toString() );
        movie.setOverView( json.get( "overview" ).toString() );
        movie.setPopularity( Double.parseDouble( json.get( "popularity" ).toString() ) );
        movie.setPosterPath( json.get( "poster_path" ) != null ? IMAGEPATH + json.get( "poster_path" ).toString() : "" );
        
        ArrayList<Map<String, Object>>  companiesMap = (ArrayList< Map<String, Object> >)json.get( "production_companies" );
        ArrayList<String> companies = new ArrayList<>();

        if ( companiesMap != null )
        {
            for ( Map<String,Object> c : companiesMap )
            {
                companies.add( c.get("name").toString() );
            }
        }

        movie.setProductionCompanies( companies );
        movie.setReleaseDate( json.get( "release_date").toString() != null ? json.get( "release_date").toString() : null );
        movie.setRuntime( Double.parseDouble( json.get( "runtime" ).toString() ) );
        movie.setTagLine( json.get( "tagline" ).toString() != null ? json.get( "tagline" ).toString() : null );
        movie.setTitle( json.get( "title" ).toString() );

        return movie;
    }
}
