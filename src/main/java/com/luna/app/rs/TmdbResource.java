package com.luna.app.rs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.luna.core.webservice.TmdbController;
import com.luna.misc.exception.RequestException;

@RestController
public class TmdbResource 
    extends 
        DefaultResource
{
    private final TmdbController controller = TmdbController.getInstance();
    private final Gson gson = new Gson();

    public TmdbResource(){}

    @GetMapping( value="movie" )
    public ResponseEntity<String> getUser( @RequestParam( "id" ) String id ) 
    {
        try 
        {
            Object movie = controller.getMovieById( id );

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

