package employee.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTextField empIdField;
    JButton searchButton, showAllButton, updateButton, printButton, backButton;
    JTable table;
    DefaultTableModel model;

    public ViewEmployee() {
        setTitle("View Employees");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel
        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 860, 430);
        panel.setLayout(null);
        panel.setBackground(new Color(0, 0, 0, 150));
        add(panel);

        // Heading
        JLabel heading = new JLabel("Employee Records");
        heading.setBounds(300, 10, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        panel.add(heading);

        // Employee ID search
        JLabel empIdLabel = new JLabel("Search by Employee ID:");
        empIdLabel.setBounds(20, 50, 180, 25);
        empIdLabel.setForeground(Color.WHITE);
        panel.add(empIdLabel);

        empIdField = new JTextField();
        empIdField.setBounds(200, 50, 150, 25);
        panel.add(empIdField);

        searchButton = new JButton("Search");
        searchButton.setBounds(370, 50, 100, 25);
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        panel.add(searchButton);

        // Show All button
        showAllButton = new JButton("Show All");
        showAllButton.setBounds(500, 50, 120, 25);
        showAllButton.setBackground(new Color(0, 102, 204));
        showAllButton.setForeground(Color.WHITE);
        showAllButton.addActionListener(e -> loadAllEmployees());
        panel.add(showAllButton);

        // Table to display employees
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Employee ID");
        model.addColumn("Name");
        model.addColumn("DOB");
        model.addColumn("Father's Name");
        model.addColumn("Salary");
        model.addColumn("Phone");
        model.addColumn("Email");

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 90, 820, 250);
        panel.add(scroll);

        // Buttons
        updateButton = new JButton("Update");
        updateButton.setBounds(150, 360, 120, 35);
        updateButton.setBackground(new Color(0, 102, 204));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(this);
        panel.add(updateButton);

        printButton = new JButton("Print");
        printButton.setBounds(300, 360, 120, 35);
        printButton.setBackground(new Color(0, 102, 204));
        printButton.setForeground(Color.WHITE);
        printButton.addActionListener(this);
        panel.add(printButton);

        backButton = new JButton("Back");
        backButton.setBounds(450, 360, 120, 35);
        backButton.setBackground(new Color(204, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        panel.add(backButton);

        // Load all employees initially
        loadAllEmployees();

        setVisible(true);
    }

    private void loadAllEmployees() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM employee";
            ResultSet rs = c.s.executeQuery(query);
            model.setRowCount(0); // clear existing rows

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
            e.printStackTrace();
        }
    }

    private void searchEmployeeById(String empId) {
        try {
            conn c = new conn();
            String query = "SELECT * FROM employee WHERE empid=?";
            PreparedStatement pst = c.c.prepareStatement(query);
            pst.setString(1, empId);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0); // clear existing rows
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
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String empId = empIdField.getText();
            if (!empId.isEmpty()) {
                searchEmployeeById(empId);
            } else {
                loadAllEmployees(); // if empty, show all
            }
        } else if (e.getSource() == updateButton) {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try {
                    String empid = table.getValueAt(row, 0).toString();
                    String name = table.getValueAt(row, 1).toString();
                    String dob = table.getValueAt(row, 2).toString();
                    String father = table.getValueAt(row, 3).toString();
                    String salary = table.getValueAt(row, 4).toString();
                    String phone = table.getValueAt(row, 5).toString();
                    String email = table.getValueAt(row, 6).toString();

                    conn c = new conn();
                    String query = "UPDATE employee SET name=?, dob=?, fathername=?, salary=?, phonenumber=?, email=? WHERE empid=?";
                    PreparedStatement pst = c.c.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, dob);
                    pst.setString(3, father);
                    pst.setString(4, salary);
                    pst.setString(5, phone);
                    pst.setString(6, email);
                    pst.setString(7, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Employee updated successfully!");
                    loadAllEmployees();

                    pst.close();
                    c.c.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select an employee from table to update!");
            }

        } else if (e.getSource() == printButton) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == backButton) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}