package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
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

    public static boolean add(String fullname, String username, String pass, String type) {
        String query = "INSERT INTO users (fullname, username, pass, user_type) VALUES (?,?,?,?)";
        User findUser = User.getFetchByUsername(username);
        if (findUser != null) {
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, fullname);
            pr.setString(2, username);
            pr.setString(3, pass);
            pr.setString(4, type);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getFetchByUsername(String username) {
        User obj = null;
        String query = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User((int) rs.getInt("id"), rs.getString("fullname"),
                        rs.getString("username"), rs.getString("pass"), rs.getString("user_type"));
            }
            pr.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public static User getFetchById(int id) {
        User obj = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User((int) rs.getInt("id"), rs.getString("fullname"),
                        rs.getString("username"), rs.getString("pass"), rs.getString("user_type"));
            }
            pr.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        ArrayList<Course> courses = Course.getListByUserId(id);
        for (Course c : courses){
            Course.delete(c.getId());
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id, String fullname, String username, String pass, String type) {
        String query = "UPDATE users SET fullname = ?, username = ?, pass = ?, user_type = ? WHERE id = ?";

        User findUser = User.getFetchByUsername(username);
        if (findUser != null && findUser.getId() != id) {
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, fullname);
            pr.setString(2, username);
            pr.setString(3, pass);
            pr.setString(4, type);
            pr.setInt(5, id);

            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> users = new ArrayList<>();
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

    public static String searchQuery(String fullname, String username, String type) {
        String query = "SELECT * FROM users WHERE username ILIKE '%{{username}}%' AND fullname ILIKE  '%{{fullname}}%'";
        query = query.replace("{{username}}", username);
        query = query.replace("{{fullname}}", fullname);
        if (!type.isEmpty()){
            query += " AND user_type LIKE '{{type}}'";
            query = query.replace("{{type}}", type);
        }
        return query;
    }

    public static ArrayList<User> getListOnlyEducator() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE user_type = 'educator'";
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
