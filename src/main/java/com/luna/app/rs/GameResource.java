package com.luna.app.rs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.luna.core.data.Game;
import com.luna.core.webservice.GameController;
import com.luna.misc.exception.RequestException;

@RestController
public class GameResource 
    extends 
        DefaultResource
{
    private final GameController controller = GameController.getInstance();
    private final Gson gson = new Gson();

    public GameResource(){}

    @GetMapping( value="games" )
    public ResponseEntity<String> getUser( @RequestParam( "appids" ) String appids ) 
    {
        try 
        {
            Game game = controller.getGameByAppId( appids );
            
            if ( game == null )
            {
                return notFound( "game not found for the given appids" );
            }

            return ok( gson.toJson( game ) );
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
    
    @GetMapping( value = "game")
    public ResponseEntity<String> gameName( @RequestParam( "name" ) String name )
    {
        try 
        {
            List<Game> games = controller.getGamesByName( name );

            if ( games == null || games.isEmpty() )
            {
                return notFound( "game not found for the given name" );
            }

            return ok( gson.toJson( games ) );
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

    @GetMapping( value = "most-played" )
    public ResponseEntity<String> getMostPlayedGames()
    {
        try 
        {
            List<Game> games = controller.getMostPlayedGames();

            if ( games == null || games.isEmpty() )
            {
                return notFound( "no games found" );
            }

            return ok( gson.toJson( games ) );
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

