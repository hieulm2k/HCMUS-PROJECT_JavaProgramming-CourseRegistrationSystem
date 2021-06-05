package course_registration_system;

import course_registration_system.JPanel_MinistryDashboard.AllAccount;
import course_registration_system.JPanel_MinistryDashboard.Dashboard;
import course_registration_system.JPanel_MinistryDashboard.MyAccount;
import course_registration_system.JPanel_MinistryDashboard.AllSubject;
import pojo.Users;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinistryDashboard extends JFrame {
    private JPanel jPanel_root;
    private JPanel logo;
    private JPanel jPanel_side;
    private JPanel jPanel_header;
    private JPanel jPanel_main;
    private JPanel jPanel_menu;
    private JLabel jLabel_menuItem8;
    private JLabel jLabel_menuItem1;
    private JLabel jLabel_menuItem2;
    private JLabel jLabel_menuItem3;
    private JLabel jLabel_menuItem4;
    private JLabel jLabel_menuItem5;
    private JLabel jLabel_menuItem6;
    private JLabel jLabel_menuItem7;
    private JLabel jLabel_menuItem10;
    private JLabel jLabel_menuItem9;
    private JLabel jLabel_menuItem11;
    private Users users;
    // default border for the menu items
    Border defaultBorder = BorderFactory.createMatteBorder(0,0,0,0, new Color(46,49,49));

    // yellow border for the menu items
    Border yellowBorder = BorderFactory.createMatteBorder(1,0,1,0,Color.YELLOW);

    // create array of jlabels
    JLabel[] menuLabels = new JLabel[11];

    public MinistryDashboard(Users users){
        add(jPanel_root);
        setTitle("Ministry Dashboard");
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
        menuLabels[2] = jLabel_menuItem3;
        menuLabels[3] = jLabel_menuItem4;
        menuLabels[4] = jLabel_menuItem5;
        menuLabels[5] = jLabel_menuItem6;
        menuLabels[6] = jLabel_menuItem7;
        menuLabels[7] = jLabel_menuItem8;
        menuLabels[8] = jLabel_menuItem9;
        menuLabels[9] = jLabel_menuItem10;
        menuLabels[10] = jLabel_menuItem11;

        jPanel_main.add(new Dashboard().getjPanel_dashboard());

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
                        showPanel(new Dashboard().getjPanel_dashboard());
                        break;
                    case "All Account":
                        showPanel(new AllAccount(users).getjPanel_allAccount());
                        break;
                    case "Subject":
                        showPanel(new AllSubject().getjPanel_subject());
                        break;
                    case "Semester":

                        break;
                    case "Class":

                        break;
                    case "Student":

                        break;
                    case "Session":

                        break;
                    case "Course":

                        break;
                    case "Register List":

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
}
