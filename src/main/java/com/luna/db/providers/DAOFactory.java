package com.luna.db.providers;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAOFactory {

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DATABASE = "database";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private static DAOFactory instance;

    public static DAOFactory getInstance() throws DAOConfigurationException {

        if( instance == null )
        {
            DAOProperties properties = new DAOProperties();
            String url = properties.getProperty( PROPERTY_URL, true );
            String database = properties.getProperty( PROPERTY_DATABASE, true );
            String password = properties.getProperty( PROPERTY_PASSWORD, false );
            String username = properties.getProperty( PROPERTY_USERNAME, password != null );

            instance = new DriverManagerDAOFactory(url, database, username, password);
        }

        return  instance;
    }

    abstract Connection getConnection() throws SQLException;

    public DAO<?> get(Class<?> clazz) throws DAOConfigurationException {

        try {
            Constructor<?> constructor = clazz.getConstructor( DAOFactory.class );
            return (DAO<?>) constructor.newInstance( (DriverManagerDAOFactory) instance);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new DAOConfigurationException( "Failed to get '" + clazz.getName() + "'", e );
        }
    }

    public ConnectionProvider getConnectionProvider() {
        return new ConnectionProvider( this );
    }


}
    class DriverManagerDAOFactory extends DAOFactory {
        private final String url;
        private final String database;
        private final String username;
        private final String password;

        protected DriverManagerDAOFactory( String url, String database, String username, String password ) {

            this.url = url;
            this.database = database;
            this.username = username;
            this.password = password;
        }

        @Override
        Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url + "/" + database, username, password);
        }
    }

