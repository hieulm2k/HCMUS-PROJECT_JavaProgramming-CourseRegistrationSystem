package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.Add_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.Edit_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.ResetPassword_account;
import dao.RegisterDao;
import dao.UserDao;
import pojo.Courses;
import pojo.Registers;
import pojo.Subjects;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllAccount {
    private JPanel jPanel_allAccount;
    private JTable table_allAccount;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton resetPasswordButton;
    private JButton searchButton;
    private JTextField textField_ministry;
    private List<Users> usersList;
    private DefaultTableModel model;
    private Users user;

    public JPanel getjPanel_allAccount() {
        return jPanel_allAccount;
    }

    public AllAccount(Users u) {
        user = u;
        usersList = UserDao.getAllMinistry();
        usersList.remove(u);
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allAccount.getRowCount() > 0){
                    if(table_allAccount.getSelectedRow() != -1){
                        String username = table_allAccount.getValueAt(table_allAccount.getSelectedRow(),0).toString();
                        Users users = UserDao.getByUsername(username);
                        UserDao.delete(users);
                        model.removeRow(table_allAccount.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "Delete success!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a row to delete!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot delete!");
                }

            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Add_account jPanel_add = new Add_account(model);
                jPanel_add.setVisible(true);
            }
        });

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allAccount.getRowCount() > 0){
                    if(table_allAccount.getSelectedRow() != -1){
                        String username = table_allAccount.getValueAt(table_allAccount.getSelectedRow(),0).toString();
                        Users users = UserDao.getByUsername(username);
                        Edit_account edit = new Edit_account(users, model, table_allAccount.getSelectedRow());
                        edit.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a row to edit!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot edit!");
                }
            }
        });

        resetPasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allAccount.getRowCount() > 0){
                    if(table_allAccount.getSelectedRow() != -1){
                        String username = table_allAccount.getValueAt(table_allAccount.getSelectedRow(),0).toString();
                        Users users = UserDao.getByUsername(username);
                        ResetPassword_account reset = new ResetPassword_account(users);
                        reset.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a row to reset password!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot reset password!");
                }
            }
        });
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                trySearch();
            }
        });
    }

    private void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Username", "Name", "Gender", "Date of birth"}
        );
        for (Users users : usersList) {
            Object[] o = new Object[4];
            o[0] = users.getUsername();
            o[1] = users.getName();
            o[2] = (users.getGender() == 1 ? "Male" : "Female");
            o[3] = users.getDob().toString();
            model.addRow(o);
        }
        setTable();
    }

    private void setTable(){
        table_allAccount.getTableHeader().setOpaque(false);
        table_allAccount.getTableHeader().setBackground(Color.DARK_GRAY);
        table_allAccount.getTableHeader().setForeground(Color.WHITE);
        table_allAccount.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_allAccount.setModel(model);
        table_allAccount.setFont(new Font("Serif", Font.PLAIN, 18));
        table_allAccount.setRowHeight(30);
    }

    private void trySearch(){
        String username = textField_ministry.getText();
        if(username.equals("")){
            createTable();
            return;
        }

        Users users = UserDao.getByUsername(username);

        if(users == null){
            JOptionPane.showMessageDialog(null, "This username is not exists, please try again!");
        }
        else {
            model = new DefaultTableModel(
                    null,
                    new String[]{"Username", "Name", "Gender", "Date of birth"}
            );

            Object[] o = new Object[4];
            o[0] = users.getUsername();
            o[1] = users.getName();
            o[2] = (users.getGender() == 1 ? "Male" : "Female");
            o[3] = users.getDob().toString();
            model.addRow(o);
            setTable();
        }
    }
}
