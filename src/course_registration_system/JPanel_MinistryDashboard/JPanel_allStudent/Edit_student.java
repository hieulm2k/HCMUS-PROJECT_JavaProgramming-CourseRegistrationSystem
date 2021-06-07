package course_registration_system.JPanel_MinistryDashboard.JPanel_allStudent;

import com.toedter.calendar.JDateChooser;
import dao.ClassDao;
import dao.UserDao;
import pojo.Classes;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Edit_student extends JFrame{
    private JComboBox comboBox_gender;
    private JTextField textField_name;
    private JTextField textField_username;
    private JPanel jPanel_dob;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JTextField textField_code;
    private JComboBox comboBox_class;

    JDateChooser dateChooser = new JDateChooser();
    private Users users;
    private DefaultTableModel model;
    private int row;

    public Edit_student(Users u, DefaultTableModel m, int selectedRow){
        users = u;
        model = m;
        row = selectedRow;

        add(jPanel_root);
        setTitle("Edit Student Account");
        setSize(490, 350);
        setResizable(false);
        setLocationRelativeTo(null);

        loadComboboxGender();
        loadComboboxClass();
        loadDateTimePicker();
        setDate(users.getDob());
        textField_name.setText(users.getName());
        textField_username.setText(users.getUsername());
        textField_code.setText(users.getStdCode());

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!users.getUsername().equals(textField_username.getText()) && checkExistsUsername()) {
                        JOptionPane.showMessageDialog(null, "This username is already exists, please try again!");
                        return;
                    }
                    if(!users.getStdCode().equals(textField_code.getText()) && checkExistsStdCode()) {
                        JOptionPane.showMessageDialog(null, "This student code is already exists, please try again!");
                        return;
                    }
                    updateAccount();
                    JOptionPane.showMessageDialog(null, "Edit success!");
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });
    }

    public void updateAccount(){
        users.setUsername(textField_username.getText());
        users.setName(textField_name.getText());
        users.setGender((byte) (comboBox_gender.getSelectedItem().equals("Male")?1:0));
        java.sql.Date date = java.sql.Date.valueOf(getDate());
        users.setDob(date);
        users.setStdCode(textField_code.getText());
        Classes classes = ClassDao.getByName(comboBox_class.getSelectedItem().toString());
        users.setClasses(classes);

        model.setValueAt(users.getUsername(),row,0);
        model.setValueAt(users.getStdCode(),row,1);
        model.setValueAt(users.getName(),row,2);
        model.setValueAt(users.getClasses().getName(),row,3);
        model.setValueAt(comboBox_gender.getSelectedItem(),row,4);
        model.setValueAt(getDate(),row,5);

        UserDao.update(users);
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
        if(!textField_username.getText().equals("") && !textField_name.getText().equals("") && dateChooser.getDate()!=null) return false;

        return true;
    }

    public void loadComboboxGender(){
        comboBox_gender.addItem("Male");
        comboBox_gender.addItem("Female");
        comboBox_gender.setSelectedItem(users.getGender()==1?"Male":"Female");
    }

    public void loadComboboxClass(){
        List<Classes> classesList = ClassDao.getAll();
        classesList.remove(classesList.get(0));
        for (Classes classes:classesList) {
            comboBox_class.addItem(classes.getName());
        }
        comboBox_class.setSelectedItem(users.getClasses().getName());
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
