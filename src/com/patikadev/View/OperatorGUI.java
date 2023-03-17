package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable table_user_list;
    private DefaultTableModel model_user_list;
    private Object[] row_user_list;
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

        //ModelUserList
        model_user_list = new DefaultTableModel();
        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        model_user_list.setColumnIdentifiers(col_user_list);

        for (User obj: User.getList()){
            Object[] row = new Object[col_user_list.length];
            row[0] = obj.getId();
            row[1] = obj.getFullname();
            row[2] = obj.getUsername();
            row[3] = obj.getPass();
            row[4] = obj.getType();
            model_user_list.addRow(row);
        }

        table_user_list.setModel(model_user_list);
        table_user_list.getTableHeader().setReorderingAllowed(false);

    }


    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator(1, "Batuhan Güven", "batuhan", "1234", "operator");
        OperatorGUI operatorGUI = new OperatorGUI(op);
    }
}
