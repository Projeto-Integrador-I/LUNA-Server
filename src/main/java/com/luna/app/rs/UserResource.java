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
import com.luna.core.data.User;
import com.luna.db.persistence.UserDAO;
import com.luna.db.providers.DAOFactory;
import com.luna.misc.util.ApplicationContext;

@RestController
public class UserResource 
    extends
        DefaultResource
{
    private final UserDAO userDAO;
    private final Gson gson;

    public UserResource()
    {
        this.userDAO = (UserDAO) DAOFactory.getInstance().get( UserDAO.class );
        gson = new Gson();
    }

    @GetMapping( value="users/{id}" )
    public ResponseEntity<String> getUser( @PathVariable( "id" ) int id ) 
    {
        try 
        {
            User user = userDAO.get( id );
            if( user == null )
            {    
                return notFound( "no such user: " + id );
            }

            return ok( gson.toJson( user ) );
        } 
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

    @GetMapping( value="users" )
    public ResponseEntity<String> getUsers() 
    {
        try 
        {
            List<User> users = userDAO.fetch();
            if( users.isEmpty() )
            {    
                return noContent();
            }

            return ok( gson.toJson( users ) );
        } 
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

    @PostMapping( value="users" )
    public ResponseEntity<String> addUser( @RequestBody String body )
    {
        try 
        {
            User user = gson.fromJson( body, User.class );
            user.setPassword( ApplicationContext.encodePassword( user.getPassword() ) );
            userDAO.add(user);


            return created( gson.toJson( user ) );
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

    @PutMapping( value="users/{id}" )
    public ResponseEntity<String> updateUser( @RequestBody String body, @PathVariable( "id" ) int id )
    {
        try 
        {
            User systemUser = userDAO.get( id );
            if( systemUser == null )
            {    
                return notFound( "no such user: " + id );
            }

            User user = gson.fromJson( body, User.class );
            user.setPassword( ApplicationContext.encodePassword( user.getPassword() ) );            
            user.setId( id );

            userDAO.update( user );

            return ok( gson.toJson( user ) );
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

    @DeleteMapping( value="users/{id}" )
    public ResponseEntity<String> deleteUser( @PathVariable( "id" ) int id ) 
    {
        try 
        {
            User user = userDAO.get( id );
            if( user == null )
            {    
                return notFound( "no such user: " + id );
            }

            user.setCategory( User.CATEGORY_INACTIVE );
            userDAO.update( user );

            return ok();
        } 
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }
}
