package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, backButton;

    Login() {
        // Frame settings
        setTitle("Employee Login");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icon/LoginB.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
        bgLabel.setBounds(0, 0, 800, 500);
        add(bgLabel);
        bgLabel.setLayout(null);
        
        ImageIcon userIcon = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image userImg = userIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        JLabel userLabel = new JLabel(new ImageIcon(userImg));
        userLabel.setBounds(470, 80, 800, 550);
        bgLabel.add(userLabel);

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(50, 100, 350, 300);
        loginPanel.setBackground(new Color(0, 0, 0, 150));
        loginPanel.setLayout(null);
        bgLabel.add(loginPanel);

        // Title
        JLabel titleLabel = new JLabel("EMPLOYEE LOGIN");
        titleLabel.setBounds(50, 10, 250, 40);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        loginPanel.add(titleLabel);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 70, 100, 25);
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(140, 70, 160, 25);
        loginPanel.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 120, 100, 25);
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 120, 160, 25);
        loginPanel.add(passwordField);

        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setBounds(30, 180, 130, 35);
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);

        // Back Button
        backButton = new JButton("BACK");
        backButton.setBounds(170, 180, 130, 35);
        backButton.setBackground(new Color(204, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        loginPanel.add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                conn c = new conn(); // database connection
                String query = "SELECT * FROM login WHERE username=? AND password=?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    // Login successful, open dashboard
                    new dashboard();
                    this.dispose(); // close login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                pst.close();
                c.c.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == backButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}