package com.luna.core.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.misc.util.RequestProvider;
import com.luna.core.data.Book;
import com.luna.misc.util.ApiBuilder;


public class BookController 
{
    private static BookController defaultInstance;
    private final String gBooksUrl = "https://www.googleapis.com/books";
    private static final String APIKEY = "AIzaSyAQ2vcHDt0mXfKbRNDpUD3vICT7OUvZ77U";

    private BookController() {}

    public static BookController getInstance() 
    {
        if ( defaultInstance == null ) 
        {
            defaultInstance = new BookController();
        }

        return defaultInstance;
    }

    public List<Book> getBookByName( String name ) throws Exception 
    {
        name = name.replaceAll( " ", "+" );

        HashMap<String, String> queryParams = new HashMap<String, String>();

        queryParams.put( "q", name );
        queryParams.put( "key", APIKEY );
        queryParams.put("maxResults", "25");
        
        RequestProvider requestProvider = new RequestProvider( gBooksUrl );

        Map<String, Object> json = requestProvider.setPath( "v1/volumes" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        return ApiBuilder.buidBook( json );
    }
}