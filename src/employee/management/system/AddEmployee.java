package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployee extends JFrame implements ActionListener {

    JTextField nameField, dobField, fatherNameField, salaryField, phoneField, emailField, empIdField;
    JButton submitButton, cancelButton;

    AddEmployee() {
        setTitle("Add Employee");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for form
        JPanel formPanel = new JPanel();
        formPanel.setBounds(50, 50, 500, 400);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(0, 0, 0, 150));
        add(formPanel);

        // Heading
        JLabel heading = new JLabel("Add Employee");
        heading.setBounds(150, 10, 200, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        formPanel.add(heading);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 120, 25);
        nameLabel.setForeground(Color.WHITE);
        formPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 70, 250, 25);
        formPanel.add(nameField);

        // DOB
        JLabel dobLabel = new JLabel("DOB (YYYY-MM-DD):");
        dobLabel.setBounds(50, 110, 120, 25);
        dobLabel.setForeground(Color.WHITE);
        formPanel.add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(180, 110, 250, 25);
        formPanel.add(dobField);

        // Father's Name
        JLabel fatherLabel = new JLabel("Father's Name:");
        fatherLabel.setBounds(50, 150, 120, 25);
        fatherLabel.setForeground(Color.WHITE);
        formPanel.add(fatherLabel);

        fatherNameField = new JTextField();
        fatherNameField.setBounds(180, 150, 250, 25);
        formPanel.add(fatherNameField);

        // Salary
        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(50, 190, 120, 25);
        salaryLabel.setForeground(Color.WHITE);
        formPanel.add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(180, 190, 250, 25);
        formPanel.add(salaryField);

        // Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 230, 120, 25);
        phoneLabel.setForeground(Color.WHITE);
        formPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(180, 230, 250, 25);
        formPanel.add(phoneField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 270, 120, 25);
        emailLabel.setForeground(Color.WHITE);
        formPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 270, 250, 25);
        formPanel.add(emailField);

        // Employee ID
        JLabel empIdLabel = new JLabel("Employee ID:");
        empIdLabel.setBounds(50, 310, 120, 25);
        empIdLabel.setForeground(Color.WHITE);
        formPanel.add(empIdLabel);

        empIdField = new JTextField();
        empIdField.setBounds(180, 310, 250, 25);
        formPanel.add(empIdField);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 360, 120, 35);
        submitButton.setBackground(new Color(0, 102, 204));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        formPanel.add(submitButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(260, 360, 120, 35);
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        formPanel.add(cancelButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText();
            String dob = dobField.getText();
            String father = fatherNameField.getText();
            String salary = salaryField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String empId = empIdField.getText();

            try {
                conn c = new conn();
                String query = "INSERT INTO employee(empid, name, dob, fathername, salary, phonenumber, email) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, empId);
                pst.setString(2, name);
                pst.setString(3, dob);
                pst.setString(4, father);
                pst.setString(5, salary);
                pst.setString(6, phone);
                pst.setString(7, email);

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Employee added successfully!");
                    this.dispose(); // close form
                }

                pst.close();
                c.c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }

        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}