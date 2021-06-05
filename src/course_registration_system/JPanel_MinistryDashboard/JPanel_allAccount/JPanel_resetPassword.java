package course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount;

import dao.UserDao;
import pojo.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JPanel_resetPassword extends JFrame{
    private JPanel jPanel_root;
    private JPasswordField confirmPasswordField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton saveButton;
    private Users users;

    public JPanel_resetPassword(Users u) {
        users = u;
        add(jPanel_root);
        setTitle("Add Ministry Account");
        setSize(490, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswordCheckBox.isSelected()){
                    passwordField.setEchoChar((char)0);
                    confirmPasswordField.setEchoChar((char)0);
                }else {
                    passwordField.setEchoChar('•');
                    confirmPasswordField.setEchoChar('•');
                }
            }
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(passwordField.getText().equals("") || confirmPasswordField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Some field is empty, please try again!");
                }
                else{
                    if(passwordField.getText().equals(confirmPasswordField.getText())){
                        resetPassword();
                        JOptionPane.showMessageDialog(null, "Success!");
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "The confirm password confirmation does not match!");
                    }
                }
            }
        });
    }

    public void resetPassword(){
        users.setPassword(passwordField.getText());
        UserDao.update(users);
    }
}
