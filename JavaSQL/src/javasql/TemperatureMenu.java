package javasql;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TemperatureMenu {
    
    public TemperatureMenu(){
        String queryTemplate = "SELECT Log_ID_PK, Date_of_Log, Time_of_Log, Temperature FROM datalogs WHERE Date_of_Log=";
        String averageQueryTemplate = "SELECT Temperature FROM datalogs WHERE Date_of_Log=";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        
        JFrame tempMenu = new JFrame("Temperature Menu");
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(2, 0, 410, 50);
        Color titlePanelBG = new Color(153,255,153);
        titlePanel.setBackground(titlePanelBG);
        JLabel title = new JLabel("Temperature Menu");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        titlePanel.add(title);
        
        tempMenu.add(titlePanel);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, Y_AXIS));
        contentPanel.setBounds(2, 55, 410, 120);
        
        JPanel searchContainer = new JPanel();
        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setFont(new Font("Serif", Font.BOLD, 28));
        searchContainer.add(dateLabel);
        JDateChooser dateField = new JDateChooser();
        dateField.setDateFormatString("yyyy-MM-dd");
        dateField.setPreferredSize(new Dimension(200, 40));
        searchContainer.add(dateField);
        contentPanel.add(searchContainer);
        
        JTextPane averageTextPane = new JTextPane();
        averageTextPane.setFont(new Font("Serif", Font.PLAIN, 20));
        StyledDocument doc = averageTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        JPanel buttonContainer = new JPanel();
        JButton searchButton = new JButton("View All Data");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dateField.getDate() == null){
                    showMessageDialog(null, "No date has been entered");
                }
                else{
                    String strDate = dateFormat.format(dateField.getDate());  
                    String finalQuery = queryTemplate + "'" + strDate + "'";
                    TemperatureTable tempTable = new TemperatureTable(finalQuery);
                }
            }
        });
        JButton averageButton = new JButton("Get Average");
        averageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dateField.getDate() == null){
                    showMessageDialog(null, "No date has been entered");
                }
                else{
                    String strDate = dateFormat.format(dateField.getDate());  
                    String finalAverageQuery = averageQueryTemplate + "'" + strDate + "'";
                    averageTextPane.setText(String.valueOf(getAverageTempForDate(finalAverageQuery)));
                }
            }
        });
        buttonContainer.add(searchButton);
        buttonContainer.add(averageButton);
        
        contentPanel.add(buttonContainer);
        contentPanel.add(averageTextPane);
        
        tempMenu.add(contentPanel);
        
        tempMenu.setSize(429,250);
        tempMenu.setLayout(null);
        tempMenu.setResizable(false);
        tempMenu.setVisible(true);
    }
    
    private float getAverageTempForDate(String query){
        String url = "jdbc:mysql://localhost:3306/flat_logs";
        String username = "root";
        String password = "password";
        int resultsLength=0;
        float temp;
        float total = 0;
        float average = 0;
        
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                temp = rs.getFloat("Temperature");
                total+=temp;
                resultsLength++;
            }
            con.close();
            
            average = total/resultsLength;
        }
        catch(SQLException e){
            showMessageDialog(null, e);
        }
        
        return average;
    }
    
}
