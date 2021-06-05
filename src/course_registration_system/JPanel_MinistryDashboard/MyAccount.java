package course_registration_system.JPanel_MinistryDashboard;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
import dao.UserDao;
import pojo.Users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAccount {
    private JPanel jPanel_myAcount;
    private JTextField textField_name;
    private JButton button_edit;
    private JTextField textField_username;
    private JButton button_changePassword;
    private JLabel jLabel_newPassword;
    private JLabel jLabel_confirmPassword;
    private JPasswordField textField_newPassword;
    private JPanel jPanel_dob;
    private JComboBox comboBox_gender;
    private JPasswordField textField_confirmPassword;
    private JLabel jLabel_name;
    private JLabel jLabel_gender;
    private JLabel jLabel_dob;
    private JLabel jLabel_username;
    private JCheckBox showPasswordCheckBox;
    JDateChooser dateChooser = new JDateChooser();

    public MyAccount(Users users) {
        loadDateTimePicker();
        loadComboboxGender();
        setDate(users.getDob());

        textField_name.setText(users.getName());
        comboBox_gender.setSelectedItem((users.getGender() == 1)?"Male":"Female");
        textField_username.setText(users.getUsername());

        button_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(button_edit.getText().equals("Edit")){
                    button_edit.setText("Save");
                    setEnabled(true);
                }
                else {
                    updateUser(users);
                }
            }
        });

        button_changePassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(button_changePassword.getText().equals("Change password")) {
                    button_changePassword.setText("Save");
                    setVisible(true);
                }
                else{
                    savePassword(users);
                }
            }
        });

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswordCheckBox.isSelected()){
                    textField_newPassword.setEchoChar((char)0);
                    textField_confirmPassword.setEchoChar((char)0);
                }else {
                    textField_newPassword.setEchoChar('•');
                    textField_confirmPassword.setEchoChar('•');
                }
            }
        });
    }

    public JPanel getjPanel_myAcount() {
        return jPanel_myAcount;
    }

    public void updateUser(Users users){
        if(!textField_name.getText().equals("") && dateChooser.getDate()!=null && !textField_username.getText().equals("")) {
            java.sql.Date date = java.sql.Date.valueOf(getDate());
            users.setName(textField_name.getText());
            users.setGender((byte) (comboBox_gender.getSelectedItem().toString().equals("Male") ? 1 : 0));
            users.setDob(date);

            // check if username is exists
            if(!users.getUsername().equals(textField_username.getText())){
                Users usersCheck = UserDao.getByUsername(textField_username.getText());
                if(usersCheck != null){
                    JOptionPane.showMessageDialog(null, "This username is already exists, please try again!");
                    return;
                }
                users.setUsername(textField_username.getText());
            }

            UserDao.update(users);
            JOptionPane.showMessageDialog(null, "Success!");
            button_edit.setText("Edit");
            setEnabled(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
        }
    }

    public void setEnabled(boolean isEnabled){
        textField_name.setEnabled(isEnabled);
        textField_username.setEnabled(isEnabled);
        comboBox_gender.setEnabled(isEnabled);
        dateChooser.setEnabled(isEnabled);
        button_changePassword.setVisible(!isEnabled);
        showPasswordCheckBox.setVisible(!isEnabled);
    }

    public void setVisible(boolean isVisible){
        jLabel_newPassword.setVisible(isVisible);
        jLabel_confirmPassword.setVisible(isVisible);
        jLabel_name.setVisible(!isVisible);
        jLabel_gender.setVisible(!isVisible);
        jLabel_dob.setVisible(!isVisible);
        jLabel_username.setVisible(!isVisible);

        textField_newPassword.setVisible(isVisible);
        textField_confirmPassword.setVisible(isVisible);
        showPasswordCheckBox.setVisible(isVisible);
        textField_name.setVisible(!isVisible);
        textField_username.setVisible(!isVisible);
        comboBox_gender.setVisible(!isVisible);
        jPanel_dob.setVisible(!isVisible);

        button_edit.setVisible(!isVisible);
    }

    public void savePassword(Users users){
        if(!textField_newPassword.getText().equals("") && !textField_confirmPassword.getText().equals("")) {
            // check if confirm password is correct
            if(!textField_confirmPassword.getText().equals(textField_newPassword.getText())){
                JOptionPane.showMessageDialog(null, "The confirm password confirmation does not match!");
                return;
            }
            users.setPassword(textField_newPassword.getText());

            UserDao.update(users);
            JOptionPane.showMessageDialog(null, "Success!");

            button_changePassword.setText("Change password");
            setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "Some field is empty, please try again!");
        }
    }

    public void loadComboboxGender(){
        comboBox_gender.addItem("Male");
        comboBox_gender.addItem("Female");
    }

    public void loadDateTimePicker(){
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setEnabled(false);
        dateChooser.setFont(new Font("Serif", Font.PLAIN, 18));
        jPanel_dob.add(dateChooser);
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(dateChooser.getDate());
    }

    public void setDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date date = simpleDateFormat.parse(dt);
        dateChooser.setDate(date);
    }
}
