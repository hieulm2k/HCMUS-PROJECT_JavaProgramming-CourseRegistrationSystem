import course_registration_system.JPanel_MinistryDashboard.JPanel_dashboard;
import course_registration_system.Login;
import course_registration_system.MinistryDashboard;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               Login loginForm = new Login();
               loginForm.setVisible(true);
           }
       });
    }
}
