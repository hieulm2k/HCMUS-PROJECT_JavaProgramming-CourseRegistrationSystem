package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.Add_subject;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.Edit_subject;
import dao.SubjectDao;
import pojo.Subjects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllSubject {
    private JTable table_subject;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JPanel jPanel_subject;
    private JButton registerListButton;
    private List<Subjects> subjectsList;
    private DefaultTableModel model;

    public JPanel getjPanel_subject() {
        return jPanel_subject;
    }

    public AllSubject() {
        subjectsList = SubjectDao.getAll();
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_subject.getRowCount() > 0){
                    if(table_subject.getSelectedRow() != -1){
                        String code = table_subject.getValueAt(table_subject.getSelectedRow(),0).toString();
                        Subjects subjects = SubjectDao.getByCode(code);
                        SubjectDao.delete(subjects);
                        model.removeRow(table_subject.getSelectedRow());
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
                Add_subject add_subject = new Add_subject(model);
                add_subject.setVisible(true);
            }
        });

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_subject.getRowCount() > 0){
                    if(table_subject.getSelectedRow() != -1){
                        String code = table_subject.getValueAt(table_subject.getSelectedRow(),0).toString();
                        Subjects subjects = SubjectDao.getByCode(code);
                        Edit_subject edit = new Edit_subject(subjects, model, table_subject.getSelectedRow());
                        edit.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a row to edit!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot edit!");
                }
            }
        });
    }

    private void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Code", "Name", "Num of Credits"}
        );
        for (Subjects subjects : subjectsList) {
            Object[] o = new Object[3];
            o[0] = subjects.getCode();
            o[1] = subjects.getName();
            o[2] = subjects.getCredit();
            model.addRow(o);
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
