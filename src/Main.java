import course_registration_system.Login;
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
