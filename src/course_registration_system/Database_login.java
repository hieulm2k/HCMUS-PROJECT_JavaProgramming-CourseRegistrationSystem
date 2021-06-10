package course_registration_system;

import dao.UserDao;
import pojo.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Database_login extends JFrame {
    private JPanel rootPane;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JCheckBox showPasswordCheckBox;
    private JButton loginButton;

    public Database_login()
    {
        add(rootPane);
        setTitle("Login To Database");
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

                    }
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Login fail!");
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
