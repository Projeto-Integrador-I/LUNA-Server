package com.example.db.persistence;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.example.core.data.User;
import com.example.db.providers.DAOFactory;
import com.example.db.providers.DefaultDAO;

public class UserDAO extends DefaultDAO implements UserDAOI {

    public UserDAO( DAOFactory driverManager ) {
        super( driverManager );
    }

    @Override
    public void add( User user ) throws SQLException {

        int lastId = getLastId( "users" );
        if( lastId == -1 ) {
            throw new SQLException( "unable to increment user id!" );
        }

        user.setId( lastId + 1 );
        execute( "insert into users values( " + user.getId() + ", '" + user.getLogin() + "', '" + user.getPassword() + "', '" + user.getName() + "', '" + user.getEmail() + "')" );
    }

    @Override
    public void delete( User subject ) throws SQLException {

        execute( "delete from users where id = " + subject.getId() );
    }

    @Override
    public ArrayList<User> fetch() throws SQLException {
        return fetchUsers( "select * from users", "id" );
    }

    @Override
    public void update( User subject ) throws SQLException {

        execute(
        "update\n" +
            "        users\n" +
            "set\n" +
            "        login = '" + subject.getLogin() + "',\n" +
            "        password = '" + subject.getPassword() + "',\n" +
            "        name = '" + subject.getName() + "',\n" +
            "        email = '" + subject.getEmail() + "'\n" +
            "where\n" +
            "        id = " + subject.getId()
        );

    }

    @Override
    public User get( int id ) throws SQLException {

        return fetchUser( "select * from users where id = " + id );
    }

    public User get( String login ) throws SQLException {

        return fetchUser( "select * from users where login = '" + login + "'" );
    }

    private ArrayList<User> fetchUsers(String sql, String idColumn) throws SQLException {

        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = fetch( sql );
            if ( resultSet.isBeforeFirst() ) {
                while ( resultSet.next() ) {
                    users.add( Objects.requireNonNull( get( resultSet.getInt( idColumn ) ) ) );
                }
            }
        } finally {
            connection.close();
        }

        return users;
    }

    private User fetchUser(String sql) throws SQLException {

        User user = null;
        try {
            ResultSet resultSet = fetch( sql );

            if ( resultSet.isBeforeFirst() ) {
                while ( resultSet.next() ) {

                    user = new User();
                    user.setId( Integer.parseInt( resultSet.getString( "id" ) ) );
                    user.setName( resultSet.getString("name") );
                    user.setLogin( resultSet.getString("login") );
                    user.setPassword( resultSet.getString("password") );
                    user.setEmail( resultSet.getString("email") );
                }
            }
        } catch ( SQLException e  ) {
            user = null;
            throw e;

        } finally {
            connection.close();
        }

        return user;
    }
}
