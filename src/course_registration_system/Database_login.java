package course_registration_system;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.HibernateUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Database_login extends JFrame {
    private JPanel rootPane;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JCheckBox showPasswordCheckBox;
    private JButton loginButton;
    private JTextField txtPort;
    private JTextField txtSchema;
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
                    String port = txtPort.getText();
                    String schema = txtSchema.getText();
                    if(username.equals("") || password.equals("") || port.equals("") || schema.equals("")){
                        JOptionPane.showMessageDialog(null, "Your input field cannot empty!");
                    }
                    else {
                        if(tryParseInt()!=-1){
                            String url = "jdbc:mysql://localhost:"+ port +"/" + schema;
                            Configuration cf = new Configuration();
                            cf.configure();
                            cf.setProperty("hibernate.connection.url", url);
                            cf.setProperty("hibernate.connection.username", username);
                            cf.setProperty("hibernate.connection.password", password);
                            SessionFactory sessionFactory = cf.buildSessionFactory();
                            HibernateUtil.setSessionFactory(sessionFactory);
                            dispose();
                            Login login = new Login();
                            login.setVisible(true);
                        }
                    }
                }
                catch(Exception exp){
                    System.out.println(exp);
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

    public int tryParseInt(){
        try{
            return Integer.parseInt(txtPort.getText());
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Port field has wrong format of integer, please try again!");
            return -1;
        }
    }
}
