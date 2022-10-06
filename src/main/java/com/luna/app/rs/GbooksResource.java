package com.luna.app.rs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.luna.core.data.Book;
import com.luna.core.webservice.GbooksController;
import com.luna.misc.exception.RequestException;

@RestController
public class GbooksResource 
    extends 
        DefaultResource
{
    private final GbooksController controller = GbooksController.getInstance();
    private final Gson gson = new Gson();

    public GbooksResource(){}

    @GetMapping( value="book" )
    public ResponseEntity<String> getMovieById( @RequestParam( "name" ) String name ) 
    {
        try 
        {
            List<Book> movie = controller.getBook( name );

            if ( movie == null )
            {
                return notFound( "movie not found for the given id" );
            }

            return ok( gson.toJson( movie ) );
        }

        catch( RequestException e )
        {
            return dynamicResponse( e.getMessage(), e.getCode() );
        }
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }
}

