package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class dashboard extends JFrame implements ActionListener {

	JButton addEmployeeButton, viewEmployeeButton, removeEmployeeButton;

	dashboard() {
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

		// Heading label on top of image
		JLabel heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
		heading.setBounds(200, 20, 600, 50);
		heading.setFont(new Font("Tahoma", Font.BOLD, 32));
		heading.setForeground(Color.WHITE);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		bgLabel.add(heading);

		// Buttons panel (left side)
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 1, 10, 20)); // 3 rows, 1 column
		buttonPanel.setBounds(50, 150, 200, 200);
		buttonPanel.setOpaque(false); // transparent so background shows
		bgLabel.add(buttonPanel);

		// Add Employee Button
		addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		addEmployeeButton.setBackground(new Color(0, 102, 204));
		addEmployeeButton.setForeground(Color.WHITE);
		addEmployeeButton.setFocusPainted(false);
		addEmployeeButton.addActionListener(this);
		buttonPanel.add(addEmployeeButton);

		// View Employee Button
		viewEmployeeButton = new JButton("View Employee");
		viewEmployeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		viewEmployeeButton.setBackground(new Color(0, 102, 204));
		viewEmployeeButton.setForeground(Color.WHITE);
		viewEmployeeButton.setFocusPainted(false);
		viewEmployeeButton.addActionListener(this);
		buttonPanel.add(viewEmployeeButton);

		// Remove Employee Button
		removeEmployeeButton = new JButton("Remove Employee");
		removeEmployeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		removeEmployeeButton.setBackground(new Color(204, 0, 0));
		removeEmployeeButton.setForeground(Color.WHITE);
		removeEmployeeButton.setFocusPainted(false);
		removeEmployeeButton.addActionListener(this);
		buttonPanel.add(removeEmployeeButton);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addEmployeeButton) {
			new AddEmployee(); // opens AddEmployee form
		}
		if (e.getSource() == viewEmployeeButton) {
			new ViewEmployee(); // opens the view employee form
		} else if (e.getSource() == removeEmployeeButton) {
		    dispose();               // Close Dashboard
		    new RemoveEmployee();    // Open RemoveEmployee window
		}

	}

	public static void main(String[] args) {
		new dashboard();
	}
}