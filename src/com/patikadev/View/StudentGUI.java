package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JTabbedPane tab_student;
    private JTable table_patika_list;
    private DefaultTableModel model_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;


    public StudentGUI(Student student) {
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.getScreenCenterPoint("x", getSize()), Helper.getScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hosgeldiniz " + student.getFullname());

        //----------------------------------PatikaList

        patikaMenu = new JPopupMenu();
        JMenuItem joinMenu = new JMenuItem("Patikaya katil");
        patikaMenu.add(joinMenu);

        joinMenu.addActionListener( e -> {
            int selected_id = Integer.parseInt(table_patika_list.getValueAt(table_patika_list.getSelectedRow(),0).toString());
            if (Student.patikaRegister(student.getId(), selected_id)){
                Helper.showMsg("succes");
                loadPatikaModel(student.getId());
            } else {
                Helper.showMsg("error");
            }
        });

        model_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Adi", "Kayit Durumu"};
        model_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];

        loadPatikaModel(student.getId());

        table_patika_list.setModel(model_patika_list);
        table_patika_list.setComponentPopupMenu(patikaMenu);
        table_patika_list.getTableHeader().setReorderingAllowed(false);
        table_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_patika_list.getColumnModel().getColumn(2).setMaxWidth(500);


        table_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = table_patika_list.rowAtPoint(point);
                table_patika_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });

        //#######PatikaList
    }

    private void loadPatikaModel(int studentId) {
        DefaultTableModel clearModel = (DefaultTableModel) table_patika_list.getModel();
        clearModel.setRowCount(0);

        ArrayList<Integer> patikas = Student.isPatikaRegistered(studentId);
        int i;
        for (Patika patika : Patika.getList()) {
            i = 0;
            row_patika_list[i++] = patika.getId();
            row_patika_list[i++] = patika.getName();
            if (patikas.contains(patika.getId())){
                row_patika_list[i++] = "Katildiniz";
            } else {
                row_patika_list[i++] = "Katil";
            }
            model_patika_list.addRow(row_patika_list);
        }
    }
}
