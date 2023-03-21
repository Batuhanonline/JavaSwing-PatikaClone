package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbot;
    private JTextField fld_user_username;
    private JPasswordField fld_user_pass;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(500,600);
        setLocation(Helper.getScreenCenterPoint("x", getSize()), Helper.getScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            } else {
                User user = User.getFetchLogin(fld_user_username.getText(), fld_user_pass.getText());
                if (user == null){
                    Helper.showMsg("Kullanıcı bulunamadı!");
                } else {
                    switch (user.getType()){
                        case "operator" -> {
                            OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                        }
                        case "educator" -> {
                            EducatorGUI educatorGUI = new EducatorGUI((Educator) user);
                        }
                        case "student" -> {
                            StudentGUI studentGUI = new StudentGUI();
                        }
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
    }
}
