package course_registration_system.JPanel_StudentDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allCourse.Add_course;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.View_registerList;
import dao.CourseDao;
import dao.RegisterDao;
import dao.SemesterDao;
import dao.SessionDao;
import pojo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllCourseRegister {
    private JTable table_allCourse;
    private JButton deleteButton;
    private JLabel jlabel_information;
    private JPanel jPanel_course;

    private List<Courses> coursesList;
    private DefaultTableModel model;
    private Users users;
    private Semesters currentSemes = SemesterDao.getCurrent();

    public JPanel getjPanel_course() {
        return jPanel_course;
    }

    public AllCourseRegister(Users u) {
        users = u;
        createTable();

        jlabel_information.setText(currentSemes.getSchoolYear() +" - " + (currentSemes.getType()==1?"HK1":currentSemes.getType()==2?"HK2":"HK3"));
        createTable();

        if(getCurrentSession()==null){
            deleteButton.setVisible(false);
        }

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_allCourse.getRowCount() > 0){
                    if(table_allCourse.getSelectedRow() != -1){
                        String id = table_allCourse.getValueAt(table_allCourse.getSelectedRow(),0).toString();
                        Courses courses = CourseDao.getById(Integer.parseInt(id));
                        Registers registers = RegisterDao.getByUserNCourse(users.getId(), courses.getId());
                        RegisterDao.delete(registers);
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
    }

    private void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Id", "Code", "Name", "Credits", "Class", "Tutor", "Room", "Week Day", "Time", "Slot"}
        );

        if(currentSemes!=null) {
            coursesList = RegisterDao.getByUserInSemes(users.getId(), currentSemes.getId());
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
                o[9] = countAvailableSlot(courses) + "/"+ courses.getMaxSlot();
                model.addRow(o);
            }
        }
        else {
            coursesList = null;
        }

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

    public int countAvailableSlot(Courses course){
        List<Courses> courses = RegisterDao.getByCourseInSemes(course.getId(), currentSemes.getId());
        return (int) courses.stream().count();
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

    public Sessions getCurrentSession(){
        List<Sessions> sessions = SessionDao.getAllOfSemester(currentSemes);
        if(sessions.stream().count() == 0)
            return null;

        Sessions last = sessions.get((int) (sessions.stream().count()-1));
        return last;
    }
}
