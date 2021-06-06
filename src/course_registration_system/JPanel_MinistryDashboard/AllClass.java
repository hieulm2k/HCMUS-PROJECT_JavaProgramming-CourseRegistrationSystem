package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allClass.Add_class;
import dao.ClassDao;
import pojo.Classes;
import pojo.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllClass {
    private JTable table_class;
    private JButton addButton;
    private JButton deleteButton;
    private JPanel jPanel_class;

    private List<Classes> classesList;
    private DefaultTableModel model;

    public JPanel getjPanel_class() {
        return jPanel_class;
    }

    public AllClass() {
        classesList = ClassDao.getAll();
        //classesList.remove(classesList.get(0));
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_class.getRowCount() > 0){
                    if(table_class.getSelectedRow() != -1){
                        String name = table_class.getValueAt(table_class.getSelectedRow(),0).toString();
                        Classes classes = ClassDao.getByName(name);
                        ClassDao.delete(classes);
                        model.removeRow(table_class.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "Delete success!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a row to delete!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot delete!");
                }
            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Add_class add = new Add_class(model);
                add.setVisible(true);
            }
        });
    }

    public void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Name", "Total", "Total of Male", "Total of Female"}
        );

        for (Classes classes : classesList) {
            long total = classes.getUsersSet().stream().count();
            long totalMale = getTotalOfMale(classes);

            Object[] o = new Object[4];
            o[0] = classes.getName();
            o[1] = total;
            o[2] = totalMale;
            o[3] = total - totalMale;
            model.addRow(o);
        }

        table_class.getTableHeader().setOpaque(false);
        table_class.getTableHeader().setBackground(Color.DARK_GRAY);
        table_class.getTableHeader().setForeground(Color.WHITE);
        table_class.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_class.setModel(model);
        table_class.setFont(new Font("Serif", Font.PLAIN, 18));
        table_class.setRowHeight(30);
    }

    public long getTotalOfMale(Classes classes){
        long sum = 0;
        for (Users users: classes.getUsersSet()) {
            sum += users.getGender();
        }
        return sum;
    }
}
