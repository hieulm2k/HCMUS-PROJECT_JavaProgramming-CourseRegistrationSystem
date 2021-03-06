package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.Add_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.Edit_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allAccount.ResetPassword_account;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allCourse.Add_course;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.View_registerList;
import dao.*;
import pojo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

public class AllCourse {
    private JTable table_allCourse;
    private JButton addButton;
    private JButton editButton;
    private JButton resetPasswordButton;
    private JButton deleteButton;
    private JPanel jPanel_course;
    private JLabel jLabel_schoolYear;
    private JLabel jlabel_semester;
    private JButton registerButton;
    private JButton searchButton;
    private JTextField textField_subject;
    private List<Courses> coursesList;
    private DefaultTableModel model;

    public JPanel getjPanel_allAccount() {
        return jPanel_course;
    }

    public AllCourse() {
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allCourse.getRowCount() > 0){
                    if(table_allCourse.getSelectedRow() != -1){
                        String subjectName= table_allCourse.getValueAt(table_allCourse.getSelectedRow(),2).toString();
                        String classes = table_allCourse.getValueAt(table_allCourse.getSelectedRow(),4).toString();
                        Courses courses = CourseDao.getBySubjectNClass(subjectName,classes);
                        CourseDao.delete(courses);
                        model.removeRow(table_allCourse.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "Delete success!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a row to delete!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot delete!");
                }
            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Add_course add_course = new Add_course(model);
                add_course.setVisible(true);
            }
        });

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allCourse.getRowCount() > 0){
                    if(table_allCourse.getSelectedRow() != -1){
                        String subjectName= table_allCourse.getValueAt(table_allCourse.getSelectedRow(),2).toString();
                        String classes = table_allCourse.getValueAt(table_allCourse.getSelectedRow(),4).toString();
                        Courses courses = CourseDao.getBySubjectNClass(subjectName,classes);
                        View_registerList view_registerList = new View_registerList(courses);
                        view_registerList.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a course to view register list!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot view register list!");
                }

            }
        });
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                trySearch();
            }
        });
    }

    private void createTable(){
        Semesters currentSemes = SemesterDao.getCurrent();
        jLabel_schoolYear.setText(currentSemes.getSchoolYear() + " - ");
        jlabel_semester.setText(currentSemes.getType()==1?"HK1":currentSemes.getType()==2?"HK2":"HK3");
        model = new DefaultTableModel(
                null,
                new String[]{"Id", "Code", "Name", "Credits", "Class", "Tutor", "Room", "Week Day", "Time", "Slot"}
        );

        if(currentSemes!=null) {
            coursesList = CourseDao.getAllOfSemester(currentSemes.getId());
            for (Courses courses : coursesList) {
                Object[] o = new Object[10];
                o[0] = courses.getId();
                o[1] = courses.getSubjects().getCode();
                o[2] = courses.getSubjects().getName();
                o[3] = courses.getSubjects().getCredit();
                o[4] = courses.getClasses().getName();
                o[5] = courses.getTutorName();
                o[6] = courses.getRoom();
                o[7] = getWeekDay(courses.getWeekDay());
                o[8] = getTimeCase(courses.getTimeCase());
                o[9] = courses.getMaxSlot();
                model.addRow(o);
            }
        }
        else {
            coursesList = null;
        }
        setTable();
    }

    private void setTable(){
        table_allCourse.getTableHeader().setOpaque(false);
        table_allCourse.getTableHeader().setBackground(Color.DARK_GRAY);
        table_allCourse.getTableHeader().setForeground(Color.WHITE);
        table_allCourse.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 16));
        table_allCourse.setModel(model);
        table_allCourse.setFont(new Font("Serif", Font.PLAIN, 15));
        table_allCourse.setRowHeight(30);
        table_allCourse.getColumnModel().getColumn(0).setMinWidth(0);
        table_allCourse.getColumnModel().getColumn(0).setMaxWidth(0);
        table_allCourse.getColumnModel().getColumn(5).setPreferredWidth(200);
        table_allCourse.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    private void trySearch(){
        String code = textField_subject.getText();
        if(code.equals("")){
            createTable();
            return;
        }

        Subjects subjects = SubjectDao.getByCode(code);
        if(subjects == null) {
            JOptionPane.showMessageDialog(null, "This subject is not exists, please try again!");
            return;
        }

        Set<Courses> courses = subjects.getCoursesSet();
        if(courses.stream().count() == 0){
            JOptionPane.showMessageDialog(null, "Cannot find any courses, please try again!");
        }
        else {
            model = new DefaultTableModel(
                    null,
                    new String[]{"Id", "Code", "Name", "Credits", "Class", "Tutor", "Room", "Week Day", "Time", "Slot"}
            );
            for(Courses course:courses) {
                Object[] o = new Object[10];
                o[0] = course.getId();
                o[1] = course.getSubjects().getCode();
                o[2] = course.getSubjects().getName();
                o[3] = course.getSubjects().getCredit();
                o[4] = course.getClasses().getName();
                o[5] = course.getTutorName();
                o[6] = course.getRoom();
                o[7] = getWeekDay(course.getWeekDay());
                o[8] = getTimeCase(course.getTimeCase());
                o[9] = course.getMaxSlot();
                model.addRow(o);
            }
            setTable();
        }
    }

    private String getTimeCase(int timeCase){
        String time = null;
        switch (timeCase){
            case 1:
                time = "7:30???9:30";
                break;
            case 2:
                time = "9:30???11:30";
                break;
            case 3:
                time = "13:30???15:30";
                break;
            case 4:
                time = "15:30???17:30";
                break;
            default:
                break;
        }
        return time;
    }

    private String getWeekDay(int weekDay){
        String day = null;
        switch (weekDay){
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
            default:
                break;
        }
        return day;
    }
}
