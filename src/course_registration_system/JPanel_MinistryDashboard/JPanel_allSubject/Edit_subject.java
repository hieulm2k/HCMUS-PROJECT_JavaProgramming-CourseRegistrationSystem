package course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject;

import dao.SubjectDao;
import dao.UserDao;
import pojo.Subjects;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Edit_subject extends JFrame {
    private JComboBox comboBox_gender;
    private JTextField textField_name;
    private JTextField textField_code;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JTextField textField_credit;

    private Subjects subjects;
    private DefaultTableModel model;
    private int row;

    public Edit_subject(Subjects sub, DefaultTableModel m, int selectedRow){
        subjects = sub;
        model = m;
        row = selectedRow;

        add(jPanel_root);
        setTitle("Edit Subject");
        setSize(490, 310);
        setResizable(false);
        setLocationRelativeTo(null);

        loadData();

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!subjects.getCode().equals(textField_code.getText()) && checkExistsSubject()) {
                        JOptionPane.showMessageDialog(null, "This subject code is already exists, please try again!");
                        return;
                    }

                    if(tryParseInt() == -1){
                        return;
                    }

                    updateSubject();
                    JOptionPane.showMessageDialog(null, "Edit success!");
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });
    }

    public void loadData(){
        textField_name.setText(subjects.getName());
        textField_code.setText(subjects.getCode());
        textField_credit.setText(subjects.getCredit().toString());
    }

    public void updateSubject(){
        subjects.setCode(textField_code.getText());
        subjects.setName(textField_name.getText());
        subjects.setCredit(tryParseInt());
        model.setValueAt(subjects.getCode(),row,0);
        model.setValueAt(subjects.getName(),row,1);
        model.setValueAt(subjects.getCredit(),row,2);

        SubjectDao.update(subjects);
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
