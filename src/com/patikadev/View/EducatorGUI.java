package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JTabbedPane tab_education;
    private JTable table_education_list;
    private JTable table_content_list;
    private JButton btn_close;
    private JTextField fld_content_title;
    private JTextField fld_content_explanation;
    private JComboBox cmb_content_course;
    private JButton btn_course_add;
    private JTextField fld_content_link;
    private JTable table_quiz_list;
    private DefaultTableModel model_course_list;
    private Object[] row_course_list;
    private DefaultTableModel model_content_list;
    private Object[] row_content_list;
    private DefaultTableModel model_quiz_list;
    private Object[] row_quiz_list;
    private JPopupMenu contentMenu;
    private JPopupMenu quizMenu;

    public EducatorGUI(Educator educator) {
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.getScreenCenterPoint("x", getSize()), Helper.getScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldiniz, " + educator.getFullname());





        //-----------------CourseList

        model_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID", "Ders Adı", "Programlama Dili", "Patika"};
        model_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];

        loadCourseModel(educator.getId());

        table_education_list.setModel(model_course_list);
        table_education_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_education_list.getTableHeader().setReorderingAllowed(false);


        //####CourseList

        //-----------------ContentList

        contentMenu = new JPopupMenu();
        JMenuItem deleteMenu = new JMenuItem("Sil");
        JMenuItem addQuestionMenu = new JMenuItem("Soru ekle");
        contentMenu.add(deleteMenu);
        contentMenu.add(addQuestionMenu);

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(table_content_list.getValueAt(table_content_list.getSelectedRow(), 0).toString());
                if (Content.delete(select_id)) {
                    Helper.showMsg("succes");
                    loadContentModel(educator.getId());
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        addQuestionMenu.addActionListener( e -> {
            int select_id = Integer.parseInt(table_content_list.getValueAt(table_content_list.getSelectedRow(), 0).toString());
            AddQuestionGUI addQuestionGUI  = new AddQuestionGUI(select_id);
            addQuestionGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadQuizModel(educator.getId());
                }
            });
        });

        model_content_list = new DefaultTableModel();
        Object[] col_content_list = {"ID", "Başlık", "Açıklama", "Link", "Eğitim"};
        model_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];

        loadContentModel(educator.getId());
        loadCourseCombo(educator.getId());

        table_content_list.setModel(model_content_list);
        table_content_list.setComponentPopupMenu(contentMenu);
        table_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_content_list.getTableHeader().setReorderingAllowed(false);

        table_content_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = table_content_list.rowAtPoint(point);
                table_content_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });

        //####ContentList

        //-----------------QuizList

        quizMenu = new JPopupMenu();
        JMenuItem quizDeleteMenu = new JMenuItem("Sil");
        quizMenu.add(quizDeleteMenu);

        quizDeleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(table_quiz_list.getValueAt(table_quiz_list.getSelectedRow(), 0).toString());
                if (Quiz.delete(select_id)) {
                    Helper.showMsg("succes");
                    loadQuizModel(educator.getId());
                } else {
                    Helper.showMsg("error");
                }
            }
        });


        model_quiz_list = new DefaultTableModel();
        Object[] col_quiz_list = {"ID", "Soru", "Cevap 1", "Cevap 2", "Cevap 3", "Cevap 4", "Cevap"};
        model_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];

        loadQuizModel(educator.getId());

        table_quiz_list.setModel(model_quiz_list);
        table_quiz_list.setComponentPopupMenu(quizMenu);
        table_quiz_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_quiz_list.getTableHeader().setReorderingAllowed(false);

        table_quiz_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = table_quiz_list.rowAtPoint(point);
                table_quiz_list.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        //####QuizList

        btn_close.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        btn_course_add.addActionListener(e -> {
            Item courseItem = (Item) cmb_content_course.getSelectedItem();
            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_explanation) || Helper.isFieldEmpty(fld_content_link)){
                Helper.showMsg("fill");
            } else {
                String title = fld_content_title.getText();
                String explanation = fld_content_explanation.getText();
                String link = fld_content_link.getText();
                int courseId = courseItem.getKey();

                if (Content.add(courseId, title, explanation, link)){
                    Helper.showMsg("succes");
                    loadContentModel(educator.getId());
                    fld_content_explanation.setText(null);
                    fld_content_link.setText(null);
                    fld_content_title.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    public void loadQuizModel(int educatorId) {
        DefaultTableModel clearModel = (DefaultTableModel) table_quiz_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Quiz quiz : Quiz.getListByUserId(educatorId)) {
            i = 0;
            row_quiz_list[i++] = quiz.getId();
            row_quiz_list[i++] = quiz.getQuestion();
            row_quiz_list[i++] = quiz.getOption1();
            row_quiz_list[i++] = quiz.getOption2();
            row_quiz_list[i++] = quiz.getOption3();
            row_quiz_list[i++] = quiz.getOption4();
            row_quiz_list[i++] = quiz.getAnswer();

            model_quiz_list.addRow(row_quiz_list);
        }
    }

    private void loadContentModel(int educatorId) {
        DefaultTableModel clearModel = (DefaultTableModel) table_content_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Content obj : Content.getListByUserId(educatorId)) {
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getExplanation();
            row_content_list[i++] = obj.getLink();
            row_content_list[i++] = obj.getCourse().getName();

            model_content_list.addRow(row_content_list);
        }
    }

    public void loadCourseModel(int educator_id) {
        DefaultTableModel clearModel = (DefaultTableModel) table_education_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Course obj : Course.getListByUserId(educator_id)) {
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getPrograming_lang();
            row_course_list[i++] = obj.getPatika().getName();

            model_course_list.addRow(row_course_list);
        }
    }

    public void loadCourseCombo(int id){
        cmb_content_course.removeAllItems();
        for (Course course : Course.getListByUserId(id)){
            cmb_content_course.addItem(new Item(course.getId(), course.getName()));
        }
    }
}
