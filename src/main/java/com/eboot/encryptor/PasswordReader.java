package com.eboot.encryptor;

import java.io.Console;
import java.util.Scanner;

public class PasswordReader {
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
