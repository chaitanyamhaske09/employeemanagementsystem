package employee.management.system;

import java.sql.*;

public class conn {
    Connection c;
    Statement s;

    public conn() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employeemanagement", "root", "jack"); // replace "" with your MySQL password

            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}