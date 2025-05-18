package com.eboot.encryptor.cli;

import java.io.Console;
import java.util.Scanner;
/**
 * A utility class for securely reading passwords from the user.
 * It supports both secure console input (no echo) and fallback to standard input.
 */
public class PasswordReader {

    /**
     * Prompts the user for input using a secure password input (no echo) if available.
     * Falls back to standard input if the console is not available (e.g., in IDEs).
     *
     * @param message The prompt message to display to the user.
     * @return The password (or input) entered by the user.
     */
    public static String prompt(String message) {
        Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword(message);
            return new String(pwd);
        } else {
            System.out.print(message);
            return new Scanner(System.in).nextLine();
        }
    }
    /**
     * Prompts the user to enter and confirm a password.
     * If both entries match, the password is returned; otherwise, returns null.
     *
     * @return The confirmed password or null if passwords don't match.
     */
    public static String getConfirmedPassword() {
        String pwd1 = prompt("Enter password: ");
        String pwd2 = prompt("Confirm password: ");
        if (!pwd1.equals(pwd2)) {
            System.out.println("Passwords don't match.");
            return null;
        }
        return pwd1;
    }
}
