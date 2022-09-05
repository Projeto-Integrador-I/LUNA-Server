package com.luna.app.rs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultResource 
{
    
    protected ResponseEntity<String> dynamicResponse( String info, int code ) 
    {
        return new ResponseEntity<>( info, HttpStatus.valueOf( code ) );
    }  

    protected ResponseEntity<String> notFound( String info ) 
    {
        return new ResponseEntity<>( info, HttpStatus.NOT_FOUND );
    }    

    protected ResponseEntity<String> badRequest( String info ) 
    {
        return new ResponseEntity<>( info, HttpStatus.BAD_REQUEST );
    }    

    protected ResponseEntity<String> internalServerError( String info ) 
    {
        return new ResponseEntity<>( info, HttpStatus.INTERNAL_SERVER_ERROR );
    }        

    protected ResponseEntity<String> ok( String jsonString ) 
    {
        return new ResponseEntity<>( jsonString, HttpStatus.OK );
    }        

    protected ResponseEntity<String> ok() 
    {
        return new ResponseEntity<>( HttpStatus.OK );
    }  

    protected ResponseEntity<String> created( String jsonString ) 
    {
        return new ResponseEntity<>( jsonString, HttpStatus.CREATED );
    }        

    protected ResponseEntity<String> noContent() 
    {
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }            
}
