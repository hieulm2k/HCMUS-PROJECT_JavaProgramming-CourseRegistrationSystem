package course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject;

import dao.RegisterDao;
import dao.UserDao;
import pojo.Courses;
import pojo.Registers;
import pojo.Subjects;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View_registerList extends JFrame{
    private JTable table_register;
    private JPanel jPanel_root;
    private JTextField textField_student;
    private JButton searchButton;

    private DefaultTableModel model;
    private Courses courses;
    private List<Registers> registersList;

    public View_registerList(Courses c){
        courses = c;

        add(jPanel_root);
        setTitle("Register List Of Course");
        setSize(1200, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        createTable();
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(textField_student.getText().equals("")){
                    createTable();
                }
                else{
                    trySearch();
                }
            }
        });
    }

    private void createTable(){
        registersList = RegisterDao.getByCourseId(courses.getId());

        model = new DefaultTableModel(
                null,
                new String[]{"Student Code", "Student Name", "Subject Code", "Subject Name", "Tutor", "Study Time", "Register Time"}
        );

        if(registersList!=null) {
            for (Registers registers : registersList) {
                Courses courses = registers.getCourses();
                Users users = registers.getUsers();
                Subjects subjects = courses.getSubjects();

                Object[] o = new Object[7];
                o[0] = users.getStdCode();
                o[1] = users.getName();
                o[2] = subjects.getCode();
                o[3] = subjects.getName();
                o[4] = courses.getTutorName();
                o[5] = getTimeCase(courses.getTimeCase());
                o[6] = registers.getTime();
                model.addRow(o);
            }
        }
        setTable();
    }

    private void setTable(){
        table_register.getTableHeader().setOpaque(false);
        table_register.getTableHeader().setBackground(Color.DARK_GRAY);
        table_register.getTableHeader().setForeground(Color.WHITE);
        table_register.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_register.setModel(model);
        table_register.setFont(new Font("Serif", Font.PLAIN, 18));
        table_register.setRowHeight(30);
        table_register.getColumnModel().getColumn(1).setPreferredWidth(150);
        table_register.getColumnModel().getColumn(3).setPreferredWidth(200);
        table_register.getColumnModel().getColumn(4).setPreferredWidth(150);
        table_register.getColumnModel().getColumn(6).setPreferredWidth(170);
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

    private void trySearch(){
        String studentCode = textField_student.getText();
        Users users = UserDao.getByStdCode(studentCode);
        if(users == null){
            JOptionPane.showMessageDialog(null, "This student code is not exists, please try again!");
        }
        else {
            model = new DefaultTableModel(
                    null,
                    new String[]{"Student Code", "Student Name", "Subject Code", "Subject Name", "Tutor", "Study Time", "Register Time"}
            );

            Registers registers = RegisterDao.getByUserNCourse(users.getId(), courses.getId());

            if(registers!=null) {
                    Courses course = registers.getCourses();
                    Users user = registers.getUsers();
                    Subjects subjects = course.getSubjects();
                    Object[] o = new Object[7];
                    o[0] = user.getStdCode();
                    o[1] = user.getName();
                    o[2] = subjects.getCode();
                    o[3] = subjects.getName();
                    o[4] = course.getTutorName();
                    o[5] = getTimeCase(course.getTimeCase());
                    o[6] = registers.getTime();
                    model.addRow(o);
            }
            else {
                JOptionPane.showMessageDialog(null, "This student has not registered for this course");
            }
            setTable();
        }
    }
}
