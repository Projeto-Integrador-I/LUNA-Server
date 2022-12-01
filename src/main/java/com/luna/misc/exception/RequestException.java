package com.luna.misc.exception;

public class RequestException extends Exception 
{
    private int code;

    public RequestException(String message) 
    {
        super( message );
    }

    public RequestException(String message, int code) 
    {
        super( message );
        this.code = code;
    }

    public RequestException(Throwable cause) 
    {
        super( cause );
    }

    public RequestException(String message, Throwable cause) 
    {
        super( message, cause );
    }

    public int getCode() {
        return code;
    }

    public void setCode( int code ) 
    {
        this.code = code;
    }
}