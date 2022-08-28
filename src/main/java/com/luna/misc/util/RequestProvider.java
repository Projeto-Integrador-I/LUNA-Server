package com.luna.misc.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.luna.misc.exception.RequestException;

public class RequestProvider {
    
    private final Gson gson = new Gson();
    private String url = null;
    private HashMap<String, String> queryParams = new HashMap<String, String>();
    private String path = null;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public RequestProvider( String url )
    {
        this.url = url;
    }

    public RequestProvider seturl( String url )
    {
        this.url = url;
        return this;
    } 

    public RequestProvider setPath( String path )
    {
        this.path = path;
        return this;
    }

    public RequestProvider setQueryParam( HashMap<String, String> queryParams )
    {
        this.queryParams = queryParams;
        return this;
    }

    public RequestProvider addHeader( String header, String value )
    {
        this.headers.put( header, value );
        return this;
    }


    public Map<String, Object> get() throws Exception
    {
        validate();
        Map<String, Object> jsonMap = new HashMap<>();

        URL URL = new URL( url + "/" + path  +  buildParams() );
        HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
        connection.setRequestMethod( "GET" );

        for( String header : headers.keySet() )
        {
            connection.addRequestProperty( header, headers.get( header ) );
        }
        
        int responseCode = connection.getResponseCode();
        if( responseCode >= 200 && responseCode <= 299 )
        {
            InputStream responseStream = connection.getInputStream();
            jsonMap = gson.fromJson( new String(responseStream.readAllBytes()) , Map.class);
        }
        else
        {
            throw new RequestException( connection.getResponseMessage(), responseCode );
        }

        return jsonMap;
    }

    private void validate() throws Exception
    {
        Validator.requireNotNullOrEmpty( url, new IllegalArgumentException( "baserUrl not defined" ) );
        Validator.requireNotNullOrEmpty( path, new IllegalArgumentException( "path not defined" ) );
    }

    private String buildParams()
    {
        String params = "";

        for( String key : queryParams.keySet() )
        {
            params += params.isEmpty() ? "?" + key + "=" + queryParams.get( key ) : "&" + key + "=" + queryParams.get( key ); 
        }

        return params;
    }
}