package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.CryptoUtils;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Handles the low-level logic for encrypting and decrypting file contents.
 */
public class FileEncryptor {

    /**
     * Encrypts the content of the given file using AES encryption and writes it to outputFile.
     *
     * @param inputFile  Path to the input file to encrypt.
     * @param outputFile Path where the encrypted file will be written.
     * @param password   Password to derive the AES encryption key.
     * @throws Exception If encryption or file I/O fails.
     */
    public void encrypt(Path inputFile, Path outputFile, String password) throws Exception{
        byte[] fileBytes = Files.readAllBytes(inputFile);
        byte[] encrypted = CryptoUtils.encrypt(fileBytes, password);
        Files.write(outputFile ,encrypted);
    }

    /**
     * Decrypts the content of the given file using AES decryption and writes it to outputFile.
     *
     * @param inputFile  Path to the encrypted input file.
     * @param outputFile Path where the decrypted file will be written.
     * @param password   Password to derive the AES decryption key.
     * @throws Exception If decryption or file I/O fails.
     */
    public void decrypt(Path inputFile, Path outputFile, String password) throws Exception{
      byte[] fileBytes = Files.readAllBytes(inputFile);
      byte[] decrypted = CryptoUtils.decrypt(fileBytes, password);
      Files.write(outputFile, decrypted);
    }

}
