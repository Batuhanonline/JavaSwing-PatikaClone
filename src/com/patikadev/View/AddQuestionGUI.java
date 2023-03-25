package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Quiz;

import javax.swing.*;

public class AddQuestionGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_options_1;
    private JTextField fld_options_2;
    private JTextField fld_options_3;
    private JTextField fld_options_4;
    private JComboBox cmb_answer;
    private JButton btn_add_question;
    private JTextField fld_quiz_question;
    private int contentId;

    public AddQuestionGUI(int contentId) {
        this.contentId = contentId;
        add(wrapper);
        setSize(400, 500);
        setLocation(Helper.getScreenCenterPoint("x", getSize()), Helper.getScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);



        btn_add_question.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_question) || Helper.isFieldEmpty(fld_options_1) || Helper.isFieldEmpty(fld_options_2)
            || Helper.isFieldEmpty(fld_options_3) || Helper.isFieldEmpty(fld_options_4)) {
                Helper.showMsg("fill");
            } else {
                if (Quiz.add(contentId, fld_quiz_question.getText(), fld_options_1.getText(), fld_options_2.getText(),
                        fld_options_3.getText(), fld_options_4.getText(), cmb_answer.getSelectedItem().toString())) {
                    Helper.showMsg("succes");

                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
