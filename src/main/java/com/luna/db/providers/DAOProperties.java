package com.luna.db.providers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import com.luna.misc.exception.DAOConfigurationException;

public class DAOProperties {

    private static final String PROPERTIES_FILE = "dao.properties";
    private static final Properties PROPERTIES = new Properties();

    static {

        try {

            ClassPathResource resource = new ClassPathResource( PROPERTIES_FILE );
            InputStream inputStream = resource.getInputStream();
            
            PROPERTIES.load( inputStream );
        } catch (IOException e) {
            throw new DAOConfigurationException( "Cannot load properties file '" + PROPERTIES_FILE + "'.", e );
        }
    }

    public String getProperty(String key, boolean mandatory) throws DAOConfigurationException {
        String property = PROPERTIES.getProperty( key );

        if (property == null || property.trim().length() == 0) {
            if ( mandatory ) {
                throw new DAOConfigurationException("Required property '" + key + "'" + " is missing in properties file '" + PROPERTIES_FILE + "'.");
            } else {
                property = null;
            }
        }
        return property;
    }

}
