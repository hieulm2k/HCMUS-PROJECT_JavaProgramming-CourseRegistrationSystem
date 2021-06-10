package course_registration_system;

import dao.UserDao;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import pojo.Users;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class Database_login extends JFrame {
    private JPanel rootPane;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JCheckBox showPasswordCheckBox;
    private JButton loginButton;
    public static final String xmlFilePath = "src\\hibernate.cfg.xml";
    public Database_login()
    {
        add(rootPane);
        setTitle("Login To Database");
        setSize(400,400);
        setResizable(false);
        setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = txtUsername.getText();
                    String password = new String(txtPassword.getPassword());

                    if(username.equals("") || password.equals("")){
                        JOptionPane.showMessageDialog(null, "Your input field cannot empty!");
                    }
                    else {
                        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

                        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                        Document document = documentBuilder.parse(xmlFilePath);

                        Node usernameTag = document.getElementsByTagName("property").item(2);
                        Node passwordTag = document.getElementsByTagName("property").item(3);
                        usernameTag.setTextContent(username);
                        passwordTag.setTextContent(password);

                        // write the DOM object to the file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();

                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource domSource = new DOMSource(document);

                        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
                        transformer.transform(domSource, streamResult);

                        //text connection
                        try {
                            List<Users> usersList = UserDao.getAll();
                        }
                        catch (ExceptionInInitializerError exceptionInInitializerError){
                            JOptionPane.showMessageDialog(null, "Login fail!");
                            return;
                        }
                        catch (NoClassDefFoundError noClassDefFoundError){
                            JOptionPane.showMessageDialog(null, "Login fail!");
                            return;
                        }
                        dispose();
                        Login login = new Login();
                        login.setVisible(true);
                    }
                }
                catch(Exception exp){
                    System.out.println(exp);
                    JOptionPane.showMessageDialog(null, "Login fail!");
                }
            }
        });

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswordCheckBox.isSelected()){
                    txtPassword.setEchoChar((char)0);
                }else {
                    txtPassword.setEchoChar('•');
                }
            }
        });
    }
}
