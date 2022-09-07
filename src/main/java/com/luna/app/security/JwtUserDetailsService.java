package com.luna.app.security;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luna.db.persistence.UserDAO;
import com.luna.db.providers.DAOFactory;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserDAO userDAO = (UserDAO) DAOFactory.getInstance().get( UserDAO.class );
	
	@Override
	public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException 
    {
        com.luna.core.data.User user = null;
        
        try 
        {
            user = userDAO.get( login );    
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

		if ( user != null ) 
        {
			return new User( login, user.getPassword(), new ArrayList<>() );
		} 
        else 
        {
			throw new UsernameNotFoundException( "User not found with login: " + login );
		}
	}
}