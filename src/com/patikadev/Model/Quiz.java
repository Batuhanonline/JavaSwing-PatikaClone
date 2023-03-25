package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int contentId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    public Quiz(int id, int contentId, String question, String option1, String option2, String option3, String option4, String answer) {
        this.id = id;
        this.contentId = contentId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static boolean add(int contentId, String question, String option1, String option2, String option3, String option4, String answer) {
        String query = "INSERT INTO quiz (content_id, question, option1, option2, option3, option4, answer) " +
                "VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, contentId);
            pr.setString(2, question);
            pr.setString(3, option1);
            pr.setString(4, option2);
            pr.setString(5, option3);
            pr.setString(6, option4);
            pr.setString(7, String.valueOf(answer));

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean delete(int id) {
        String query = "DELETE FROM quiz WHERE id = " + id;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            return st.executeUpdate(query) != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Quiz> getList() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz";
        Quiz quiz;

            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    quiz = new Quiz(rs.getInt("id") , rs.getInt("content_id"), rs.getString("question"), rs.getString("option1"),
                            rs.getString("option2"), rs.getString("option3"), rs.getString("option4"),
                            rs.getString("answer"));

                    quizzes.add(quiz);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return quizzes;
    }

    public static ArrayList<Quiz> getListByUserId(int educatorId) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz WHERE content_id = ";
        Quiz quiz;

        for (Content content : Content.getListByUserId(educatorId)){
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery(query + content.getId());
                while (rs.next()) {
                    quiz = new Quiz(rs.getInt("id") , content.getId(), rs.getString("question"), rs.getString("option1"),
                            rs.getString("option2"), rs.getString("option3"), rs.getString("option4"),
                            rs.getString("answer"));

                    quizzes.add(quiz);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return quizzes;
    }
}
