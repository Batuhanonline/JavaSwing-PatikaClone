package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable table_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_fullname;
    private JTextField fld_username;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_fullname;
    private JTextField fld_sh_user_username;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable table_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_courses;
    private JScrollPane scrl_course_list;
    private JTable table_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_programing_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private DefaultTableModel model_user_list;
    private Object[] row_user_list;
    private Object[] row_patika_list;
    private DefaultTableModel model_patika_list;
    private DefaultTableModel model_course_list;
    private Object[] row_course_list;
    private JPopupMenu patikaMenu;

    private final Operator operator;
    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.getScreenCenterPoint("x", getSize()), Helper.getScreenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        lbl_welcome.setText("Hoşgeldin, " + operator.getFullname());

        //----------------------UserList
        model_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        model_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        table_user_list.setModel(model_user_list);
        table_user_list.getTableHeader().setReorderingAllowed(false);

        table_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(table_user_list.getValueAt(table_user_list.getSelectedRow(), 0).toString());
                String user_fullname = table_user_list.getValueAt(table_user_list.getSelectedRow(), 1).toString();
                String user_username = table_user_list.getValueAt(table_user_list.getSelectedRow(), 2).toString();
                String user_pass = table_user_list.getValueAt(table_user_list.getSelectedRow(), 3).toString();
                String user_type = table_user_list.getValueAt(table_user_list.getSelectedRow(), 4).toString();

                if (User.update(user_id, user_fullname, user_username, user_pass, user_type)) {
                    Helper.showMsg("succes");
                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });


        // Tabloda seçili satırın id numarasını kullanıcı girişi olmayan field'a aktarılıyor. Bu sayede sayı dışında bir girdi girmenin önünü kapatmış oluyoruz.
        table_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = table_user_list.getValueAt(table_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(select_user_id);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        //####UserList

        ////----------------------PatikaList

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(table_patika_list.getValueAt(table_patika_list.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetchById(select_id));
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(table_patika_list.getValueAt(table_patika_list.getSelectedRow(), 0).toString());
                if (Patika.delete(select_id)){
                    Helper.showMsg("succes");
                    loadPatikaModel();
                    loadCourseModel();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        model_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Adı"};
        model_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();

        table_patika_list.setModel(model_patika_list);
        table_patika_list.setComponentPopupMenu(patikaMenu);
        table_patika_list.getTableHeader().setReorderingAllowed(false);
        table_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);

        table_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = table_patika_list.rowAtPoint(point);
                table_patika_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        //####PatikaList

        ////----------------------CourseList

        model_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        model_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];
        loadCourseModel();
        loadEducatorCombo();

        table_course_list.setModel(model_course_list);
        table_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_course_list.getTableHeader().setReorderingAllowed(false);

        loadPatikaCombo();
        //####CourseList
        btn_user_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_fullname) || Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_user_pass)) {
                    Helper.showMsg("fill");
                } else {

                    String fullname = fld_user_fullname.getText();
                    String username = fld_username.getText();
                    String pass = fld_user_pass.getText();
                    String type = cmb_user_type.getSelectedItem().toString();

                    if (User.add(fullname, username, pass, type)) {
                        loadUserModel();
                        loadEducatorCombo();
                        Helper.showMsg("succes");
                    } else {
                        Helper.showMsg("error");
                        fld_username.setText(null);
                        fld_user_fullname.setText(null);
                        fld_user_pass.setText(null);
                    }
                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)){
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)) {
                        Helper.showMsg("succes");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseModel();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String fullname = fld_sh_user_fullname.getText();
            String username = fld_sh_user_username.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();
            String query = User.searchQuery(fullname, username, type);
            ArrayList<User> searchingUser = User.searchUserList(query);
            loadUserModel(searchingUser);
        });
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMsg("fill");
            } else {
                if (Patika.add(fld_patika_name.getText())){
                    Helper.showMsg("succes");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }

        });

        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_programing_lang)) {
                Helper.showMsg("fill");
            } else {
                if (Course.add(userItem.getKey(), patikaItem.getKey(), fld_course_name.getText(), fld_course_programing_lang.getText())){
                    Helper.showMsg("succes");
                    loadCourseModel();
                    fld_course_name.setText(null);
                    fld_course_programing_lang.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_course_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Course obj : Course.getList()) {
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getPrograming_lang();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getFullname();

            model_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_patika_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Patika obj : Patika.getList()) {
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            model_patika_list.addRow(row_patika_list);
        }
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_user_list.getModel();
        clearModel.setRowCount(0);
        int i;

        for (User obj: User.getList()){
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getFullname();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            model_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> userList) {
        DefaultTableModel clearModel = (DefaultTableModel) table_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj: userList){
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getFullname();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            model_user_list.addRow(row_user_list);
        }
    }

    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for (Patika obj : Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo() {
        cmb_course_user.removeAllItems();
        for( User obj : User.getListOnlyEducator()){
            cmb_course_user.addItem(new Item(obj.getId(), obj.getFullname()));
        }
    }

}
