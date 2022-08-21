package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import com.example.core.data.User;
import com.example.db.persistence.UserDAO;
import com.example.db.providers.DAOFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Controller {
    
    @GetMapping( value="users/{id}" )
    public String getMethodName( @PathVariable( "id" ) int id ) {
        try {
            UserDAO userDAO = (UserDAO) DAOFactory.getInstance().get( UserDAO.class );
        
            User user = userDAO.get( id );
            if( user == null )
            {
                return "404 - Not Found";
            }

            return user.getName();

        } catch ( Exception e ) {
            return "500 - Internal Server Error";
        }
    }
    
}
