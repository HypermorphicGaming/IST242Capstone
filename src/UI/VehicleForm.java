package UI;

import db.Database;
import vehicles.Car;
import vehicles.Motorcycle;
import vehicles.Truck;
import vehicles.Vehicle;
import javax.swing.*;
import java.awt.*;

public class VehicleForm extends JDialog {
    private boolean saved = false;

    private JTextField vinField = new JTextField(20);
    private JTextField makeField = new JTextField(20);
    private JTextField modelField = new JTextField(20);
    private JTextField yearField = new JTextField(6);
    private JComboBox<String> typeBox = new JComboBox<>(new String[]{"Car","Truck","Motorcycle"});
    private JTextField vehicleTypeField = new JTextField(12);
    private JTextField costEstimateField = new JTextField(8);

    private JTextField doorsField = new JTextField(4);
    private JTextField oilCostField = new JTextField(6);
    private JTextField maxLoadField = new JTextField(6);
    private JTextField cargoCostField = new JTextField(6);
    private JTextField chainCondField = new JTextField(10);
    private JTextField chainCostField = new JTextField(6);

    public VehicleForm(Frame owner, Vehicle edit) {
        super(owner, true);
        setTitle(edit == null ? "Add Vehicle" : "Edit Vehicle");
        setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.add(new JLabel("VIN:")); p.add(vinField);
        p.add(new JLabel("Make:")); p.add(makeField);
        p.add(new JLabel("Model:")); p.add(modelField);
        p.add(new JLabel("Year:")); p.add(yearField);
        p.add(new JLabel("Type:")); p.add(typeBox);
        p.add(new JLabel("Vehicle Type:")); p.add(vehicleTypeField);
        p.add(new JLabel("Cost Estimate:")); p.add(costEstimateField);

        p.add(new JLabel("Car - Doors:")); p.add(doorsField);
        p.add(new JLabel("Car - Oil Cost:")); p.add(oilCostField);
        p.add(new JLabel("Truck - Max Load:")); p.add(maxLoadField);
        p.add(new JLabel("Truck - Cargo Cost:")); p.add(cargoCostField);
        p.add(new JLabel("Motorcycle - Chain Condition:")); p.add(chainCondField);
        p.add(new JLabel("Motorcycle - Chain Replace Cost:")); p.add(chainCostField);

        add(p, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        btns.add(save); btns.add(cancel);
        add(btns, BorderLayout.SOUTH);


        if (edit != null) {
            vinField.setText(edit.getVin()); vinField.setEditable(false);
            makeField.setText(edit.getMake());
            modelField.setText(edit.getModel());
            yearField.setText(String.valueOf(edit.getYear()));
            typeBox.setSelectedItem(edit.getType());
            vehicleTypeField.setText(edit.getVehicleType());
            costEstimateField.setText(String.valueOf(edit.getCostEstimate()));
            if (edit instanceof Car) {
                Car c = (Car) edit;
                doorsField.setText(String.valueOf(c.getNumberOfDoors()));
                oilCostField.setText(String.valueOf(c.getOilChangeCost()));
            } else if (edit instanceof Truck) {
                Truck t = (Truck) edit;
                maxLoadField.setText(String.valueOf(t.getMaxLoad()));
                cargoCostField.setText(String.valueOf(t.getCargoInspectionCost()));
            } else if (edit instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) edit;
                chainCondField.setText(m.getChainCondition());
                chainCostField.setText(String.valueOf(m.getChainReplacementCost()));
            }
        }

        save.addActionListener(e -> {
            try {
                String vin = vinField.getText().trim();
                String make = makeField.getText().trim();
                String model = modelField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                String type = (String) typeBox.getSelectedItem();
                String vehicleType = vehicleTypeField.getText().trim();
                double cost = Double.parseDouble(costEstimateField.getText().trim());

                Vehicle v;
                if ("Car".equalsIgnoreCase(type)) {
                    int doors = doorsField.getText().trim().isEmpty() ? 4 : Integer.parseInt(doorsField.getText().trim());
                    double oil = oilCostField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(oilCostField.getText().trim());
                    v = new Car(vin, make, model, year, type, vehicleType, cost, doors, oil);
                } else if ("Truck".equalsIgnoreCase(type)) {
                    double maxLoad = maxLoadField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(maxLoadField.getText().trim());
                    double cargo = cargoCostField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(cargoCostField.getText().trim());
                    v = new Truck(vin, make, model, year, type, vehicleType, cost, maxLoad, cargo);
                } else {
                    String chainCond = chainCondField.getText().trim().isEmpty() ? "OK" : chainCondField.getText().trim();
                    double chainCost = chainCostField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(chainCostField.getText().trim());
                    v = new Motorcycle(vin, make, model, year, type, vehicleType, cost, chainCond, chainCost);
                }

                boolean ok = Database.upsertVehicle(v);
                if (ok) {
                    saved = true;
                    JOptionPane.showMessageDialog(this, "Saved successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Save failed; check console");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Numeric fields must be valid numbers");
            }
        });

        cancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isSaved() { return saved; }
}