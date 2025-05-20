package com.eboot.encryptor.cli;

import com.eboot.encryptor.service.CommandHandler;
import java.nio.file.Path;

/**
 * Entry point for the file encryption/decryption CLI application.
 * Usage:
 *   java -jar app.jar encrypt -in input.txt -out encrypted.txt
 *   java -jar app.jar decrypt -in encrypted.txt -out decrypted.txt
 */
public class Main {
    public static void main(String[] args) {


        try {
            ArgumentParser parser = new ArgumentParser(args);
            String mode = parser.getMode();
            Path inputPath = parser.getInputPath();

            CommandHandler handler = new CommandHandler();


            switch (mode){
                case "encrypt":
                    String password = PasswordReader.getConfirmedPassword();
                    if(password == null) return;
                    handler.encryptFile(inputPath, password);
                    System.out.println("Encryption complete.");
                    break;

                case "decrypt":
                    String pwd = PasswordReader.prompt("Enter password: ");
                    handler.decryptFile(inputPath, pwd);
                    break;

                default:
                    System.out.println("Unknown mode: " + mode);
                    showHelp();
            }


        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            showHelp();
        }
    }


    /**
     * Prints help/usage instructions for the CLI.
     */
    private static void showHelp() {
        System.out.println("Usage: java -jar app.jar <encrypt|decrypt> -in <input_file> -out <output_file>");
    }
}
