package com.eboot.encryptor;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class CommandHandler {

    // Method to run the program interactively with the user
    public void runInteractive() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt user for action (encrypt or decrypt)
            System.out.print("Do you want to encrypt or decrypt a file? (e/d): ");
            String action = scanner.nextLine().trim().toLowerCase();

            // Prompt user for file paths
            System.out.print("Enter input file path: ");
            String inputPath = scanner.nextLine().trim();

            System.out.print("Enter output file path: ");
            String outputPath = scanner.nextLine().trim();

            // Prompt user for password
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Generate key from password
            SecretKey key = CryptoUtils.getKeyFromPassword(password);

            // Read input file
            byte[] inputBytes = Files.readAllBytes(new File(inputPath).toPath());

            // Encrypt or decrypt the file based on user's action
            byte[] outputBytes;
            if (action.equals("e")) {
                outputBytes = CryptoUtils.process(javax.crypto.Cipher.ENCRYPT_MODE, key, inputBytes);
                System.out.println("File encrypted successfully.");
            } else if (action.equals("d")) {
                outputBytes = CryptoUtils.process(javax.crypto.Cipher.DECRYPT_MODE, key, inputBytes);
                System.out.println("File decrypted successfully.");
            } else {
                System.out.println("Invalid option. Please use 'e' for encrypt or 'd' for decrypt.");
                return;
            }

            // Write the output bytes to the output file
            Files.write(new File(outputPath).toPath(), outputBytes);
            System.out.println("Output written to: " + outputPath);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Close scanner
        scanner.close();
    }
}
