package com.luna.misc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luna.core.data.Book;
import com.luna.core.data.Game;
import com.luna.core.data.Media;
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
    
                game.setCoverLink( appData.get( "header_image" ).toString() );

                HashMap<String, String> images = new HashMap<>();
    
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
                game.setApiId( appData.get( "steam_appid" ).toString() ); 
                game.setIs_free( (boolean)appData.get( "is_free" ) );
                game.setRequired_age( Double.parseDouble( appData.get( "required_age" ).toString() ) ); 
                game.setDlcs( (ArrayList<Object>)appData.get( "dlc" ) );
                game.setDesc( appData.get( "detailed_description" ).toString() != null ? appData.get( "detailed_description" ).toString() : "data not found" );
                game.setAboutGame( appData.get( "about_the_game" ).toString() != null ? appData.get( "about_the_game" ).toString() : "data not found" );
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
        movie.setApiId( json.get( "id" ).toString() );
        movie.setOriginalLang( json.get( "original_language" ).toString() );
        movie.setOriginalTitle( json.get( "original_title" ) != null ? json.get( "original_title" ).toString() :  json.get( "original_name" ).toString() );
        movie.setOverView( json.get( "overview" ).toString() );
        movie.setPopularity( Double.parseDouble( json.get( "popularity" ).toString() ) );
        movie.setCoverLink( json.get( "poster_path" ) != null ? IMAGEPATH + json.get( "poster_path" ).toString() : "" );
        
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
        movie.setReleaseDate( json.get( "release_date") != null ? json.get( "release_date").toString() : null );
        movie.setRuntime( json.get( "runtime" ) != null ? Double.parseDouble( json.get( "runtime" ).toString() ) : 0 );
        movie.setTagLine( json.get( "tagline" ).toString() != null ? json.get( "tagline" ).toString() : null );
        movie.setTitle( json.get( "title" ) != null ? json.get( "title" ).toString() : json.get( "name" ).toString()  );

        movie.setType( Media.TYPE_MOVIE );

        if ( json.get( "seasons" ) != null )
        {
            ArrayList<String> seasons = (ArrayList<String>)json.get( "seasons" );
            movie.setSeasons( seasons.size() );
        }

        return movie;
    }

    public static List<Book> buidBook( Map<String, Object> json ) throws Exception
    {
        Book book = null;
        List<Book> books = new ArrayList<>();

        ArrayList<Map<String, Object>>  itemsList = (ArrayList<Map<String, Object>>) json.get("items");

        if ( itemsList != null || !itemsList.isEmpty() )
        {
            for ( Map<String, Object> b : itemsList )
            {
                book = new Book();

                Map<String, Object> volumeInfo = (Map<String, Object>) b.get( "volumeInfo" );
                
                if ( volumeInfo != null )
                {
                    book.setTitle( volumeInfo.get( "title" ).toString() );
                    book.setPublisher( volumeInfo.get( "publisher" ) != null ? volumeInfo.get( "publisher" ).toString() : "n/d" );
                    book.setDescription( volumeInfo.get( "description" ) != null ? volumeInfo.get( "description" ).toString() : "n/d" );

                    ArrayList<Map<String, Object>>  identifiers = (ArrayList<Map<String, Object>>) volumeInfo.get("industryIdentifiers");
                    
                    if ( identifiers != null )
                    {
                        for ( Map<String,Object> key : identifiers )
                        {
                            if ( key.get("type") != null )
                            {
                                if ( key.get("type").toString().equals("ISBN_13") )
                                {
                                    book.setApiId( key.get( "identifier" ).toString() );
                                }
                            }
                        }
                    }

                    Map<String, Object>  images = (Map<String, Object>) volumeInfo.get("imageLinks" );
                    ArrayList<String> imagesLink = new ArrayList<>();

                    if ( images != null )
                    {
                        imagesLink.add( images.get( "smallThumbnail" ) != null ? images.get( "smallThumbnail" ).toString() : "n/d" );
                       book.setCoverLink( images.get( "thumbnail" ) != null ? images.get( "thumbnail" ).toString() : "n/d" );
                    }

                    book.setImageLinks( imagesLink );
                    book.setPageCount( volumeInfo.get( "pageCount" ) != null ? volumeInfo.get( "pageCount" ).toString() : "0");
                    book.setCategories( (ArrayList<String>) volumeInfo.get("categories") );
                    book.setLanguage( volumeInfo.get( "language" ).toString() );

                    book.setType( Media.TYPE_BOOK );

                }
                books.add(book);
            }
        }

        return books;
    }
}
