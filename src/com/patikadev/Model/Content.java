package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private int course_id;
    private String title;
    private String explanation;
    private String link;

    private Course course;

    public Content(int id, int course_id, String title, String explanation, String link) {
        this.id = id;
        this.course_id = course_id;
        this.title = title;
        this.explanation = explanation;
        this.link = link;
        this.course = Course.getFetchById(course_id);
    }

    public static ArrayList<Content> getListByUserId(int educatorId) {
        ArrayList<Content> contents = new ArrayList<>();
        String query = "SELECT id, title, explanation, link FROM content WHERE course_id = ";
        Content content;

        for (Course obj : Course.getListByUserId(educatorId)){

            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery(query + obj.getId());
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String explanation = rs.getString("explanation");
                    String link = rs.getString("link");

                    content = new Content(id, obj.getId(), title, explanation,link);
                    contents.add(content);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return contents;
    }

    public static boolean add(int courseId, String title, String explanation, String link) {
        String query = "INSERT INTO content (course_id, title, explanation, link) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, courseId);
            pr.setString(2, title);
            pr.setString(3, explanation);
            pr.setString(4, link);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int selectId) {
        String query = "DELETE FROM content WHERE id = ?";

        ArrayList<Quiz> quizzes = Quiz.getList();
        for (Quiz quiz : quizzes) {
            if (quiz.getContentId() == selectId) {
                Quiz.delete(quiz.getId());
            }
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, selectId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
