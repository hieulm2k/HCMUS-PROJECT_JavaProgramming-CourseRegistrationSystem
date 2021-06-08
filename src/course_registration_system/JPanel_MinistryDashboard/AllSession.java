package course_registration_system.JPanel_MinistryDashboard;

import course_registration_system.JPanel_MinistryDashboard.JPanel_allSemester.Add_semester;
import course_registration_system.JPanel_MinistryDashboard.JPanel_allSession.Add_session;
import dao.SemesterDao;
import dao.SessionDao;
import pojo.Semesters;
import pojo.Sessions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AllSession {
    private JTable table_session;
    private JButton addButton;
    private JButton deleteButton;
    private JPanel jPanel_session;

    private List<Sessions> sessionsList;
    private DefaultTableModel model;

    public JPanel getjPanel_session() {
        return jPanel_session;
    }

    public AllSession() {
        createTable();

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(table_session.getRowCount() > 0){
                    if(table_session.getSelectedRow() != -1){
                        String id = table_session.getValueAt(table_session.getSelectedRow(),0).toString();
                        Sessions sessions = SessionDao.getById(Integer.parseInt(id));
                        SessionDao.delete(sessions);
                        model.removeRow(table_session.getSelectedRow());
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
                Add_session add_session = new Add_session(model);
                add_session.setVisible(true);
            }
        });
    }

    private void createTable(){
        Semesters currentSemes = SemesterDao.getCurrent();

        model = new DefaultTableModel(
                null,
                new String[]{"Id", "School Year", "Semester", "Start Date",  "End Date"}
        );

        if(currentSemes!=null) {
            sessionsList = SessionDao.getAllOfSemester(currentSemes);
            for (Sessions sessions : sessionsList) {
                Object[] o = new Object[5];
                o[0] = sessions.getId();
                o[1] = sessions.getSemesters().getSchoolYear();
                o[2] = sessions.getSemesters().getType()==1?"HK1":sessions.getSemesters().getType()==2?"HK2":"HK3";
                o[3] = sessions.getStart();
                o[4] = sessions.getEnd();

                model.addRow(o);
            }
        }
        else {
            sessionsList = null;
        }

        table_session.getTableHeader().setOpaque(false);
        table_session.getTableHeader().setBackground(Color.DARK_GRAY);
        table_session.getTableHeader().setForeground(Color.WHITE);
        table_session.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 18));
        table_session.setModel(model);
        table_session.setFont(new Font("Serif", Font.PLAIN, 18));
        table_session.setRowHeight(30);
        table_session.getColumnModel().getColumn(0).setMinWidth(0);
        table_session.getColumnModel().getColumn(0).setMaxWidth(0);
    }
}
