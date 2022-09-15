package com.luna.app.rs;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.luna.core.data.MediaList;
import com.luna.db.persistence.MediaListDAO;
import com.luna.db.providers.DAOFactory;

@RestController
public class MediaListResource 
    extends
        DefaultResource
{
    private final MediaListDAO mediaListDAO;
    private final Gson gson;

    public MediaListResource()
    {
        this.mediaListDAO = (MediaListDAO) DAOFactory.getInstance().get( MediaListDAO.class );
        gson = new Gson();
    }

    @GetMapping( value="mediaLists/{id}" )
    public ResponseEntity<String> getMediaList( @PathVariable( "id" ) int id ) 
    {
        try 
        {
            MediaList mediaList = mediaListDAO.get( id );
            if( mediaList == null )
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
            if( mediaLists.isEmpty() )
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
            mediaListDAO.add(mediaList);


            return created( gson.toJson( mediaList ) );
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

    @PutMapping( value="mediaLists/{id}" )
    public ResponseEntity<String> updateMediaList( @RequestBody String body, @PathVariable( "id" ) int id )
    {
        try 
        {
            MediaList systemMediaList = mediaListDAO.get( id );
            if( systemMediaList == null )
            {    
                return notFound( "no such mediaList: " + id );
            }

            MediaList mediaList = gson.fromJson( body, MediaList.class );
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
            if( mediaList == null )
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
}
