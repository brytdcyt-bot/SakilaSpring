package com.pluralsight.NorthwindTradersAPI.ui;

import java.io.Console;
import java.util.Scanner;

public class CredentialUI {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main entry for prompting user credentials.
     */
    public static Credentials prompt() {
        System.out.println("=== Northwind Traders API ===");
        System.out.println("Database Credentials Required\n");

        String username = promptUsername();
        String password = promptPassword();
        String profile  = promptProfile();

        return new Credentials(username, password, profile);
    }

    private static String promptUsername() {
        String username;

        while (true) {
            System.out.print("Enter database username: ");
            username = scanner.nextLine().trim();

            if (!username.isEmpty()) break;

            System.out.println("⚠ Username cannot be empty.\n");
        }

        return username;
    }

    private static String promptPassword() {
        String password;
        Console console = System.console();

        while (true) {
            if (console != null) {
                char[] pwdChars = console.readPassword("Enter database password: ");
                password = new String(pwdChars);
            } else {
                System.out.print("Enter database password: ");
                password = scanner.nextLine().trim(); // fallback for IDEs
            }

            if (!password.isEmpty()) break;

            System.out.println("⚠ Password cannot be empty.\n");
        }

        return password;
    }

    private static String promptProfile() {
        String profile;

        while (true) {
            System.out.print("\nSelect profile [dev / prod]: ");
            profile = scanner.nextLine().trim().toLowerCase();

            if (profile.equals("dev") || profile.equals("prod")) break;

            System.out.println("⚠ Invalid input. Type 'dev' or 'prod'.\n");
        }

        return profile;
    }

    /**
         * Record-style credential container.
         */
        public record Credentials(String username, String password, String profile) {
    }
}