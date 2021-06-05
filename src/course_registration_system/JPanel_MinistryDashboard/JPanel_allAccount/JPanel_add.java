package course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount;

import com.toedter.calendar.JDateChooser;
import dao.UserDao;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JPanel_add extends JFrame {
    private JButton saveButton;
    private JPanel JPanel_root;
    private JTextField textField_username;
    private JPanel jPanel_dob;
    private JComboBox comboBox_gender;
    private JTextField textField_name;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox showPasswordCheckBox;
    JDateChooser dateChooser = new JDateChooser();
    private DefaultTableModel model;

    public JPanel_add(DefaultTableModel m) {
        model = m;
        add(JPanel_root);
        setTitle("Add Ministry Account");
        setSize(490, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        loadComboboxGender();
        loadDateTimePicker();
        setDate(new Date());

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!checkExistsUsername()){
                        if(passwordField.getText().equals(confirmPasswordField.getText())){
                            saveAccount();
                            JOptionPane.showMessageDialog(null, "Add success!");
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "The confirm password confirmation does not match!");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "This username is already exists, please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });

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
    }

    public void saveAccount(){
        Users users = new Users();
        users.setUsername(textField_username.getText());
        users.setName(textField_name.getText());
        users.setPassword(passwordField.getText());
        users.setPermission((byte) 1);
        users.setGender((byte) (comboBox_gender.getSelectedItem().equals("Male")?1:0));
        users.setClassId(1);
        java.sql.Date date = java.sql.Date.valueOf(getDate());
        users.setDob(date);

        Object[] o = new Object[4];
        o[0] = users.getUsername();
        o[1] = users.getName();
        o[2] = (users.getGender() == 1 ? "Male" : "Female");
        o[3] = users.getDob().toString();
        model.addRow(o);
        UserDao.save(users);
    }

    public boolean checkExistsUsername(){
        Users users = UserDao.getByUsername(textField_username.getText());
        if(users == null) return false;
        return true;
    }

    public boolean checkEmptyField(){
        if(!textField_username.getText().equals("") && !textField_name.getText().equals("") && !passwordField.getText().equals("")
                && !confirmPasswordField.equals("") && dateChooser.getDate()!=null) return false;

        return true;
    }

    public void loadComboboxGender(){
        comboBox_gender.addItem("Male");
        comboBox_gender.addItem("Female");
    }

    public void loadDateTimePicker(){
        dateChooser.setDateFormatString("yyyy-MM-dd");
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
