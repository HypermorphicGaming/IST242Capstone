package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class systemUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel viewPanel;
    private JPanel addPanel;
    private JPanel updatePanel;
    private JPanel searchPanel;
    private JPanel costPanel;


    public systemUI(){
        frame = new JFrame("Vehicle Maintenance System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(5, 1, 5, 5));
        JButton viewBtn = new JButton("View Vehicles");
        JButton addBtn = new JButton("Add Vehicle");
        JButton updateBtn = new JButton("Update Vehicle");
        JButton searchBtn = new JButton("Search Vehicle");
        JButton costBtn = new JButton("Calculate Avg Cost");

        menu.add(viewBtn);
        menu.add(addBtn);
        menu.add(updateBtn);
        menu.add(searchBtn);
        menu.add(costBtn);

        frame.add(menu, BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        createPanels();

        show(viewPanel);

        viewBtn.addActionListener(e -> show(viewPanel));
        addBtn.addActionListener(e -> show(addPanel));
        updateBtn.addActionListener(e -> show(updatePanel));
        searchBtn.addActionListener(e -> show(searchPanel));
        costBtn.addActionListener(e -> show(costPanel));

        frame.setVisible(true);


    }

    public void createPanels(){
        viewPanel = setViewPanel();
        addPanel = setAddPanel();
        updatePanel = setUpdatePanel();
        searchPanel = setSearchPanel();
        costPanel = setCostPanel();
    }

    public void show(JPanel panel){
        mainPanel.removeAll();
        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public JPanel setViewPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Vehicle List: "));
        return panel;
    }

    public JPanel setAddPanel(){
        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.add(new JLabel("VIN:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Make:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Model:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Year:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Type:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Cost Estimate:"));
        panel.add(new JTextField());
        panel.add(new JButton("Add Vehicle"));
        return panel;
    }

    public JPanel setUpdatePanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Update or Delete Vehicle"));
        return panel;
    }

    public JPanel setSearchPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("VIN: "));
        panel.add(new JTextField(17));
        panel.add(new JButton("Find Vehicle"));
        return panel;
    }

    public JPanel setCostPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Vehicle Type: "));
        panel.add(new JComboBox<>(new String[]{"Car", "Truck", "Motorcycle"}));
        panel.add(new JButton("Calculate"));
        return panel;
    }


}
