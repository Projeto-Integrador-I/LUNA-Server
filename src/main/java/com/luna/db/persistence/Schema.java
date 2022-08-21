package com.luna.db.persistence;

public class Schema {
    
    public static class TableUser
    {
        public final static String TABLE_NAME = "users";
        
        //columns
        public final static String ID           = "id";
        public final static String LOGIN        = "login";
        public final static String EMAIL        = "email";
        public final static String NAME         = "name";
        public final static String PASSWORD     = "password";
        public final static String CATEGORY     = "category";
    }

}
