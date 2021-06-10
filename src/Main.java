import course_registration_system.Database_login;
import course_registration_system.Login;
import dao.UserDao;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               Database_login database_login = new Database_login();
               database_login.setVisible(true);
           }
       });
    }
}
