package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private  String fullname;
    private String username;
    private String pass;
    private String type;

    public User(int id, String fullname, String username, String pass, String type) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.pass = pass;
        this.type = type;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User((int) rs.getInt("id"), rs.getString("fullname"),
                        rs.getString("username"), rs.getString("pass"), rs.getString("user_type"));
                users.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
