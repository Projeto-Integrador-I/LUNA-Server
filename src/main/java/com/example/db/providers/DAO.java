package com.example.db.providers;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO<T>{

    public void add( T subject ) throws SQLException;
    public void delete( T subject ) throws SQLException;
    public ArrayList<T> fetch() throws SQLException;
    public void update( T subject ) throws SQLException;
    public T get( int id ) throws SQLException;
}
