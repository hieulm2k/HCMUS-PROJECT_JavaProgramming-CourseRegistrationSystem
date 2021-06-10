package course_registration_system.JPanel_StudentDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allCourse.Add_course;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.View_registerList;
import dao.CourseDao;
import dao.RegisterDao;
import dao.SemesterDao;
import pojo.Courses;
import pojo.Registers;
import pojo.Semesters;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllRegister {
    private JTable table_course;
    private JButton registerButton;
    private JLabel jLabel_information;
    private JPanel jPanel_register;
    private Users users;
    private Semesters currentSemes = SemesterDao.getCurrent();
    private List<Courses> coursesList;
    private DefaultTableModel model;

    public JPanel getjPanel_register() {
        return jPanel_register;
    }

    public AllRegister(Users u) {
        users = u;
//        jLabel_information.setText(currentSemes.getSchoolYear() +" - " + (currentSemes.getType()==1?"HK1":currentSemes.getType()==2?"HK2":"HK3")
//        + "(Register time: " + currentSemes.getSessionsSet().stream().count());
        createTable();

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_course.getRowCount() > 0){
                    if(table_course.getSelectedRow() != -1){
                        if(checkMaxCourse()){
                            JOptionPane.showMessageDialog(null, "You registered 8 courses, cannot register anymore!");
                            return;
                        }
                        String id = table_course.getValueAt(table_course.getSelectedRow(),0).toString();
                        Courses courses = CourseDao.getById(Integer.parseInt(id));
                        if(checkExistTime(courses)){
                            JOptionPane.showMessageDialog(null, "This course has the same time with course you registered before!");
                            return;
                        }

                        if(checkMaxSlot(courses)){
                            JOptionPane.showMessageDialog(null, "This course has full slot, cannot register!");
                            return;
                        }

                        saveRegister(courses);
                        createTable();
                        JOptionPane.showMessageDialog(null, "Register success!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a course to register!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Course list is empty, cannot register!");
                }
            }
        });
    }

    private boolean checkMaxCourse(){
        if(RegisterDao.getByUserInSemes(users.getId(), currentSemes.getId()) !=null && RegisterDao.getByUserInSemes(users.getId(), currentSemes.getId()).stream().count()==8){
            return true;
        }
        return false;
    }

    private boolean checkExistTime(Courses c){
        if(RegisterDao.getByUserInSemes(users.getId(), currentSemes.getId())!=null) {
            for (Courses courses : RegisterDao.getByUserInSemes(users.getId(), currentSemes.getId())) {
                if (courses.getTimeCase() == c.getTimeCase() && courses.getWeekDay() == c.getWeekDay()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMaxSlot(Courses c){
        List<Registers> registers = RegisterDao.getByCourseId(c.getId());
        if(registers!=null && registers.stream().count() == c.getMaxSlot()) return true;
        return false;
    }

    private void saveRegister(Courses courses){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Registers registers = new Registers();
        registers.setUsers(users);
        registers.setCourses(courses);
        Timestamp timestamp = Timestamp.valueOf(dtf.format(now));
        registers.setTime(timestamp);
        RegisterDao.save(registers);
    }

    private void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Id", "Code", "Name", "Credits", "Class", "Tutor", "Room", "Week Day", "Time", "Slot"}
        );
        coursesList = getAllAvailableCourse();
        if(coursesList!=null) {
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
        table_course.getTableHeader().setOpaque(false);
        table_course.getTableHeader().setBackground(Color.DARK_GRAY);
        table_course.getTableHeader().setForeground(Color.WHITE);
        table_course.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 16));
        table_course.setModel(model);
        table_course.setFont(new Font("Serif", Font.PLAIN, 15));
        table_course.setRowHeight(30);
        table_course.getColumnModel().getColumn(0).setMinWidth(0);
        table_course.getColumnModel().getColumn(0).setMaxWidth(0);
        table_course.getColumnModel().getColumn(5).setPreferredWidth(200);
        table_course.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    private String getTimeCase(int timeCase){
        String time = null;
        switch (timeCase){
            case 1:
                time = "7:30–9:30";
                break;
            case 2:
                time = "9:30–11:30";
                break;
            case 3:
                time = "13:30–15:30";
                break;
            case 4:
                time = "15:30–17:30";
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

    private List<Courses> getAllAvailableCourse(){
        if(currentSemes==null) {
            return null;
        }

        List<Courses> coursesStudent = RegisterDao.getByUserInSemes(users.getId(), currentSemes.getId());
        List<Courses> allCourses = CourseDao.getAllOfSemester(currentSemes.getId());
        List<Courses> res = CourseDao.getAllOfSemester(currentSemes.getId());
        if(allCourses.stream().count()==0){
            return null;
        }

        if(coursesStudent.stream().count()==0){
            return res;
        }

        for(Courses courses:allCourses){
            for(Courses courses1:coursesStudent){
                if(courses.getSubjects().getId() == courses1.getSubjects().getId()){
                    res.remove(courses);
                    break;
                }
            }
        }

        return res;
    }
}
