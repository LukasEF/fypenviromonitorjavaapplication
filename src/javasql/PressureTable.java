package javasql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PressureTable {
    
    public PressureTable(String query){
        JFrame frame = new JFrame("Pressure by Date");
        
        String column[]={"Log_ID_PK", "Date_of_Log", "Time_of_Log", "Pressure (Pa)"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        JTable dataTable = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        dataTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        dataTable.setCellSelectionEnabled(true);
        dataTable.setBounds(30,40,200,300);
        
        JScrollPane scroll = new JScrollPane(dataTable);
        
        String url = "jdbc:mysql://localhost:3306/flat_logs";
        String username = "root";
        String password = "password";
        String logid;
        String date;
        String time;
        String pressure;
        
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                logid = Integer.toString(rs.getInt("Log_ID_PK"));
                date = rs.getDate("Date_of_Log").toString();
                time = rs.getTime("Time_of_log").toString();
                pressure = Float.toString(rs.getFloat("Pressure"));
                Object[] data = {logid, date, time, pressure};
                tableModel.addRow(data);
            }
            con.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
        frame.add(scroll);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
}
