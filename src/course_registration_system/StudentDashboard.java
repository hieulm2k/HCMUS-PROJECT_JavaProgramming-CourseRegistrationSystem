package course_registration_system;

import com.mysql.cj.Session;
import course_registration_system.JPanel_MinistryDashboard.*;
import course_registration_system.JPanel_StudentDashboard.AllCourseRegister;
import course_registration_system.JPanel_StudentDashboard.AllRegister;
import course_registration_system.JPanel_StudentDashboard.Dashboard_student;
import dao.SemesterDao;
import dao.SessionDao;
import pojo.Semesters;
import pojo.Sessions;
import pojo.Users;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.List;

public class StudentDashboard extends JFrame{
    private JPanel jPanel_root;
    private JPanel jPanel_side;
    private JPanel jPanel_header;
    private JPanel logo;
    private JPanel jPanel_menu;
    private JLabel jLabel_menuItem1;
    private JLabel jLabel_menuItem2;
    private JLabel jLabel_menuItem9;
    private JLabel jLabel_menuItem10;
    private JLabel jLabel_menuItem11;
    private JPanel jPanel_main;
    private Users users;
    private Semesters currentSemes = SemesterDao.getCurrent();

    // default border for the menu items
    Border defaultBorder = BorderFactory.createMatteBorder(0,0,0,0, new Color(46,49,49));

    // yellow border for the menu items
    Border yellowBorder = BorderFactory.createMatteBorder(1,0,1,0,Color.YELLOW);

    // create array of jlabels
    JLabel[] menuLabels = new JLabel[5];

    public StudentDashboard(Users users){
        add(jPanel_root);
        setTitle("Student Dashboard");
        setSize(1280,720);
        setResizable(false);
        setLocationRelativeTo(null);

        this.users = users;

        // set borders
        // panel logo border
        Border panelBorder = BorderFactory.createMatteBorder(0,0,2,0, Color.lightGray);
        jPanel_header.setBorder(panelBorder);

        // populate the menuLabels array
        menuLabels[0] = jLabel_menuItem1;
        menuLabels[1] = jLabel_menuItem2;
        menuLabels[2] = jLabel_menuItem9;
        menuLabels[3] = jLabel_menuItem10;
        menuLabels[4] = jLabel_menuItem11;

        jPanel_main.add(new Dashboard_student().getjPanel_root());

        addActionToMenuLabels();
    }

    public void addActionToMenuLabels(){
        Component[] components = jPanel_menu.getComponents();

        for(Component component : components){
            if(component instanceof JLabel){
                addMouseListener(component);
            }
        }
    }

    public void addMouseListener(Component component){
        JLabel label = (JLabel) component;
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setLabelBackground(label, menuLabels);
                switch (label.getText().trim()){
                    case "Dashboard":
                        showPanel(new Dashboard_student().getjPanel_root());
                        break;
                    case "Register Course":
                        if(!checkSession()){
                            JOptionPane.showMessageDialog(null,"Register session expired, cannot register!");
                        }
                        else {
                            showPanel(new AllRegister(users).getjPanel_register());
                        }
                        break;
                    case "Course List":
                       showPanel(new AllCourseRegister(users).getjPanel_course());
                        break;
                    case "My Account":
                        showPanel(new MyAccount(users).getjPanel_myAcount());
                        break;
                    case "Log Out":
                        dispose();
                        Login login = new Login();
                        login.setVisible(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBorder(yellowBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBorder(defaultBorder);
            }
        });
    }

    private void showPanel(JPanel jPanel) {

        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        jPanel_main.removeAll();
                        jPanel_main.add(jPanel);
                    }
                }
        );
    }

    public void setLabelBackground(JLabel label, JLabel[] jLabels){

        for(JLabel menuItem : jLabels) {
            menuItem.setForeground(Color.WHITE);
            menuItem.setBackground(new Color(36, 37, 42));
        }

        label.setBackground(Color.WHITE);
        label.setForeground(new Color(36, 37, 42));
    }

    public boolean checkSession(){
        if(currentSemes == null) return false;
        LocalDate now = LocalDate.now();

        Sessions current = getCurrentSession();
        if(current == null)
            return false;

        if(now.compareTo(current.getStart().toLocalDate())>=0 && now.compareTo(current.getEnd().toLocalDate())<=0){
            return true;
        }

        return false;
    }

    public Sessions getCurrentSession(){
        List<Sessions> sessions = SessionDao.getAllOfSemester(currentSemes);
        if(sessions.stream().count() == 0)
            return null;

        Sessions last = sessions.get((int) (sessions.stream().count()-1));
        return last;
    }
}
