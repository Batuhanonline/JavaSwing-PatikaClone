package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Patika;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private DefaultTableModel model_course_list;
    private Object[] row_course_list;
    private DefaultTableModel model_content_list;
    private Object[] row_content_list;

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

        model_content_list = new DefaultTableModel();
        Object[] col_content_list = {"ID", "Başlık", "Açıklama", "Link", "Eğitim"};
        model_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];

        loadContentModel(educator.getId());
        loadCourseCombo(educator.getId());

        table_content_list.setModel(model_content_list);
        table_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_content_list.getTableHeader().setReorderingAllowed(false);

        //####ContentList

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
