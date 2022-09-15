package com.luna.db.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.luna.core.data.MediaList;
import com.luna.db.persistence.Schema.TableMediaList;
import com.luna.db.providers.DAOFactory;
import com.luna.db.providers.DefaultDAO;

public class MediaListDAO 
    extends 
        DefaultDAO 
            implements 
                MediaListDAOI 
{
    public MediaListDAO( DAOFactory driverManager ) 
    {
        super( driverManager );
    }

    @Override
    public void add( MediaList mediaList ) throws SQLException 
    {
        int lastId = getLastId( "mediaLists" );
        if( lastId == -1 )
        {
            throw new SQLException( "unable to increment mediaList id!" );
        }

        mediaList.setId( lastId + 1 );
        execute( 
            "insert into " + TableMediaList.TABLE_NAME + "\n" +
            "   values( " + 
                    mediaList.getId()        + ", '" + 
                    mediaList.getName()      + "', '" + 
                    mediaList.getUserId()     + "', '" + 
                    mediaList.getDescription() + 
            "   ')" );
    }

    @Override
    public void delete( MediaList subject ) throws SQLException 
    {

        execute( "delete from " + TableMediaList.TABLE_NAME + " where " + TableMediaList.ID + " = " + subject.getId() );
    }

    @Override
    public ArrayList<MediaList> fetch() throws SQLException 
    {
        return fetchMediaLists( "select * from " + TableMediaList.TABLE_NAME );
    }

    @Override
    public void update( MediaList subject ) throws SQLException 
    {
        execute(
            "update " + TableMediaList.TABLE_NAME + "\n" +
            "set\n" +
            "        " + TableMediaList.NAME         + "= '" + subject.getName()        + "',\n" +
            "        " + TableMediaList.USERS_ID     + "= '" + subject.getUserId()      + "',\n" +
            "        " + TableMediaList.DESCRIPTION  + "= '" + subject.getDescription() + "'\n" +
            "where\n" +
            "        " + TableMediaList.ID + " = " + subject.getId()
        );
    }

    @Override
    public MediaList get( int id ) throws SQLException 
    {
        return fetchMediaList( "select * from " + TableMediaList.TABLE_NAME + " where " + TableMediaList.ID + " = " + id );
    }

    private ArrayList<MediaList> fetchMediaLists( String sql ) throws SQLException 
    {

        ArrayList<MediaList> MediaLists = new ArrayList<>();
        try 
        {
            ResultSet resultSet = fetch( sql );
            if ( resultSet.isBeforeFirst() ) 
            {
                while ( resultSet.next() ) 
                {
                    MediaLists.add( buildMediaList( resultSet ) );
                }
            }
        } 
        finally 
        {
            connection.close();
        }

        return MediaLists;
    }

    private MediaList fetchMediaList( String sql ) throws SQLException 
    {

        MediaList mediaList = null;
        try 
        {
            ResultSet resultSet = fetch( sql );

            if ( resultSet.isBeforeFirst() ) 
            {
                while ( resultSet.next() ) 
                {

                    mediaList = buildMediaList( resultSet );
                }
            }
        } 
        catch ( SQLException e  ) 
        {
            mediaList = null;
            throw e;

        } 
        finally 
        {
            connection.close();
        }

        return mediaList;
    }

    private MediaList buildMediaList( ResultSet resultSet ) throws SQLException
    {
        MediaList mediaList = new MediaList();
        mediaList.setId( resultSet.getInt( TableMediaList.ID ) );
        mediaList.setName( resultSet.getString( TableMediaList.NAME ) );
        mediaList.setUserId( resultSet.getInt( TableMediaList.USERS_ID ) );
        mediaList.setDescription( resultSet.getString( TableMediaList.DESCRIPTION ) );

        return mediaList;
    }

}
