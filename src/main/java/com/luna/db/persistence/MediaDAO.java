package com.luna.db.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.luna.core.data.Media;
import com.luna.db.persistence.Schema.*;
import com.luna.db.providers.DAOFactory;
import com.luna.db.providers.DefaultDAO;

public class MediaDAO 
    extends 
        DefaultDAO 
            implements 
                MediaDAOI 
{
    public MediaDAO( DAOFactory driverManager ) 
    {
        super( driverManager );
    }

    @Override
    public void add( Media media ) throws SQLException 
    {
        int lastId = getLastId( "medias" );
        if( lastId == -1 )
        {
            throw new SQLException( "unable to increment media id!" );
        }

        media.setId( lastId + 1 );
        execute( 
            "insert into " + TableMedia.TABLE_NAME + "\n" +
            "   values( " + 
                    media.getId()           + ", '" + 
                    media.getTitle()        + "', '" + 
                    media.getYear()         + "', '" + 
                    media.getDescription()  + "', '" + 
                    media.getType()         + "', '" + 
                    media.getDescription()  + 
            "   ')" );
    }

    @Override
    public void delete( Media subject ) throws SQLException 
    {
        execute( "delete from " + TableMedia.TABLE_NAME + " where " + TableMedia.ID + " = " + subject.getId() );
    }
    
    public void deleteAllMaps( Media subject ) throws SQLException 
    {
        execute( "delete from " + TableListsMedias.TABLE_NAME + " where " + TableListsMedias.MEDIAS_ID + " = " + subject.getId() );
    }

    @Override
    public ArrayList<Media> fetch() throws SQLException 
    {
        return fetchMedias( "select * from " + TableMedia.TABLE_NAME );
    }

    @Override
    public void update( Media subject ) throws SQLException 
    {
        execute(
            "update " + TableMedia.TABLE_NAME + "\n" +
            "set\n" +
            "        " + TableMedia.TITLE        + " = '" + subject.getTitle()       + "',\n" +
            "        " + TableMedia.YEAR         + " = '" + subject.getYear()        + "',\n" +
            "        " + TableMedia.DESCRIPTION  + " = '" + subject.getDescription() + ",'\n"  +
            "        " + TableMedia.TYPE         + " = '" + subject.getType() + ",'\n"  +
            "        " + TableMedia.API_ID       + " = '" + subject.getApiId() + "'\n"  +
            "where\n" +
            "        " + TableMedia.ID + " = " + subject.getId()
        );
    }

    public Media getByApiId( int type, String apiId ) throws SQLException
    {
        return fetchMedia( 
                    "select * from " + TableMedia.TABLE_NAME + 
                    " where "        + TableMedia.API_ID + " = " + apiId +
                    " and "          + TableMedia.TYPE + " = " + type       
                );
    }

    @Override
    public Media get( int id ) throws SQLException 
    {
        return fetchMedia( "select * from " + TableMedia.TABLE_NAME + " where " + TableMedia.ID + " = " + id );
    }

    private ArrayList<Media> fetchMedias( String sql ) throws SQLException 
    {

        ArrayList<Media> Medias = new ArrayList<>();
        try 
        {
            ResultSet resultSet = fetch( sql );
            if ( resultSet.isBeforeFirst() ) 
            {
                while ( resultSet.next() ) 
                {
                    Medias.add( buildMedia( resultSet ) );
                }
            }
        } 
        finally 
        {
            connection.close();
        }

        return Medias;
    }

    private Media fetchMedia( String sql ) throws SQLException 
    {

        Media media = null;
        try 
        {
            ResultSet resultSet = fetch( sql );

            if ( resultSet.isBeforeFirst() ) 
            {
                while ( resultSet.next() ) 
                {

                    media = buildMedia( resultSet );
                }
            }
        } 
        catch ( SQLException e  ) 
        {
            media = null;
            throw e;

        } 
        finally 
        {
            connection.close();
        }

        return media;
    }

    private Media buildMedia( ResultSet resultSet ) throws SQLException
    {
        Media media = new Media();
        media.setId( resultSet.getInt( TableMedia.ID ) );
        media.setTitle( resultSet.getString( TableMedia.TITLE ) );
        media.setYear( resultSet.getInt( TableMedia.YEAR ) );
        media.setDescription( resultSet.getString( TableMedia.DESCRIPTION ) );
        media.setType( resultSet.getInt( TableMedia.TYPE ) );
        media.setApiId( resultSet.getString( TableMedia.API_ID ) );

        return media;
    }
}
