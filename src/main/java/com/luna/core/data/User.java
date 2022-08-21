package com.luna.core.data;

public class User {

    private int id;
    private String login;
    private String email;
    private String name;
    private String password;
    private int category;

    public final static int CATEGORY_INACTIVE   = 0;
    public final static int CATEGORY_USER       = 1;
    public final static int CATEGORY_ADMIN      = 2;

    public static String[] categories =
    {
        "inativo",
        "usuário",
        "administrador"
    };


    public User() {
    }

    public User( String name ) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
