package javasql;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SelectAllTable {
    
    public SelectAllTable(){
        JFrame frame = new JFrame("All Data");
        
        String column[]={"Log_ID_PK", "Date_of_Log", "Time_of_Log", "Temperature (°C)", "Pressure (Pa)", "Humidity (g.kg-1)"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        JTable dataTable = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        dataTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        dataTable.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        dataTable.setCellSelectionEnabled(true);
        dataTable.setBounds(30,40,200,300);
        
        JScrollPane scroll = new JScrollPane(dataTable);
        
        String url = "jdbc:mysql://localhost:3306/flat_logs";
        String username = "root";
        String password = "password";
        String logid;
        String date;
        String time;
        String temp;
        String press;
        String humid;
        
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM datalogs LIMIT 1000");
            
            while(rs.next()){
                logid = Integer.toString(rs.getInt("Log_ID_PK"));
                date = rs.getDate("Date_of_Log").toString();
                time = rs.getTime("Time_of_log").toString();
                temp = Float.toString(rs.getFloat("Temperature"));
                press = Float.toString(rs.getFloat("Pressure"));
                humid = Float.toString(rs.getFloat("Humidity"));
                Object[] data = {logid, date, time, temp, press, humid};
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
