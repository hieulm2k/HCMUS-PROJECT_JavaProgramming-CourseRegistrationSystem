package course_registration_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.UserDao;
import pojo.Users;

public class Login extends JFrame
{
    private JPanel rootPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private JCheckBox showPasswordCheckBox;

    public Login()
    {
        add(rootPane);
        setTitle("Login");
        setSize(400,400);
        setResizable(false);
        setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = txtUsername.getText();
                    String password = new String(txtPassword.getPassword());

                    if(username.equals("") || password.equals("")){
                        JOptionPane.showMessageDialog(null, "Your input field cannot empty!");
                    }
                    else {
                        Users users = UserDao.getByUsername(username);
                        String correctPassword = users.getPassword();
                        if(correctPassword.equals(password)){
                            dispose();
                            if(users.getPermission() == 1) {
                                MinistryDashboard ministryDashboard = new MinistryDashboard(users);
                                ministryDashboard.setVisible(true);
                            }
                            else{
                                StudentDashboard studentDashboard = new StudentDashboard(users);
                                studentDashboard.setVisible(true);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Wrong password!");
                        }
                    }
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Login fail! This username does not exist!");
                }
            }
        });

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswordCheckBox.isSelected()){
                    txtPassword.setEchoChar((char)0);
                }else {
                    txtPassword.setEchoChar('•');
                }
            }
        });
    }
}
