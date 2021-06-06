package course_registration_system.JPanel_MinistryDashboard.JPanel_allClass;

import dao.ClassDao;
import pojo.Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Add_class extends JFrame {
    private JTextField textField_name;
    private JButton saveButton;
    private JPanel jPanel_root;

    private DefaultTableModel model;

    public Add_class(DefaultTableModel m) {
        model = m;
        add(jPanel_root);
        setTitle("Add Class");
        setSize(490, 150);
        setResizable(false);
        setLocationRelativeTo(null);

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!textField_name.getText().equals("")){
                    if(!checkExistsClass()){
                        saveClass();
                        JOptionPane.showMessageDialog(null, "Add success!");
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "This class is already exists, please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });
    }

    public void saveClass(){
        Classes classes = new Classes();
        classes.setName(textField_name.getText());

        Object[] o = new Object[4];
        o[0] = classes.getName();
        o[1] = 0;
        o[2] = 0;
        o[3] = 0;
        model.addRow(o);
        ClassDao.save(classes);
    }

    public boolean checkExistsClass(){
        Classes classes = ClassDao.getByName(textField_name.getText());
        if(classes == null) return false;
        return true;
    }
}
