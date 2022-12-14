package com.luna.db.providers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DefaultDAO {

    private final DAOFactory driverManager;
    protected Connection connection;

    protected DefaultDAO( DAOFactory driverManager )
    {
        this.driverManager = driverManager;
    }

    protected ResultSet fetch( String sql ) throws SQLException 
    {

        Statement statement = null;
        ResultSet resultSet = null;

        connection = driverManager.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        return resultSet;
    }

    protected void execute( String sql ) throws SQLException 
    {

        Statement statement = null;

        connection = driverManager.getConnection();
        statement = connection.createStatement();
        statement.execute( sql );
    }

    protected int getLastId( String table ) 
    {

        Statement statement = null;
        ResultSet resultSet = null;
        int maxId = 0;

        try 
        {
            connection = driverManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery( "select max(ID) as id from " + table);

            if( resultSet.isBeforeFirst() ) 
            {
                resultSet.next();
                maxId = resultSet.getInt( "id" );
            }

        } 
        catch ( SQLException e ) 
        {
            System.err.println( "Erro ao executar SQL: " + e );
            maxId = -1;
        }

        return maxId;
    }
}
