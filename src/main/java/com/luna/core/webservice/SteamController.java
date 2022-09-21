package com.luna.core.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.misc.util.RequestProvider;
import com.luna.misc.util.ApiBuilder;


public class SteamController 
{
    private static SteamController defaultInstance;
    private final HashMap<String, String> gamesMap = new HashMap<>();
    private final String steamUrl = "https://store.steampowered.com/api/";

    private SteamController() {}

    public static SteamController getInstance() 
    {
        if ( defaultInstance == null ) 
        {
            defaultInstance = new SteamController();
        }

        return defaultInstance;
    }

    public List<Object> getGamesByName( String name ) throws Exception
    {
        RequestProvider requestProvider = new RequestProvider( "http://api.steampowered.com/ISteamApps/GetAppList/" );

        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "key", "STEAMKEY" );
        queryParams.put( "format", "json" );
        
        Map<String, Object> json = requestProvider.setPath( "v0002" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();
                                                 
        Map<String, Object> app = (Map<String, Object>)json.get( "applist" );
        ArrayList<Object> appList = (ArrayList<Object>)app.get( "apps" );
        
        List<Object> result = appList.stream().filter( g -> g.toString().toLowerCase().contains( name.toLowerCase() ) ).collect( Collectors.toList() );

        ArrayList<Object> game = new ArrayList<>();

        for ( Object gameFiltered : result )
        {
            String[] data = gameFiltered.toString().replaceAll("=|appid|name|\\{|\\}","").split( ",");

            game.add( getGameByAppId( data[0].toString().split("\\.")[0] ) );
        }

        return game;                                              
    }

    public Object getGameByAppId( String appId ) throws Exception 
    {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put( "appids", appId );
        queryParams.put( "l", "portuguese" );

        RequestProvider requestProvider = new RequestProvider( steamUrl );

        Map<String, Object> json = requestProvider.setPath( "appdetails" )
                                                  .setQueryParam( queryParams )
                                                  .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                  .get();

        return ApiBuilder.buildGame( json, appId );
    }
}
