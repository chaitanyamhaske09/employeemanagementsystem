package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class dashboard extends JFrame implements ActionListener {

    JButton addEmployeeButton, viewEmployeeButton, updateEmployeeButton, removeEmployeeButton;

    public dashboard() {
        // Frame settings
        setTitle("Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icon/home.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1120, 630, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
        bgLabel.setBounds(0, 0, 1120, 600);
        add(bgLabel);

        // Heading
        JLabel heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        heading.setBounds(200, 20, 600, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        bgLabel.add(heading);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 20)); // 4 buttons now
        buttonPanel.setBounds(50, 150, 250, 260);
        buttonPanel.setOpaque(false);
        bgLabel.add(buttonPanel);

        // Add Employee Button
        addEmployeeButton = new JButton("Add Employee");
        styleButton(addEmployeeButton, new Color(0, 102, 204));
        buttonPanel.add(addEmployeeButton);

        // View Employee Button
        viewEmployeeButton = new JButton("View Employee");
        styleButton(viewEmployeeButton, new Color(0, 102, 204));
        buttonPanel.add(viewEmployeeButton);

        // 🔹 New Update Employee Button
        updateEmployeeButton = new JButton("Update Employee");
        styleButton(updateEmployeeButton, new Color(0, 153, 51));
        buttonPanel.add(updateEmployeeButton);

        // Remove Employee Button
        removeEmployeeButton = new JButton("Remove Employee");
        styleButton(removeEmployeeButton, new Color(204, 0, 0));
        buttonPanel.add(removeEmployeeButton);

        // Action Listeners
        addEmployeeButton.addActionListener(this);
        viewEmployeeButton.addActionListener(this);
        updateEmployeeButton.addActionListener(this);
        removeEmployeeButton.addActionListener(this);

        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addEmployeeButton) {
            new AddEmployee(); // Open AddEmployee form
        } else if (e.getSource() == viewEmployeeButton) {
            new ViewEmployee(); // Open ViewEmployee window
        } else if (e.getSource() == updateEmployeeButton) {
            new UpdateEmployee(); // 🔹 Opens UpdateEmployee window
        } else if (e.getSource() == removeEmployeeButton) {
            dispose(); // Close dashboard
            new RemoveEmployee(); // Open RemoveEmployee window
        }
    }

    public static void main(String[] args) {
        new dashboard();
    }
}
