package com.luna.misc.util;

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
}

