package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.Add_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.Edit_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.ResetPassword_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allStudent.Add_student;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.Add_subject;
import dao.UserDao;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllStudent {
    private JTable table_allStudent;
    private JButton addButton;
    private JButton editButton;
    private JButton resetPasswordButton;
    private JButton deleteButton;
    private JPanel jPanel_student;
    private JButton vewSubjectButton;

    private List<Users> usersList;
    private DefaultTableModel model;
    private Users user;

    public JPanel getjPanel_student() {
        return jPanel_student;
    }

    public AllStudent(Users u) {
        user = u;
        usersList = UserDao.getAllStudent();
        usersList.remove(u);
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allStudent.getRowCount() > 0){
                    if(table_allStudent.getSelectedRow() != -1){
                        String username = table_allStudent.getValueAt(table_allStudent.getSelectedRow(),0).toString();
                        Users users = UserDao.getByUsername(username);
                        UserDao.delete(users);
                        model.removeRow(table_allStudent.getSelectedRow());
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
                Add_student add_student = new Add_student(model);
                add_student.setVisible(true);
            }
        });

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allStudent.getRowCount() > 0){
                    if(table_allStudent.getSelectedRow() != -1){
                        String username = table_allStudent.getValueAt(table_allStudent.getSelectedRow(),0).toString();
                        Users users = UserDao.getByUsername(username);
//                        Edit_account edit = new Edit_account(users, model, table_allStudent.getSelectedRow());
//                        edit.setVisible(true);
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
                if(table_allStudent.getRowCount() > 0){
                    if(table_allStudent.getSelectedRow() != -1){
                        String username = table_allStudent.getValueAt(table_allStudent.getSelectedRow(),0).toString();
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
    }

    private void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Username", "Student Code", "Name", "Class", "Gender", "Date of birth"}
        );
        for (Users users : usersList) {
            Object[] o = new Object[6];
            o[0] = users.getUsername();
            o[1] = users.getStdCode();
            o[2] = users.getName();
            o[3] = users.getClasses().getName();
            o[4] = (users.getGender() == 1 ? "Male" : "Female");
            o[5] = users.getDob().toString();
            model.addRow(o);
        }
        table_allStudent.getTableHeader().setOpaque(false);
        table_allStudent.getTableHeader().setBackground(Color.DARK_GRAY);
        table_allStudent.getTableHeader().setForeground(Color.WHITE);
        table_allStudent.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_allStudent.setModel(model);
        table_allStudent.setFont(new Font("Serif", Font.PLAIN, 18));
        table_allStudent.setRowHeight(30);
    }
}
