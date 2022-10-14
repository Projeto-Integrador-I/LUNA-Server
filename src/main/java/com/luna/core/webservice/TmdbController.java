package com.luna.core.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.core.data.Movie;
import com.luna.misc.util.ApiBuilder;
import com.luna.misc.util.RequestProvider;

public class TmdbController
{

    public static final int TYPE_MOVIE = 0;
    public static final int TYPE_TV = 1;

    private static String[] categories =
    {
        "movie/",
        "tv/"
    };

    private static TmdbController defaultInstance;
    private static final String APIKEY = "2063193afeb67536cd95e217e1b4210e";
    private final String tmdbUrl = "http://api.themoviedb.org/3";


    private TmdbController() {}

    public static TmdbController getInstance() 
    {
        if ( defaultInstance == null ) 
        {
            defaultInstance = new TmdbController();
        }

        return defaultInstance;
    }

    public Movie getMovieById( String id, int type ) throws Exception
    {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "api_key", APIKEY );
        queryParams.put( "language", "pt-BR" );
        
        RequestProvider requestProvider = new RequestProvider( tmdbUrl );

        Map<String, Object> json = requestProvider.setPath( categories[type] + id )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        return ApiBuilder.buildMovie( json, id );
    }

    public List<Movie> getMovieByName( String name ) throws Exception
    {
        //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
        name = name.replaceAll( " ", "+" );

        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "api_key", APIKEY );
        queryParams.put( "query", name );
        queryParams.put( "language", "pt-BR" );
        
        RequestProvider requestProvider = new RequestProvider( tmdbUrl );

        Map<String, Object> json = requestProvider.setPath( "search/movie" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        ArrayList<Map<String, Object>>  moviesMap = (ArrayList< Map<String, Object> >)json.get( "results" );
        ArrayList<Movie> movies = new ArrayList<>();

        for ( Map<String,Object> m : moviesMap )
        {
            movies.add( getMovieById(  m.get("id").toString().split("\\.")[0], TYPE_MOVIE ) );
        }
        
        return movies;
    }

    public List<Movie> getTrendingMovies() throws Exception
    {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "api_key", APIKEY );
        queryParams.put( "language", "pt-BR" );
        
        RequestProvider requestProvider = new RequestProvider( tmdbUrl );

        Map<String, Object> json = requestProvider.setPath( "trending/movie/week" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        ArrayList<Map<String, Object>>  moviesMap = (ArrayList< Map<String, Object> >)json.get( "results" );
        ArrayList<Movie> movies = new ArrayList<>();

        for ( Map<String,Object> m : moviesMap )
        {
            movies.add( getMovieById(  m.get("id").toString().split("\\.")[0], TYPE_MOVIE ) );
        }
                                                  
        return movies;
    }

    public List<Movie> getTrandingTv() throws Exception
    {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "api_key", APIKEY );
        queryParams.put( "language", "pt-BR" );
        
        RequestProvider requestProvider = new RequestProvider( tmdbUrl );

        Map<String, Object> json = requestProvider.setPath( "trending/tv/week" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        ArrayList<Map<String, Object>>  moviesMap = (ArrayList< Map<String, Object> >)json.get( "results" );
        ArrayList<Movie> movies = new ArrayList<>();

        for ( Map<String,Object> m : moviesMap )
        {
            movies.add( getMovieById(  m.get("id").toString().split("\\.")[0], TYPE_TV ) );
        }
                                                  
        return movies;
    }
}
