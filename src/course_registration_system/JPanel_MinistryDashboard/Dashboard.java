package course_registration_system.JPanel_MinistryDashboard;

import dao.*;

import javax.security.auth.Subject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard {
    private JPanel jPanel_dashboard;
    private JLabel jLabel_ministry;
    private JLabel jLabel_student;
    private JLabel jLabel_class;
    private JLabel jLabel_subject;
    private JLabel jLabel_course;
    private JLabel jLabel_semester;

    public Dashboard() {
        jLabel_ministry.setText(String.valueOf(UserDao.getAllMinistry().stream().count()));
        jLabel_student.setText(String.valueOf(UserDao.getAllStudent().stream().count()));
        jLabel_class.setText(String.valueOf(ClassDao.getAll().stream().count()));
        jLabel_subject.setText(String.valueOf(SubjectDao.getAll().stream().count()));
        jLabel_course.setText(String.valueOf(CourseDao.getAllOfSemester(SemesterDao.getCurrent().getId()).stream().count()));
        jLabel_semester.setText(String.valueOf(SemesterDao.getAll().stream().count()));
    }

    public JPanel getjPanel_dashboard() {
        return jPanel_dashboard;
    }

}
