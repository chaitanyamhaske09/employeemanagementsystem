package employee.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewEmployee extends JFrame implements ActionListener {

    private JTextField empIdField;
    private JButton searchButton, showAllButton, backButton;
    private JTable table;
    private DefaultTableModel model;

    public ViewEmployee() {
        // Frame setup
        setTitle("View Employees");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Background panel
        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 910, 490);
        panel.setLayout(null);
        panel.setBackground(new Color(45, 45, 48));
        add(panel);

        // Heading
        JLabel heading = new JLabel("Employee Records");
        heading.setBounds(320, 10, 400, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        panel.add(heading);

        // Search Section
        JLabel empIdLabel = new JLabel("Search by Employee ID:");
        empIdLabel.setBounds(30, 60, 200, 25);
        empIdLabel.setForeground(Color.WHITE);
        empIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(empIdLabel);

        empIdField = new JTextField();
        empIdField.setBounds(210, 60, 160, 25);
        empIdField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(empIdField);

        searchButton = new JButton("Search");
        searchButton.setBounds(390, 60, 100, 25);
        styleButton(searchButton, new Color(0, 120, 215));
        searchButton.addActionListener(this);
        panel.add(searchButton);

        showAllButton = new JButton("Show All");
        showAllButton.setBounds(510, 60, 120, 25);
        styleButton(showAllButton, new Color(0, 120, 215));
        showAllButton.addActionListener(e -> loadAllEmployees());
        panel.add(showAllButton);

        // Table setup
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells non-editable now
            }
        };

        model.addColumn("Employee ID");
        model.addColumn("Name");
        model.addColumn("DOB");
        model.addColumn("Father's Name");
        model.addColumn("Salary");
        model.addColumn("Phone");
        model.addColumn("Email");

        table = new JTable(model);
        table.setFont(new Font("Tahoma", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setBackground(new Color(245, 245, 245));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 100, 850, 300);
        panel.add(scroll);

        // Back Button
        backButton = new JButton("Back to Dashboard");
        backButton.setBounds(370, 420, 200, 35);
        styleButton(backButton, new Color(204, 0, 0));
        backButton.addActionListener(this);
        panel.add(backButton);

        // Load all employees initially
        loadAllEmployees();

        setVisible(true);
    }

    // Button styling helper
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Load all employees
    public void loadAllEmployees() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM employee";
            ResultSet rs = c.s.executeQuery(query);

            model.setRowCount(0); // clear table

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("empid"),
                        rs.getString("name"),
                        rs.getString("dob"),
                        rs.getString("fathername"),
                        rs.getString("salary"),
                        rs.getString("phonenumber"),
                        rs.getString("email")
                });
            }

            rs.close();
            c.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private void searchEmployeeById(String empId) {
        try {
            conn c = new conn();
            String query = "SELECT * FROM employee WHERE empid=?";
            PreparedStatement pst = c.c.prepareStatement(query);
            pst.setString(1, empId);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);
            if (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("empid"),
                        rs.getString("name"),
                        rs.getString("dob"),
                        rs.getString("fathername"),
                        rs.getString("salary"),
                        rs.getString("phonenumber"),
                        rs.getString("email")
                });
            } else {
                JOptionPane.showMessageDialog(null, "Employee not found!");
            }

            rs.close();
            pst.close();
            c.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while searching: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == searchButton) {
            String empId = empIdField.getText().trim();
            if (!empId.isEmpty()) {
                searchEmployeeById(empId);
            } else {
                loadAllEmployees();
            }
        } else if (src == backButton) {
            dispose();
            new dashboard(); // Go back to dashboard
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
