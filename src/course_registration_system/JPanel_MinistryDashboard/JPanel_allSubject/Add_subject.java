package course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject;

import dao.SubjectDao;
import pojo.Subjects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Add_subject extends JFrame {
    private JTextField textField_name;
    private JTextField textField_code;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JTextField textField_credit;
    private DefaultTableModel model;

    public Add_subject(DefaultTableModel m) {
        model = m;
        add(jPanel_root);
        setTitle("Add Subject");
        setSize(490, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!checkExistsSubject()){
                        if(tryParseInt() != -1) {
                            saveSubject();
                            JOptionPane.showMessageDialog(null, "Add success!");
                            dispose();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "This subject code is already exists, please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });
    }

    public int tryParseInt(){
        try{
            return Integer.parseInt(textField_credit.getText());
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Credit field has wrong format or integer, please try again!");
            return -1;
        }
    }

    public void saveSubject(){
        Subjects subjects = new Subjects();
        subjects.setName(textField_name.getText());
        subjects.setCode(textField_code.getText());
        subjects.setCredit(Integer.parseInt(textField_credit.getText()));

        Object[] o = new Object[3];
        o[0] = subjects.getCode();
        o[1] = subjects.getName();
        o[2] = subjects.getCredit();

        model.addRow(o);
        SubjectDao.save(subjects);
    }

    public boolean checkExistsSubject(){
        Subjects subjects = SubjectDao.getByCode(textField_code.getText());
        if(subjects == null) return false;
        return true;
    }

    public boolean checkEmptyField(){
        if(!textField_code.getText().equals("") && !textField_name.getText().equals("") && !textField_credit.getText().equals("")){
            return false;
        }
        return true;
    }

}
