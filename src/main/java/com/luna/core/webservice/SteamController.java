package com.luna.core.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.misc.util.RequestProvider;

public class SteamController {
    
    private static SteamController defaultInstance;
    private final String steamUrl = "https://store.steampowered.com/api/";

    private SteamController() {}

    public static SteamController getInstance() {

        if( defaultInstance == null ) {
            defaultInstance = new SteamController();
        }

        return defaultInstance;
    }

    //TODO: switch object to Game
    public Object getGameByAppId( String appids ) throws Exception {

        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "appids", appids );

        RequestProvider requestProvider = new RequestProvider( steamUrl );

        Map<String, Object> json = requestProvider.setPath( "appdetails" )
                                         .setQueryParam( queryParams )
                                         .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                         .get();

        return buildGame( json );
    }


    Object buildGame( Map<String, Object> json )
    {
        //TODO: deserialize json to Game
        return null;
    }
}
