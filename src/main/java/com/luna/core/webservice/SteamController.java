package com.luna.core.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.luna.core.data.Game;
import com.luna.misc.util.RequestProvider;

import ch.qos.logback.core.joran.conditional.ElseAction;

public class SteamController {
    
    private static SteamController defaultInstance;
    private final HashMap<String, String> gamesMap = new HashMap<>();
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
                                                 .addHeader( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE )
                                                 .get();

        return buildGame( json );
    }

    Object buildGame( Map<String, Object> json )
    {
        Game game = new Game();
        Map <String, Object> app = (Map <String,Object>)json.get( "105600" );
        Map<String, Object> appData = (Map <String,Object>)app.get( "data" );

        Map<String, Object> priceOverview = (Map <String,Object>)appData.get( "price_overview" );
        Map<String, Object> releaseDateMap = (Map <String,Object>)appData.get( "release_date" );

        HashMap<String, String> images = new HashMap<>();
        images.put( "headerImage", appData.get( "header_image" ).toString() );

        ArrayList<Map<String, Object>>  screenShotsList= (ArrayList< Map<String, Object> >)appData.get( "screenshots" );

        for ( Map<String, Object> screenShotsMap : screenShotsList ) 
        {
            images.put( "path_thumbnail:" + screenShotsMap.get( "id" ), screenShotsMap.get( "path_thumbnail" ).toString() );
            images.put( "full_thumbnail:" + screenShotsMap.get( "id" ), screenShotsMap.get( "path_full" ).toString() );
        }
        
        game.setName( appData.get( "name" ).toString() != null ? appData.get( "name" ).toString() : "data not found");
        game.setAppId( (double)appData.get( "steam_appid" ) ); 
        game.setIs_free( (boolean)appData.get( "is_free" ) );
        game.setRequired_age( (double)appData.get( "required_age" ) ); 
        game.setDlcs( (ArrayList<Object>)appData.get( "dlc" ) );
        game.setDesc( appData.get( "detailed_description" ).toString() != null ? appData.get( "detailed_description" ).toString() : "data not found" );
        game.setAboutGame( appData.get( "about_the_game" ).toString() != null ? appData.get( "about_the_game" ).toString() : "data not found" );
        game.setShortDesc( appData.get( "short_description" ).toString() != null ? appData.get( "short_description" ).toString() : "data not found" );
        game.setImages(images);

        game.setOfficialWebSite( appData.get("website").toString() != null ? appData.get("website").toString() : "data not found" );
        game.setDevelopers( (ArrayList<String>)appData.get( "developers" ) );
        game.setPublishers( (ArrayList<String>)appData.get( "publishers" ) );
        game.setInicialPrice( priceOverview != null ? (double)priceOverview.get( "initial" ) : 0 );
        game.setFinalPrice( priceOverview != null ? (double)priceOverview.get( "final" ) : 0 );
        game.setDiscountPercent( priceOverview != null ? (double)priceOverview.get( "discount_percent" ) : 0 );
        game.setFinalPriceFormatted( priceOverview != null ? priceOverview.get( "final_formatted" ).toString() : "data not found" );
        game.setCategories( (ArrayList<String>)appData.get( "categories" ) );
        game.setGenres( (ArrayList<String>)appData.get( "genres" ) );
        game.setReleaseDate( ( releaseDateMap != null && !releaseDateMap.isEmpty() ) ? releaseDateMap.get("date").toString() : "data not found" );
        
        return game;
    }

}
