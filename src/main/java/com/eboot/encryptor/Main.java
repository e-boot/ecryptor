package com.eboot.encryptor;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length != 5) {
            showHelp();
            return;
        }


        String mode = args[0];
        String inputPath = null;
        String outputPath = null;


        for( int i = 1; i < args.length; i++){

            switch (args[i]){
                case "-in":
                    inputPath = args[++i];
                    break;
                case "-out":
                    outputPath = args[++i];
                    break;
                default:
                    showHelp();
                    return;
            }
        }


        Scanner scanner = new Scanner(System.in);
        try {
            CommandHandler handler = new CommandHandler();
            if(mode.equalsIgnoreCase("encrypt")){
                System.out.println("Enter password: ");
                String pwd1 = scanner.nextLine();
                System.out.println("Confirm password: ");
                String pwd2 = scanner.nextLine();

                if(!pwd1.equals(pwd2)){
                    System.out.println("Passwords don't match");
                    return;
                }

                handler.encryptFile(inputPath,outputPath,pwd1);
                System.out.println("Encryption complete.");
            } else if (mode.equals("decrypt")) {
                System.out.println("Enter password: ");
                String pwd = scanner.nextLine();
                handler.decryptFile(inputPath,outputPath,pwd);
                System.out.println("Decryption complete.");
            } else {
                System.out.println("Unknown mode: " + mode);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }








    private static void showHelp() {
        System.out.println("Usage: java -jar app.jar <encrypt|decrypt> -in <input_file> -out <output_file>");
    }
}
