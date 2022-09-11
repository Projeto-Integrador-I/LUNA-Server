package com.luna.misc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApplicationContext {

    private static ApplicationContext defaultInstance;

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {

        if( defaultInstance == null ) {
            defaultInstance = new ApplicationContext();
        }

        return defaultInstance;
    }

    public static String encodePassword( String password ) 
    {
        return new BCryptPasswordEncoder().encode( password );
    }

}

