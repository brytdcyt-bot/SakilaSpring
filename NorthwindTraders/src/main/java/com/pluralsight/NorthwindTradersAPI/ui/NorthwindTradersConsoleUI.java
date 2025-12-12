package com.pluralsight.NorthwindTradersAPI.ui;

import java.io.Console;

public class NorthwindTradersConsoleUI {

    /**
     * Prompts the user for DB credentials using the system console.
     * Falls back to standard input if a real console isn't available.
     */
    public static Credentials promptForCredentials() {
        Console console = System.console();

        String username;
        String password;

        if (console != null) {
            username = console.readLine("Enter database username: ");
            char[] pwdChars = console.readPassword("Enter database password: ");
            password = new String(pwdChars);
        } else {
            // IDEs (IntelliJ, VSCode) often return null for System.console()
            System.out.print("Enter database username: ");
            username = System.console() == null ? new java.util.Scanner(System.in).nextLine() : "";

            System.out.print("Enter database password: ");
            password = new java.util.Scanner(System.in).nextLine();
        }

        return new Credentials(username, password);
    }


    // Simple record-style container for username + password.
        public record Credentials(String username, String password) {
    }
}
