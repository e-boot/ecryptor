package com.eboot.encryptor.utils;

import java.io.Console;
import java.util.Scanner;
/**
 * A utility class for securely reading passwords from the user.
 * It supports both secure console input (no echo) and fallback to standard input.
 */
public class PasswordReader {

    private final Console console;
    private final Scanner scanner;


    public PasswordReader(Console console, Scanner scanner){
        this.console = console;
        this.scanner = scanner;
    }
    /**
     * Prompts the user for input using a secure password input (no echo) if available.
     * Falls back to standard input if the console is not available (e.g., in IDEs).
*/
    public  String prompt(String message) {

        if (console != null) {
            char[] pwd = console.readPassword(message);
            return new String(pwd).trim();
        } else {
            System.out.print(message);
            return scanner.nextLine();
        }
    }

    public String readAndConfirmPassword() {

        for(int i = 0; i< 3; i++ ) {
            String pwd1 = prompt(Messages.ENTER_PASSWORD);
            String pwd2 = prompt(Messages.CONFIRM_PASSWORD);
            if (pwd1.equals(pwd2)) return pwd1;
            System.out.println(Messages.PASSWORD_DONT_MATCH);
        }
        System.out.println(Messages.TOO_MANY_ATTEMPTS);
        return null;

    }
}
