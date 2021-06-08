package course_registration_system.JPanel_MinistryDashboard.JPanel_allStudent;

import dao.RegisterDao;
import pojo.Subjects;
import pojo.Users;
import pojo.Registers;
import pojo.Courses;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class View_subject extends JFrame{
    private JTable table_subject;
    private JPanel jPanel_root;

    private DefaultTableModel model;
    private Users users;

    public View_subject(Users u){
        users = u;

        add(jPanel_root);
        setTitle("View All Subject Of Student");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        createTable();
    }

    private void createTable(){
        List<Registers> registersList = RegisterDao.getByUserId(users.getId());

        model = new DefaultTableModel(
                null,
                new String[]{"Code", "Name", "Num of Credits"}
        );

        if(registersList!=null) {
            for (Registers registers : registersList) {
                Courses courses = registers.getCourses();
                Subjects subjects = courses.getSubjects();
                Object[] o = new Object[3];
                o[0] = subjects.getCode();
                o[1] = subjects.getName();
                o[2] = subjects.getCredit();
                model.addRow(o);
            }
        }

        table_subject.getTableHeader().setOpaque(false);
        table_subject.getTableHeader().setBackground(Color.DARK_GRAY);
        table_subject.getTableHeader().setForeground(Color.WHITE);
        table_subject.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_subject.setModel(model);
        table_subject.setFont(new Font("Serif", Font.PLAIN, 18));
        table_subject.setRowHeight(30);
    }
}
