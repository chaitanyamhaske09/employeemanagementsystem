package employee.management.system;

import javax.swing.*;


public class Splash extends JFrame {

    Splash() {
        // Load image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/front.gif"));
        JLabel imageLabel = new JLabel(i1);
        imageLabel.setBounds(0, 0, 800, 600); // Set fixed size for image label
        add(imageLabel);

        // Set frame properties
        setSize(800, 600); // Fixed width and height
        setLocation(320, 50);
        setLayout(null);
        setVisible(true);

        // Pause for 5 seconds
        try {

            Thread.sleep(5000);
            setVisible(false);
            new Login();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}