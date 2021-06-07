package course_registration_system.JPanel_MinistryDashboard.JPanel_allStudent;

import com.toedter.calendar.JDateChooser;
import dao.ClassDao;
import dao.UserDao;
import pojo.Classes;
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
import java.util.List;

public class Add_student extends JFrame {
    private JComboBox comboBox_gender;
    private JTextField textField_name;
    private JTextField textField_username;
    private JPasswordField confirmPasswordField;
    private JPasswordField passwordField;
    private JPanel jPanel_dob;
    private JCheckBox showPasswordCheckBox;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JComboBox comboBox_class;
    private JTextField textField_code;
    JDateChooser dateChooser = new JDateChooser();
    private DefaultTableModel model;

    public Add_student(DefaultTableModel m) {
        model = m;
        add(jPanel_root);
        setTitle("Add Student Account");
        setSize(490, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        loadComboboxGender();
        loadComboboxClass();
        loadDateTimePicker();
        setDate(new Date());

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!checkExistsUsername()){
                        if(!checkExistsStdCode()) {
                            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                                saveAccount();
                                JOptionPane.showMessageDialog(null, "Add success!");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "The confirm password confirmation does not match!");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "This student code is already exists, please try again!");
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
        users.setStdCode(textField_code.getText());
        users.setPassword(passwordField.getText());
        users.setPermission((byte) 0);
        users.setGender((byte) (comboBox_gender.getSelectedItem().equals("Male")?1:0));

        java.sql.Date date = java.sql.Date.valueOf(getDate());
        users.setDob(date);

        Classes classes = ClassDao.getByName(comboBox_class.getSelectedItem().toString());
        users.setClasses(classes);

        Object[] o = new Object[6];
        o[0] = users.getUsername();
        o[1] = users.getStdCode();
        o[2] = users.getName();
        o[3] = users.getClasses().getName();
        o[4] = (users.getGender() == 1 ? "Male" : "Female");
        o[5] = users.getDob().toString();
        model.addRow(o);
        UserDao.save(users);
    }

    public boolean checkExistsUsername(){
        Users users = UserDao.getByUsername(textField_username.getText());
        if(users == null) return false;
        return true;
    }

    public boolean checkExistsStdCode(){
        Users users = UserDao.getByStdCode(textField_code.getText());
        if(users == null) return false;
        return true;
    }

    public boolean checkEmptyField(){
        if(!textField_username.getText().equals("") && !textField_name.getText().equals("") && !passwordField.getText().equals("")
                && !textField_code.getText().equals("") && !confirmPasswordField.equals("") && dateChooser.getDate()!=null) return false;

        return true;
    }

    public void loadComboboxGender(){
        comboBox_gender.addItem("Male");
        comboBox_gender.addItem("Female");
    }

    public void loadComboboxClass(){
        List<Classes> classesList = ClassDao.getAll();
        classesList.remove(classesList.get(0));
        for (Classes classes:classesList) {
            comboBox_class.addItem(classes.getName());
        }
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
