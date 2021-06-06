package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allSemester.Add_semester;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.Add_subject;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSubject.Edit_subject;
import dao.SemesterDao;
import pojo.Semesters;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllSemester {
    private JTable table_semester;
    private JButton addButton;
    private JButton setCurrentSemesterButton;
    private JButton deleteButton;
    private JPanel jPanel_semester;

    private List<Semesters> semestersList;
    private DefaultTableModel model;

    public JPanel getjPanel_semester() {
        return jPanel_semester;
    }

    public AllSemester() {
        semestersList = SemesterDao.getAll();
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_semester.getRowCount() > 0){
                    if(table_semester.getSelectedRow() != -1){
                        String id = table_semester.getValueAt(table_semester.getSelectedRow(),0).toString();
                        Semesters semesters = SemesterDao.getById(Integer.parseInt(id));
                        SemesterDao.delete(semesters);
                        model.removeRow(table_semester.getSelectedRow());
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
                Add_semester add_semester = new Add_semester(model);
                add_semester.setVisible(true);
            }
        });

        setCurrentSemesterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_semester.getRowCount() > 0){
                    if(table_semester.getSelectedRow() != -1){
                        String id = table_semester.getValueAt(table_semester.getSelectedRow(),0).toString();
                        Semesters semesters = SemesterDao.getById(Integer.parseInt(id));
                        setCurrent(semesters, table_semester.getSelectedRow());
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select a semester to set as current!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your table is empty, cannot set as current!");
                }
            }
        });
    }

    private void createTable(){
        model = new DefaultTableModel(
                null,
                new String[]{"Id", "Name", "School Year", "Start Date",  "End Date", "Is Current?"}
        );
        for (Semesters semesters : semestersList) {
            Object[] o = new Object[6];
            o[0] = semesters.getId();
            o[1] = semesters.getType()==1?"HK1":semesters.getType()==2?"HK2":"HK3";
            o[2] = semesters.getSchoolYear();
            o[3] = semesters.getStartDate();
            o[4] = semesters.getEndDate();
            o[5] = semesters.getIsCurrent()==1?"YES":"NO";
            model.addRow(o);
        }
        table_semester.getTableHeader().setOpaque(false);
        table_semester.getTableHeader().setBackground(Color.DARK_GRAY);
        table_semester.getTableHeader().setForeground(Color.WHITE);
        table_semester.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_semester.setModel(model);
        table_semester.setFont(new Font("Serif", Font.PLAIN, 18));
        table_semester.setRowHeight(30);
    }

    public void setCurrent(Semesters semesters, int row){
        Semesters last = SemesterDao.getCurrent();
        if(last!=null) {
            last.setIsCurrent((byte) 0);
            SemesterDao.update(last);
        }

        for(int i = 0; i < model.getRowCount(); i++){
            model.setValueAt("NO",i,5);
        }

        model.setValueAt("YES", row, 5);
        semesters.setIsCurrent((byte) 1);
        SemesterDao.update(semesters);
    }
}
