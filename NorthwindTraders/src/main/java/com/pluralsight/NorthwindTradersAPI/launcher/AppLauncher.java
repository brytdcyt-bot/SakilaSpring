package com.pluralsight.NorthwindTradersAPI.launcher;

import com.pluralsight.NorthwindTradersAPI.ui.swing.LoginFrame;

import javax.swing.SwingUtilities;

public class AppLauncher {

    public static void main(String[] args) {
        // Launch Swing UI on the Event Dispatch Thread (EDT) to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

}