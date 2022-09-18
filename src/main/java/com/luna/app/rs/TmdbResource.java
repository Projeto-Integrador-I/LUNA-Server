package com.luna.app.rs;

import java.util.List;

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
    public ResponseEntity<String> getMovieById( @RequestParam( "id" ) String id ) 
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

    @GetMapping( value="movies" )
    public ResponseEntity<String> getMovieByname( @RequestParam( "name" ) String name ) 
    {
        try 
        {
            List<Object> movies = controller.getMovieByName( name );

            if ( movies == null )
            {
                return notFound( "movie not found for the given name" );
            }

            return ok( gson.toJson( movies ) );
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

