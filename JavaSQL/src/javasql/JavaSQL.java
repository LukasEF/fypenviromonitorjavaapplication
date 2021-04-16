package javasql;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class JavaSQL {
    
    public static void main(String[] args) {
        JFrame f = new JFrame("Environmental Monitor");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JPanel titleContainer = new JPanel();
        titleContainer.setBounds(2, 0, 410, 50);
        Color titlePanelBG = new Color(153,255,153);
        titleContainer.setBackground(titlePanelBG);
        JLabel title = new JLabel("Environmental Monitor");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        titleContainer.add(title);
        
        f.add(titleContainer);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(2, 55, 410, 350);
        GridLayout grid = new GridLayout(2,2);
        grid.setHgap(25);
        grid.setVgap(25);
        buttonPanel.setLayout(grid);
        
        JButton viewAllButton = new JButton("View All Data");
        viewAllButton.setSize(150, 30);
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectAllTable all = new SelectAllTable();
            }
        });
        
        JButton temperatureButton = new JButton("Temperature");
        temperatureButton.setSize(150,30);
        temperatureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TemperatureMenu temp = new TemperatureMenu();
            }
        });
        
        JButton humidityButton = new JButton("Humidity");
        humidityButton.setSize(150,30);
        humidityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HumidityMenu humid = new HumidityMenu();
            }
        });
        
        JButton pressureButton = new JButton("Pressure");
        pressureButton.setSize(150,30);
        pressureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PressureMenu pressure = new PressureMenu();
            }
        });
        
        buttonPanel.add(viewAllButton);
        buttonPanel.add(temperatureButton);
        buttonPanel.add(humidityButton);
        buttonPanel.add(pressureButton);
        
        f.add(buttonPanel);
        
        f.setSize(429,450);
        f.setLayout(null);
        f.setResizable(false);
        f.setVisible(true);
    }
    
}
