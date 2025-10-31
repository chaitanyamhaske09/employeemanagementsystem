package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    JTextField empIdField;
    JLabel nameLabel, phoneLabel, emailLabel;
    JButton searchButton, deleteButton, backButton;

    public RemoveEmployee() {
        setTitle("Remove Employee");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icon/rback.png"));
        Image bgImage = bgIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
        bgLabel.setBounds(0, 0, 800, 500);
        add(bgLabel);
        bgLabel.setLayout(null);

        // Panel for form
        JPanel panel = new JPanel();
        panel.setBounds(50, 50, 350, 350);
        panel.setBackground(new Color(0, 0, 0, 150));
        panel.setLayout(null);
        bgLabel.add(panel);

        JLabel title = new JLabel("REMOVE EMPLOYEE");
        title.setBounds(50, 10, 250, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // Employee ID
        JLabel empIdLabel = new JLabel("Employee ID:");
        empIdLabel.setBounds(20, 60, 100, 25);
        empIdLabel.setForeground(Color.WHITE);
        panel.add(empIdLabel);

        empIdField = new JTextField();
        empIdField.setBounds(130, 60, 150, 25);
        panel.add(empIdField);

        searchButton = new JButton("Search");
        searchButton.setBounds(100, 100, 120, 30);
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        panel.add(searchButton);

        // Display Labels
        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(20, 150, 300, 25);
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);

        phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBounds(20, 190, 300, 25);
        phoneLabel.setForeground(Color.WHITE);
        panel.add(phoneLabel);

        emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(20, 230, 300, 25);
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel);

        // Delete and Back Buttons
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(30, 280, 120, 35);
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        backButton = new JButton("Back");
        backButton.setBounds(180, 280, 120, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        panel.add(backButton);

        // Right side image
        ImageIcon rightIcon = new ImageIcon(ClassLoader.getSystemResource("icon/delete.png"));
        Image rightImg = rightIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        JLabel rightLabel = new JLabel(new ImageIcon(rightImg));
        rightLabel.setBounds(450, 50, 350, 350);
        bgLabel.add(rightLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String empId = empIdField.getText();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Employee ID");
                return;
            }
            try {
                conn c = new conn();
                String query = "SELECT * FROM employee WHERE empid=?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, empId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    nameLabel.setText("Name: " + rs.getString("name"));
                    phoneLabel.setText("Phone: " + rs.getString("phonenumber"));
                    emailLabel.setText("Email: " + rs.getString("email"));
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found!");
                    nameLabel.setText("Name: ");
                    phoneLabel.setText("Phone: ");
                    emailLabel.setText("Email: ");
                }

                rs.close();
                pst.close();
                c.c.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == deleteButton) {
            String empId = empIdField.getText();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Employee ID to delete");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    conn c = new conn();
                    String query = "DELETE FROM employee WHERE empid=?";
                    PreparedStatement pst = c.c.prepareStatement(query);
                    pst.setString(1, empId);
                    int result = pst.executeUpdate();

                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Employee deleted successfully!");
                        empIdField.setText("");
                        nameLabel.setText("Name: ");
                        phoneLabel.setText("Phone: ");
                        emailLabel.setText("Email: ");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee not found!");
                    }

                    pst.close();
                    c.c.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getSource() == backButton) {
            this.dispose();
            new dashboard();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}