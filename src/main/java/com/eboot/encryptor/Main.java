package com.eboot.encryptor;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        try {
            ArgumentParser parser = new ArgumentParser(args);
            String mode = parser.getMode();
            String inputPath = parser.getInputPath();
            String outputPath = parser.getOutputPath();

            CommandHandler handler = new CommandHandler();


            switch (mode){
                case "encrypt":
                    String password = PasswordReader.getConfirmedPassword();
                    if(password == null) return;
                    handler.encryptFile(inputPath, outputPath, password);
                    System.out.println("Encryption complete.");
                    break;

                case "decrypt":
                    String pwd = PasswordReader.prompt("Enter password: ");
                    handler.decryptFile(inputPath, outputPath, pwd);
                    break;

                default:
                    System.out.println("Unkown mode: " + mode);
                    showHelp();
            }


        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            showHelp();
        }
    }








    private static void showHelp() {
        System.out.println("Usage: java -jar app.jar <encrypt|decrypt> -in <input_file> -out <output_file>");
    }
}
