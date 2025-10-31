package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfempid, tfname, tffather, tfsalary, tfphone, tfemail, tfdob;
    JButton search, update, back;
    ViewEmployee viewRef; // reference to ViewEmployee window

    public UpdateEmployee(ViewEmployee viewRef) {
        this.viewRef = viewRef;
        initUI();
    }

    public UpdateEmployee() {
        initUI();
    }

    private void initUI() {
        setTitle("Update Employee");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Update Employee Details");
        heading.setBounds(150, 20, 400, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(heading);

        JLabel lblempid = new JLabel("Employee ID:");
        lblempid.setBounds(50, 80, 100, 25);
        add(lblempid);

        tfempid = new JTextField();
        tfempid.setBounds(180, 80, 150, 25);
        add(tfempid);

        search = new JButton("Search");
        search.setBounds(350, 80, 100, 25);
        search.setBackground(new Color(0, 102, 204));
        search.setForeground(Color.WHITE);
        search.addActionListener(this);
        add(search);

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(50, 130, 100, 25);
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(180, 130, 250, 25);
        add(tfname);

        JLabel lblfather = new JLabel("Father's Name:");
        lblfather.setBounds(50, 170, 100, 25);
        add(lblfather);

        tffather = new JTextField();
        tffather.setBounds(180, 170, 250, 25);
        add(tffather);

        JLabel lblsalary = new JLabel("Salary:");
        lblsalary.setBounds(50, 210, 100, 25);
        add(lblsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(180, 210, 250, 25);
        add(tfsalary);

        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(50, 250, 100, 25);
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(180, 250, 250, 25);
        add(tfphone);

        JLabel lblemail = new JLabel("Email:");
        lblemail.setBounds(50, 290, 100, 25);
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(180, 290, 250, 25);
        add(tfemail);

        JLabel lbldob = new JLabel("DOB (YYYY-MM-DD):");
        lbldob.setBounds(50, 330, 150, 25);
        add(lbldob);

        tfdob = new JTextField();
        tfdob.setBounds(200, 330, 230, 25);
        add(tfdob);

        update = new JButton("Update");
        update.setBounds(150, 390, 120, 35);
        update.setBackground(new Color(0, 153, 51));
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(320, 390, 120, 35);
        back.setBackground(new Color(204, 0, 0));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(new Color(60, 63, 65));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            String empid = tfempid.getText().trim();
            if (empid.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an Employee ID");
                return;
            }
            try {
                conn c = new conn();
                String query = "SELECT * FROM employee WHERE empid=?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, empid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tffather.setText(rs.getString("fathername"));
                    tfsalary.setText(rs.getString("salary"));
                    tfphone.setText(rs.getString("phonenumber"));
                    tfemail.setText(rs.getString("email"));
                    tfdob.setText(rs.getString("dob"));
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found!");
                }
                rs.close();
                pst.close();
                c.c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        else if (e.getSource() == update) {
            String empid = tfempid.getText().trim();
            if (empid.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Search an Employee first!");
                return;
            }

            try {
                conn c = new conn();
                String query = "UPDATE employee SET name=?, fathername=?, salary=?, phonenumber=?, email=?, dob=? WHERE empid=?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, tfname.getText());
                pst.setString(2, tffather.getText());
                pst.setString(3, tfsalary.getText());
                pst.setString(4, tfphone.getText());
                pst.setString(5, tfemail.getText());
                pst.setString(6, tfdob.getText());
                pst.setString(7, empid);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Employee updated successfully!");
                    if (viewRef != null) {
                        viewRef.loadAllEmployees(); // Refresh table in ViewEmployee
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update employee!");
                }
                pst.close();
                c.c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        else if (e.getSource() == back) {
            this.dispose();
            if (viewRef != null) {
                viewRef.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee();
    }
}
