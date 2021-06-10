package course_registration_system.JPanel_MinistryDashboard.JPanel_allCourse;

import dao.*;
import pojo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Add_course extends JFrame{
    private JComboBox comboBox_time;
    private JTextField textField_tutor;
    private JComboBox comboBox_weekDay;
    private JTextField textField_room;
    private JButton saveButton;
    private JPanel jPanel_root;
    private JComboBox comboBox_subject;
    private JTextField textField_slot;
    private JComboBox comboBox_class;
    private DefaultTableModel model;

    public Add_course(DefaultTableModel m) {
        model = m;
        add(jPanel_root);
        setTitle("Add Course");
        setSize(490, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        loadComboboxSubject();
        loadComboboxWeekday();
        loadComboboxTime();
        loadComboboxClass();

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!checkEmptyField()){
                    if(!checkExistsCourse()){
                        if(!checkExistsRoomDayTime()) {
                            if(tryParseInt() != -1) {
                                saveCourse();
                                JOptionPane.showMessageDialog(null, "Add success!");
                                dispose();
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "This room, weekday and time is already exists, please try again!");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "This course is already exists, please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Some fields is empty, please try again!");
                }
            }
        });
    }

    public void saveCourse(){
        Courses courses = new Courses();

        Subjects subjects = SubjectDao.getByName(comboBox_subject.getSelectedItem().toString());
        Classes classes = ClassDao.getByName(comboBox_class.getSelectedItem().toString());
        Semesters currentSemes = SemesterDao.getCurrent();

        courses.setSubjects(subjects);
        courses.setClasses(classes);
        courses.setSemesters(currentSemes);
        courses.setTutorName(textField_tutor.getText());
        courses.setMaxSlot(tryParseInt());
        courses.setRoom(textField_room.getText());
        courses.setWeekDay(getWeekDay(comboBox_weekDay.getSelectedItem().toString()));
        courses.setTimeCase(getTimeCase(comboBox_time.getSelectedItem().toString()));

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

        CourseDao.save(courses);
    }

    public int tryParseInt(){
        try{
            return Integer.parseInt(textField_slot.getText());
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Slot field has wrong format of integer, please try again!");
            return -1;
        }
    }

    public boolean checkExistsCourse(){
        Courses courses = CourseDao.getBySubjectNClass(comboBox_subject.getSelectedItem().toString(), comboBox_class.getSelectedItem().toString());
        if(courses == null) return false;
        return true;
    }

    public boolean checkExistsRoomDayTime(){
        Courses courses = CourseDao.getByRoomDayTime(textField_room.getText(), getWeekDay(comboBox_weekDay.getSelectedItem().toString()), getTimeCase(comboBox_time.getSelectedItem().toString()));
        if(courses == null) return false;
        return true;
    }

    public boolean checkEmptyField(){
        if(!textField_tutor.getText().equals("") && !textField_room.getText().equals("") && !textField_slot.getText().equals("")) return false;

        return true;
    }

    public void loadComboboxWeekday(){
        comboBox_weekDay.addItem("Monday");
        comboBox_weekDay.addItem("Tuesday");
        comboBox_weekDay.addItem("Wednesday");
        comboBox_weekDay.addItem("Thursday");
        comboBox_weekDay.addItem("Friday");
        comboBox_weekDay.addItem("Saturday");
        comboBox_weekDay.addItem("Sunday");
    }

    public void loadComboboxTime(){
        comboBox_time.addItem("7:30–9:30");
        comboBox_time.addItem("9:30–11:30");
        comboBox_time.addItem("13:30–15:30");
        comboBox_time.addItem("15:30–17:30");
    }

    public void loadComboboxSubject(){
        List<Subjects> subjectsList = SubjectDao.getAll();
        for (Subjects subjects:subjectsList) {
            comboBox_subject.addItem(subjects.getName());
        }
    }

    public void loadComboboxClass(){
        List<Classes> classesList = ClassDao.getAll();
        classesList.remove(0);
        for (Classes classes:classesList) {
            comboBox_class.addItem(classes.getName());
        }
    }

    private int getTimeCase(String timeCase){
        int time = 0;
        switch (timeCase){
            case "7:30–9:30":
                time = 1;
                break;
            case "9:30–11:30":
                time = 2;
                break;
            case "13:30–15:30":
                time = 3;
                break;
            case "15:30–17:30":
                time = 4;
                break;
            default:
                break;
        }
        return time;
    }

    private int getWeekDay(String weekDay){
        int day = 0;
        switch (weekDay){
            case "Sunday":
                day = 1;
                break;
            case "Monday":
                day = 2;
                break;
            case "Tuesday":
                day = 3;
                break;
            case "Wednesday":
                day = 4;
                break;
            case "Thursday":
                day = 5;
                break;
            case "Friday":
                day = 6;
                break;
            case "Saturday":
                day = 7;
                break;
            default:
                break;
        }
        return day;
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
}
