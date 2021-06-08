package course_registration_system.JPanel_MinistryDashboard.JPanel_allSession;

import com.toedter.calendar.JDateChooser;
import dao.SemesterDao;
import dao.SessionDao;
import pojo.Semesters;
import pojo.Sessions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Add_session extends JFrame{
    private JPanel jPanel_endDate;
    private JComboBox comboBox_semester;
    private JTextField textField_schoolYear;
    private JPanel jPanel_startDate;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JLabel jLabel_schoolYear;
    private JLabel jLabel_semester;
    private DefaultTableModel model;

    private JDateChooser dateChooserStart = new JDateChooser();
    private JDateChooser dateChooserEnd = new JDateChooser();
    private Semesters semesters = SemesterDao.getCurrent();

    public Add_session(DefaultTableModel m) {
        model = m;
        add(jPanel_root);
        setTitle("Add session");
        setSize(490, 350);
        setResizable(false);
        setLocationRelativeTo(null);

        loadData();
        loadDateTimePicker();

        setDate(dateChooserStart, new Date());
        setDate(dateChooserEnd, new Date());

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!checkExistsSession()){
                        if(checkDate()){
                            JOptionPane.showMessageDialog(null, "End date must greater than start date, please try again!");
                            return;
                        }

                        saveSession();
                        JOptionPane.showMessageDialog(null, "Add success!");
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "This session is already exists, please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });

    }

    public void saveSession(){
        Sessions sessions = new Sessions();
        java.sql.Date startDate = java.sql.Date.valueOf(getDate(dateChooserStart));
        java.sql.Date endDate = java.sql.Date.valueOf(getDate(dateChooserEnd));

        sessions.setStart(startDate);
        sessions.setEnd(endDate);
        sessions.setSemesters(semesters);
        SessionDao.save(sessions);

        Object[] o = new Object[5];
        o[0] = sessions.getId();
        o[1] = sessions.getSemesters().getSchoolYear();
        o[2] = sessions.getSemesters().getType()==1?"HK1":sessions.getSemesters().getType()==2?"HK2":"HK3";
        o[3] = sessions.getStart();
        o[4] = sessions.getEnd();
        model.addRow(o);
    }

    public boolean checkExistsSession(){
        java.sql.Date startDate = java.sql.Date.valueOf(getDate(dateChooserStart));
        java.sql.Date endDate = java.sql.Date.valueOf(getDate(dateChooserEnd));

        Sessions sessions = SessionDao.getByDate(startDate,endDate);
        if(sessions == null) return false;
        return true;
    }

    public boolean checkEmptyField(){
        if(dateChooserStart.getDate()!=null && dateChooserStart.getDate()!=null) return false;

        return true;
    }

    public boolean checkDate(){
        if(dateChooserStart.getDate().compareTo(dateChooserEnd.getDate()) >= 0){
            return true;
        }
        return false;
    }

    public void loadData(){
        jLabel_schoolYear.setText(semesters.getSchoolYear());
        jLabel_semester.setText(semesters.getType()==1?"HK1":semesters.getType()==2?"HK2":"HK3");
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
