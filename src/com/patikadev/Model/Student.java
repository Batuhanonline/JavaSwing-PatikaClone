package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User{
    public Student(int id, String fullname, String username, String pass, String type) {
        super(id, fullname, username, pass, type);
    }

    public Student() {
    }

    public static ArrayList<Integer> isPatikaRegistered(int studentId) {

        ArrayList<Integer> patika = new ArrayList<>();
        String query = "SELECT patika_id FROM patikaRegister WHERE student_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, studentId);

            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                patika.add(rs.getInt("patika_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patika;
    }

    public static boolean patikaRegister(int studentId, int patikaId) {
        String query = "INSERT INTO patikaRegister (student_id, patika_id) VALUES (?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, studentId);
            pr.setInt(2, patikaId);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
