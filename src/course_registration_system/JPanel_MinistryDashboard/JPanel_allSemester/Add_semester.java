package course_registration_system.JPanel_MinistryDashboard.JPanel_allSemester;

import com.toedter.calendar.JDateChooser;
import dao.SemesterDao;
import dao.UserDao;
import pojo.Semesters;
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

public class Add_semester extends JFrame {
    private JComboBox comboBox_current;
    private JPanel jPanel_endDate;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JComboBox comboBox_type;
    private JPanel jPanel_startDate;
    private JTextField textField_schoolYear;
    private DefaultTableModel model;

    JDateChooser dateChooserStart = new JDateChooser();
    JDateChooser dateChooserEnd = new JDateChooser();

    public Add_semester(DefaultTableModel m) {
        model = m;
        add(jPanel_root);
        setTitle("Add Semester");
        setSize(490, 350);
        setResizable(false);
        setLocationRelativeTo(null);

        loadCombobox();
        loadDateTimePicker();
        setDate(dateChooserStart, new Date());
        setDate(dateChooserEnd, new Date());

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!checkExistsSemester()){
                        if(comboBox_current.getSelectedItem().equals("YES")) {
                            setCurrent();
                        }
                        saveSemester();
                        JOptionPane.showMessageDialog(null, "Add success!");
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "This semester is already exists, please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });

    }

    public void setCurrent(){
            Semesters last = SemesterDao.getCurrent();
            if(last!=null) {
                last.setIsCurrent((byte) 0);
                SemesterDao.update(last);
            }
            for(int i = 0; i < model.getRowCount(); i++){
                if(model.getValueAt(i,5).equals("YES")){
                    model.setValueAt("NO",i,5);
                }
            }
        }

    public void saveSemester(){
        Semesters semesters = new Semesters();
        semesters.setType(comboBox_type.getSelectedItem().equals("HK1")?1:
                comboBox_type.getSelectedItem().equals("HK2")?2:3);
        semesters.setSchoolYear(textField_schoolYear.getText());
        semesters.setIsCurrent((byte) (comboBox_current.getSelectedItem().equals("YES")?1:0));

        java.sql.Date startDate = java.sql.Date.valueOf(getDate(dateChooserStart));
        java.sql.Date endDate = java.sql.Date.valueOf(getDate(dateChooserEnd));

        semesters.setStartDate(startDate);
        semesters.setEndDate(endDate);

        SemesterDao.save(semesters);

        Object[] o = new Object[6];
        o[0] = semesters.getId();
        o[1] = semesters.getType()==1?"HK1":semesters.getType()==2?"HK2":"HK3";
        o[2] = semesters.getSchoolYear();
        o[3] = semesters.getStartDate();
        o[4] = semesters.getEndDate();
        o[5] = semesters.getIsCurrent()==1?"YES":"NO";
        model.addRow(o);
    }

    public boolean checkExistsSemester(){
        Semesters semesters = SemesterDao.getByTypeAndYear(comboBox_type.getSelectedItem().equals("HK1")?1:
                comboBox_type.getSelectedItem().equals("HK2")?2:3, textField_schoolYear.getText());
        if(semesters == null) return false;
        return true;
    }

    public boolean checkEmptyField(){
        if(!textField_schoolYear.getText().equals("") && dateChooserStart.getDate()!=null && dateChooserStart.getDate()!=null) return false;

        return true;
    }

    public void loadCombobox(){
        comboBox_current.addItem("YES");
        comboBox_current.addItem("NO");

        comboBox_type.addItem("HK1");
        comboBox_type.addItem("HK2");
        comboBox_type.addItem("HK3");
    }

    public void loadDateTimePicker(){
        dateChooserStart.setDateFormatString("yyyy-MM-dd");
        dateChooserEnd.setDateFormatString("yyyy-MM-dd");

        dateChooserStart.setFont(new Font("Serif", Font.PLAIN, 18));
        dateChooserEnd.setFont(new Font("Serif", Font.PLAIN, 18));

        jPanel_startDate.add(dateChooserStart);
        jPanel_endDate.add(dateChooserEnd);
    }

    public String getDate(JDateChooser dateChooser){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(dateChooser.getDate());
    }

    public void setDate(JDateChooser dateChooser, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateChooser.setDate(date);
    }
}
