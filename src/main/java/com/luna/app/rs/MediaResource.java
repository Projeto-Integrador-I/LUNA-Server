package com.luna.app.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.luna.core.data.Media;
import com.luna.core.data.MediaList;
import com.luna.core.webservice.BookController;
import com.luna.core.webservice.GameController;
import com.luna.core.webservice.MovieController;
import com.luna.db.persistence.MediaDAO;
import com.luna.db.persistence.MediaListDAO;
import com.luna.db.persistence.UserDAO;
import com.luna.db.providers.DAOFactory;

@RestController
public class MediaResource 
    extends
        DefaultResource
{
    private final MediaListDAO mediaListDAO = (MediaListDAO) DAOFactory.getInstance().get( MediaListDAO.class );;
    private final MediaDAO mediaDAO = (MediaDAO) DAOFactory.getInstance().get( MediaDAO.class );;
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().get( UserDAO.class );

    private final GameController gameController = GameController.getInstance();
    private final MovieController movieController = MovieController.getInstance();
    private final BookController bookController = BookController.getInstance();


    private final Gson gson = new Gson();


    @GetMapping( value="mediaLists/{id}/medias")
    public ResponseEntity<String> getMediasByMediaList( @PathVariable( "id" ) int id ) 
    {
        try 
        {
            MediaList mediaList = mediaListDAO.get( id );
            if ( mediaList == null )
            {    
                return notFound( "no such mediaList: " + id );
            }

            return ok( gson.toJson( mediaDAO.getByMediaListId( id ) ) );
        } 

        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

    @GetMapping( value="media/search" )
    public ResponseEntity<String> getMediaList( @RequestParam( "name" ) String name ) 
    {
        try 
        {
            List<Media> medias = new ArrayList<Media>();
            medias.addAll( gameController.getGamesByName( name )  == null ? new ArrayList<Media>() : gameController.getGamesByName( name ) );
            medias.addAll( movieController.getMovieByName( name, MovieController.TYPE_MOVIE  ) == null ? new ArrayList<Media>() : movieController.getMovieByName( name , MovieController.TYPE_MOVIE ) );
            medias.addAll( movieController.getMovieByName( name, MovieController.TYPE_TV  ) == null ? new ArrayList<Media>() : movieController.getMovieByName( name , MovieController.TYPE_TV ) );
            medias.addAll( bookController.getBookByName( name )   == null ? new ArrayList<Media>() : bookController.getBookByName( name ));

            if( medias.isEmpty() )
            {    
                return notFound( "no medias found" );
            }

            return ok( gson.toJson( medias ) );
        } 
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

    @GetMapping( value="media/getall" )
    public ResponseEntity<String> getMediasList()
    {
        try 
        {
            List<Media> medias = new ArrayList<Media>();

            medias.addAll( gameController.getTrandingGames() );
            medias.addAll( movieController.getAllTrandings() );

            Collections.shuffle( medias );

            return ok( gson.toJson( medias ) );
        }

        catch ( Exception e )
        {
            return internalServerError( e );
        }
    }

    @GetMapping( value="mediaLists/{id}" )
    public ResponseEntity<String> getMediaList( @PathVariable( "id" ) int id ) 
    {
        try 
        {
            MediaList mediaList = mediaListDAO.get( id );
            if ( mediaList == null )
            {    
                return notFound( "no such mediaList: " + id );
            }

            return ok( gson.toJson( mediaList ) );
        } 

        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

    @GetMapping( value="mediaLists" )
    public ResponseEntity<String> getMediaLists() 
    {
        try 
        {
            List<MediaList> mediaLists = mediaListDAO.fetch();
            if ( mediaLists.isEmpty() )
            {    
                return noContent();
            }

            return ok( gson.toJson( mediaLists ) );
        } 

        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

    @PostMapping( value="mediaLists" )
    public ResponseEntity<String> addMediaList( @RequestBody String body )
    {
        try 
        {
            MediaList mediaList = gson.fromJson( body, MediaList.class );

            if ( userDAO.get( mediaList.getUserId() )  == null )
            {
                return badRequest( "no such user: " + mediaList.getUserId() );
            }

            mediaListDAO.add( mediaList );

            return created( gson.toJson( mediaList ) );
        } 
        catch ( SQLException e )
        {
            return badRequest( e.getMessage() );
        }

        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }        
    }

    @PutMapping( value="mediaLists/{id}" )
    public ResponseEntity<String> updateMediaList( @RequestBody String body, @PathVariable( "id" ) int id )
    {
        try 
        {
            MediaList systemMediaList = mediaListDAO.get( id );
            if ( systemMediaList == null )
            {    
                return notFound( "no such mediaList: " + id );
            }

            MediaList mediaList = gson.fromJson( body, MediaList.class );

            if ( userDAO.get( mediaList.getUserId() )  == null )
            {
                return badRequest( "no such user: " + mediaList.getUserId() );
            }

            mediaList.setId( id );
            mediaListDAO.update( mediaList );

            return ok( gson.toJson( mediaList ) );
        } 

        catch( SQLException e )
        {
            return badRequest( e.getMessage() );
        }

        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }        
    }

    @DeleteMapping( value="mediaLists/{id}" )
    public ResponseEntity<String> deleteMediaList( @PathVariable( "id" ) int id ) 
    {
        try 
        {
            MediaList mediaList = mediaListDAO.get( id );

            if ( mediaList == null )
            {    
                return notFound( "no such mediaList: " + id );
            }

            mediaListDAO.delete( mediaList );

            return ok();
        } 

        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }


    @PostMapping( value="mediaLists/{mediaLists_id}/medias/{medias_id}" )
    public ResponseEntity<String> addMediaToMediaList( @PathVariable( "mediaLists_id" ) int mediaLists_id, @PathVariable( "medias_id" ) int medias_id ) 
    {
        try 
        {
            MediaList mediaList = mediaListDAO.get( mediaLists_id );

            if ( mediaList == null )
            {    
                return notFound( "no such mediaList: " + mediaLists_id );
            }

            Media media = mediaDAO.get( medias_id );

            if ( media == null )
            {    
                return notFound( "no such media: " + medias_id );
            }

            mediaListDAO.addMediaToList( mediaList, media );
            return ok();
        } 
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }    
}
