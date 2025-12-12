package com.pluralsight.NorthwindTradersAPI.ui.swing;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<String> profileBox = new JComboBox<>(new String[] {"dev", "prod"});

    public LoginFrame() {
        setTitle("Northwind Traders – Login");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());
        add(buildCard(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel buildCard() {
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Northwind Traders", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        card.add(title, gbc);

        gbc.gridy++;
        card.add(new JLabel("Username:"), gbc);

        gbc.gridy++;
        card.add(usernameField, gbc);

        gbc.gridy++;
        card.add(new JLabel("Password:"), gbc);

        gbc.gridy++;
        card.add(passwordField, gbc);

        gbc.gridy++;
        card.add(new JLabel("Profile:"), gbc);

        gbc.gridy++;
        card.add(profileBox, gbc);

        gbc.gridy++;
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> attemptLogin());
        card.add(loginBtn, gbc);

        return card;
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String profile  = profileBox.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username and password cannot be empty.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pass credentials to the Spring environment
        System.setProperty("dbUsername", username);
        System.setProperty("dbPassword", password);
        System.setProperty("spring.profiles.active", profile);

        // Start Spring Boot
        new Thread(() -> {
            com.pluralsight.NorthwindTradersAPI.NorthwindTradersApiApplication.main(new String[]{});
        }).start();

        // Close the login window
        dispose();
    }
}