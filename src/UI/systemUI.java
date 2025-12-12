package UI;

import db.Database;
import vehicles.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class systemUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTable table;
    private DefaultTableModel tableModel;

    public systemUI() {
        SwingUtilities.invokeLater(this::buildUI);
    }

    private void buildUI() {
        frame = new JFrame("IST Vehicle Care Solutions - Maintenance System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        JPanel menu = new JPanel(new GridLayout(6,1,5,5));
        JButton refreshBtn = new JButton("Refresh List");
        JButton addBtn = new JButton("Add Vehicle");
        JButton updateBtn = new JButton("Load Selected to Edit");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton searchBtn = new JButton("Search by VIN");
        JButton avgBtn = new JButton("Avg Cost by Type");

        menu.add(refreshBtn);
        menu.add(addBtn);
        menu.add(updateBtn);
        menu.add(deleteBtn);
        menu.add(searchBtn);
        menu.add(avgBtn);

        frame.add(menu, BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        String[] cols = {"VIN","Make","Model","Year","Type","VehicleType","CostEstimate","Extras"};
        tableModel = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sc = new JScrollPane(table);
        mainPanel.add(sc, BorderLayout.CENTER);

        JTextArea details = new JTextArea(8,60);
        details.setEditable(false);
        mainPanel.add(new JScrollPane(details), BorderLayout.SOUTH);

        loadTable();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    String vin = (String)tableModel.getValueAt(r,0);
                    Vehicle v = Database.loadVehicle(vin);
                    details.setText(v == null ? "Not found" : v.displayMaintenanceDetails());
                }
            }
        });

        refreshBtn.addActionListener(e -> loadTable());

        addBtn.addActionListener(e -> showAddDialog());

        updateBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) { JOptionPane.showMessageDialog(frame, "Select a row first"); return; }
            String vin = (String) tableModel.getValueAt(r,0);
            Vehicle v = Database.loadVehicle(vin);
            if (v != null) showEditDialog(v);
            else JOptionPane.showMessageDialog(frame, "Could not load vehicle");
        });

        deleteBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) { JOptionPane.showMessageDialog(frame, "Select a row to delete"); return; }
            String vin = (String) tableModel.getValueAt(r,0);
            int confirm = JOptionPane.showConfirmDialog(frame, "Delete VIN " + vin + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = Database.deleteVehicle(vin);
                JOptionPane.showMessageDialog(frame, ok ? "Deleted" : "Delete failed");
                loadTable();
            }
        });

        searchBtn.addActionListener(e -> {
            String vin = JOptionPane.showInputDialog(frame, "Enter VIN to search:");
            if (vin != null && vin.trim().length()>0) {
                Vehicle v = Database.loadVehicle(vin.trim());
                if (v != null) {
                    JOptionPane.showMessageDialog(frame, v.displayMaintenanceDetails(), "Vehicle: "+vin, JOptionPane.INFORMATION_MESSAGE);
                } else JOptionPane.showMessageDialog(frame, "No vehicle with VIN: " + vin);
            }
        });

        avgBtn.addActionListener(e -> {
            String[] types = {"Car","Truck","Motorcycle"};
            String type = (String) JOptionPane.showInputDialog(frame, "Choose type", "Average cost", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
            if (type != null) {
                double avg = Database.averageCostByType(type);
                JOptionPane.showMessageDialog(frame, String.format("Average maintenance estimate for %s: $%.2f", type, avg));
            }
        });

        frame.setVisible(true);
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Vehicle> list = Database.loadAllVehicles();
        for (Vehicle v : list) {
            String extras = "";
            if (v instanceof Car) {
                Car c = (Car)v;
                extras = String.format("Doors:%d Oil:$%.2f", c.getNumberOfDoors(), c.getOilChangeCost());
            } else if (v instanceof Truck) {
                Truck t = (Truck)v;
                extras = String.format("MaxLoad:%.1f Cargo:$%.2f", t.getMaxLoad(), t.getCargoInspectionCost());
            } else if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle)v;
                extras = String.format("Chain:%s Replace:$%.2f", m.getChainCondition(), m.getChainReplacementCost());
            }
            tableModel.addRow(new Object[]{
                    v.getVin(), v.getMake(), v.getModel(), v.getYear(), v.getType(), v.getVehicleType(), v.getCostEstimate(), extras
            });
        }
    }

    private void showAddDialog() {
        VehicleForm form = new VehicleForm(frame, null);
        form.setVisible(true);
        if (form.isSaved()) {
            loadTable();
        }
    }

    private void showEditDialog(Vehicle v) {
        VehicleForm form = new VehicleForm(frame, v);
        form.setVisible(true);
        if (form.isSaved()) loadTable();
    }
}