package course_registration_system;

import course_registration_system.jPanel_dashboard;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinistryDashboard extends JFrame {
    private JPanel rootPanel;
    private JPanel logo;
    private JPanel sidePanel;
    private JPanel headPanel;
    private JPanel jPanel_main;
    private JPanel menuPanel;
    private JLabel jLabel_menuItem8;
    private JLabel jLabel_menuItem9;
    private JLabel jLabel_menuItem1;
    private JLabel jLabel_menuItem2;
    private JLabel jLabel_menuItem3;
    private JLabel jLabel_menuItem4;
    private JLabel jLabel_menuItem5;
    private JLabel jLabel_menuItem6;
    private JLabel jLabel_menuItem7;
    private JPanel jPanel_account;
    // default border for the menu items
    Border defaultBorder = BorderFactory.createMatteBorder(0,0,0,0, new Color(46,49,49));

    // yellow border for the menu items
    Border yellowBorder = BorderFactory.createMatteBorder(1,0,1,0,Color.YELLOW);

    // create array of jlabels
    JLabel[] menuLabels = new JLabel[9];

    // create array of jPanels
    JPanel[] panels = new JPanel[2];

    public MinistryDashboard(){
        add(rootPanel);
        setTitle("Ministry Dashboard");
        setSize(1280,720);
        setResizable(false);
        setLocationRelativeTo(null);

        // set borders
        // panel logo border
        Border panelBorder = BorderFactory.createMatteBorder(0,0,2,0, Color.lightGray);
        headPanel.setBorder(panelBorder);

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

        // populate the menuLabels array
        panels[0] = jPanel_main;
        panels[1] = jPanel_account;

        addActionToMenuLabels();
    }

    public void addActionToMenuLabels(){
        Component[] components = menuPanel.getComponents();

        for(Component component : components){
            if(component instanceof JLabel){
                JLabel label = (JLabel) component;
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setLabelBackground(label);

                        switch (label.getText().trim()){
                            case "Dashboard":
                                //showPanel(jPanel_main);
                                break;
                            case "Account":
                                //showPanel(jPanel_account);
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
        }
    }

    public void setLabelBackground(JLabel label){
        for(JLabel menuItem : menuLabels) {
            menuItem.setForeground(Color.WHITE);
            menuItem.setBackground(new Color(36, 37, 42));
        }
        label.setBackground(Color.WHITE);
        label.setForeground(new Color(36, 37, 42));
    }

    public void showPanel(JPanel panel){
        for(JPanel pnl : panels){
            pnl.setVisible(false);
        }

        panel.setVisible(true);
    }

    private void createUIComponents() {
        jPanel_main = new jPanel_dashboard().getjPanel_dashboard();
    }
}
