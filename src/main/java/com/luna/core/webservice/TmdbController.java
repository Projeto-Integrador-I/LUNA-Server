package com.luna.core.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.misc.util.ApiBuilder;
import com.luna.misc.util.RequestProvider;

public class TmdbController
{

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

    public Object getMovieById( String id ) throws Exception
    {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "api_key", APIKEY );
        queryParams.put( "language", "pt-BR" );
        
        RequestProvider requestProvider = new RequestProvider( tmdbUrl );

        Map<String, Object> json = requestProvider.setPath( "movie/" + id )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        return ApiBuilder.buildMovie( json, id );
    }

    public List<Object> getMovieByName( String name ) throws Exception
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
        ArrayList<Object> movies = new ArrayList<>();

        for ( Map<String,Object> m : moviesMap )
        {
            movies.add( getMovieById(  m.get("id").toString().split("\\.")[0] ) );
        }
        
        return movies;
    }

    public Map<String, Object> getTrendingMovies() throws Exception
    {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "api_key", APIKEY );
        queryParams.put( "language", "pt-BR" );
        
        RequestProvider requestProvider = new RequestProvider( tmdbUrl );

        Map<String, Object> json = requestProvider.setPath( "trending/all/day" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();
        return json;
    }
}
