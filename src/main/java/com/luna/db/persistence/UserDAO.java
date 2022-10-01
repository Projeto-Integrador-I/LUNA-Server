package com.luna.db.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.luna.core.data.User;
import com.luna.db.persistence.Schema.TableUser;
import com.luna.db.providers.DAOFactory;
import com.luna.db.providers.DefaultDAO;

public class UserDAO 
    extends 
        DefaultDAO 
            implements 
                UserDAOI 
{

    public UserDAO( DAOFactory driverManager ) 
    {
        super( driverManager );
    }

    @Override
    public void add( User user ) throws SQLException 
    {

        int lastId = getLastId( "users" );
        if( lastId == -1 )
        {
            throw new SQLException( "unable to increment user id!" );
        }

        user.setId( lastId + 1 );
        execute( 
            "insert into " + TableUser.TABLE_NAME + "\n" +
            "   values( " + 
                    user.getId()        + ", '" + 
                    user.getLogin()     + "', '" + 
                    user.getPassword()  + "', '" + 
                    user.getName()      + "', '" + 
                    user.getEmail()     + "', '" + 
                    user.getCategory() + 
            "   ')" );
    }

    @Override
    public void delete( User subject ) throws SQLException 
    {
        //TODO: cascatear delete
        execute( "delete from " + TableUser.TABLE_NAME + " where " + TableUser.ID + " = " + subject.getId() );
    }

    @Override
    public ArrayList<User> fetch() throws SQLException 
    {
        return fetchUsers( "select * from " + TableUser.TABLE_NAME );
    }

    @Override
    public void update( User subject ) throws SQLException 
    {

        execute(
            "update " + TableUser.TABLE_NAME + "\n" +
            "set\n" +
            "        " + TableUser.LOGIN        + "= '" + subject.getLogin()    + "',\n" +
            "        " + TableUser.PASSWORD     + "= '" + subject.getPassword() + "',\n" +
            "        " + TableUser.NAME         + "= '" + subject.getName()     + "',\n" +
            "        " + TableUser.CATEGORY     + "= '" + subject.getCategory() + "',\n" +
            "        " + TableUser.EMAIL        + "= '" + subject.getEmail()    + "'\n" +
            "where\n" +
            "        " + TableUser.ID + " = " + subject.getId()
        );
    }

    @Override
    public User get( int id ) throws SQLException 
    {
        return fetchUser( "select * from " + TableUser.TABLE_NAME + " where " + TableUser.ID + " = " + id );
    }

    public User get( String login ) throws SQLException 
    {
        return fetchUser( "select * from " + TableUser.TABLE_NAME + " where " + TableUser.LOGIN + " = '" + login + "'" );
    }

    private ArrayList<User> fetchUsers(String sql ) throws SQLException 
    {

        ArrayList<User> users = new ArrayList<>();
        try 
        {
            ResultSet resultSet = fetch( sql );
            if ( resultSet.isBeforeFirst() ) 
            {
                while ( resultSet.next() ) 
                {
                    users.add( buildUser( resultSet ) );
                }
            }
        } 
        finally 
        {
            connection.close();
        }

        return users;
    }

    private User fetchUser( String sql ) throws SQLException 
    {

        User user = null;
        try 
        {
            ResultSet resultSet = fetch( sql );

            if ( resultSet.isBeforeFirst() ) 
            {
                while ( resultSet.next() ) 
                {

                    user = buildUser( resultSet );
                }
            }
        } 
        catch ( SQLException e  ) 
        {
            user = null;
            throw e;

        } 
        finally 
        {
            connection.close();
        }

        return user;
    }

    private User buildUser( ResultSet resultSet ) throws SQLException
    {
        User user = new User();
        user.setPassword( resultSet.getString( TableUser.PASSWORD) );
        user.setCategory( resultSet.getInt( TableUser.CATEGORY) );        
        user.setName( resultSet.getString( TableUser.NAME ) );
        user.setEmail( resultSet.getString( TableUser.EMAIL ) );
        user.setLogin( resultSet.getString( TableUser.LOGIN ) );
        user.setId( resultSet.getInt( TableUser.ID ) );

        return user;
    }
}
