package com.luna.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.luna.app.rs.DefaultResource;

@RestController
@CrossOrigin
public class JwtAuthenticationController
    extends
        DefaultResource
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken( @RequestBody JwtRequest authenticationRequest ) throws Exception 
    {
        try
        {
            try
            {
                authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( authenticationRequest.getUsername(), authenticationRequest.getPassword() ) );
    
                final UserDetails userDetails = userDetailsService.loadUserByUsername( authenticationRequest.getUsername() );
                
                final String token = jwtTokenUtil.generateToken( userDetails );
                
                return ResponseEntity.ok(new JwtResponse(token));
            }
            catch ( DisabledException e ) 
            {
                return unauthorized( "USER_DISABLED: " + e.getMessage() );
            } 
            catch ( BadCredentialsException e ) 
            {
                return unauthorized( "INVALID_CREDENTIALS: " + e.getMessage() );
            }
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }
}