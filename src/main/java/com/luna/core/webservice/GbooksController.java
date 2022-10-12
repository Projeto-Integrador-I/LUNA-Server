package com.luna.core.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.misc.util.RequestProvider;
import com.luna.core.data.Book;
import com.luna.misc.util.ApiBuilder;


public class GbooksController 
{
    private static GbooksController defaultInstance;
    private final String gBooksUrl = "https://www.googleapis.com/books";
    private static final String APIKEY = "AIzaSyAQ2vcHDt0mXfKbRNDpUD3vICT7OUvZ77U";

    private GbooksController() {}

    public static GbooksController getInstance() 
    {
        if ( defaultInstance == null ) 
        {
            defaultInstance = new GbooksController();
        }

        return defaultInstance;
    }

    public List<Book> getBook( String name ) throws Exception 
    {
        //https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes&key=yourAPIKey
        name = name.replaceAll( " ", "+" );

        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "q", name );
        queryParams.put( "key", APIKEY );
        RequestProvider requestProvider = new RequestProvider( gBooksUrl );

        Map<String, Object> json = requestProvider.setPath( "v1/volumes" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        return ApiBuilder.buidBook( json );
    }
}