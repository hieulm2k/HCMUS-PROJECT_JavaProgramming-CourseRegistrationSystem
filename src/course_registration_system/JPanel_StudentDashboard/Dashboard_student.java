package course_registration_system.JPanel_StudentDashboard;

import dao.*;
import pojo.Semesters;

import javax.swing.*;

public class Dashboard_student {
    private JPanel jPanel_dashboard;
    private JLabel jLabel_schoolyear;
    private JLabel jLabel_course;
    private JLabel jLabel_semester;
    private JPanel jPanel_root;
    private Semesters semesters = SemesterDao.getCurrent();
    public Dashboard_student() {
        jLabel_schoolyear.setText(semesters.getSchoolYear());
        jLabel_course.setText(String.valueOf(CourseDao.getAllOfSemester(semesters.getId()).stream().count()));
        jLabel_semester.setText(semesters.getType()==1?"HK1":semesters.getType()==2?"HK2":"HK3");
    }

    public JPanel getjPanel_root() {
        return jPanel_root;
    }
}
