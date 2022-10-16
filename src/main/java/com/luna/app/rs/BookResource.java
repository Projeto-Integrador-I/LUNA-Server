package com.luna.app.rs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.luna.core.data.Book;
import com.luna.core.webservice.BookController;
import com.luna.misc.exception.RequestException;

@RestController
public class BookResource 
    extends 
        DefaultResource
{
    private final BookController controller = BookController.getInstance();
    private final Gson gson = new Gson();

    public BookResource(){}

    @GetMapping( value="book" )
    public ResponseEntity<String> getBookByName( @RequestParam( "name" ) String name ) 
    {
        try 
        {
            List<Book> movie = controller.getBookByName( name );

            if ( movie == null )
            {
                return notFound( "book not found for the given name" );
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

