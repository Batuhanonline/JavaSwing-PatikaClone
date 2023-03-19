package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        table_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = table_user_list.getValueAt(table_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(select_user_id);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
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
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_id)){
                    Helper.showMsg("fill");
                } else {
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)) {
                        Helper.showMsg("succes");
                        loadUserModel();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj: User.getList()){
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getFullname();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            model_user_list.addRow(row_user_list);
        }
    }
    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator(1, "Batuhan Güven", "batuhan", "1234", "operator");
        OperatorGUI operatorGUI = new OperatorGUI(op);
    }
}
