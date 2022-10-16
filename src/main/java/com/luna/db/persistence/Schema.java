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

    public static class TableMediaList
    {
        public final static String TABLE_NAME = "lists";
        
        //columns
        public final static String ID           = "id";
        public final static String NAME         = "name";
        public final static String USERS_ID     = "users_id";
        public final static String DESCRIPTION  = "description";
    }

    public static class TableListsMedias
    {
        public final static String TABLE_NAME = "lists_medias";
        
        //columns
        public final static String MEDIAS_ID  = "medias_id";
        public final static String LISTS_ID   = "lists_id";
    }

    public static class TableMedia
    {
        public final static String TABLE_NAME = "medias";
        
        //columns
        public final static String ID           = "id";
        public final static String TITLE        = "title";
        public final static String YEAR         = "year";
        public final static String DESCRIPTION  = "description";
        public final static String TYPE         = "type";
        public final static String API_ID       = "api_id";
        public final static String COVER        = "cover";
    }

}
