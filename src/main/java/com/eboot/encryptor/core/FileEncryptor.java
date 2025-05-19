package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.CryptoUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Handles the low-level logic for encrypting and decrypting file contents.
 */
public class FileEncryptor {

    private static final String ENCRYPTED_EXTENSION = ".enc";

    /**
     * Encrypts the content of the given file using AES encryption and writes it to a new file
     * with the original extension stored inside the encrypted file.
     *
     * @param inputFile Path to the input file to encrypt.
     * @param password  Password to derive the AES encryption key.
     * @throws Exception If encryption or file I/O fails.
     */
    public void encrypt(Path inputFile, String password) throws Exception {
        byte[] fileBytes = Files.readAllBytes(inputFile);
        byte[] encrypted = CryptoUtils.encrypt(fileBytes, password);

        // Extract original extension (including dot), or empty string if none
        String originalName = inputFile.getFileName().toString();
        int lastDotIndex = originalName.lastIndexOf('.');
        String originalExtension = (lastDotIndex == -1) ? "" : originalName.substring(lastDotIndex);

        // Convert extension to bytes
        byte[] extBytes = originalExtension.getBytes(StandardCharsets.UTF_8);
        int extLength = extBytes.length;
        if (extLength > 255) {
            throw new Exception("Original extension too long to store");
        }

        // Prepare output byte array: 1 byte for ext length + ext bytes + encrypted content
        byte[] outputBytes = new byte[1 + extLength + encrypted.length];
        outputBytes[0] = (byte) extLength;
        System.arraycopy(extBytes, 0, outputBytes, 1, extLength);
        System.arraycopy(encrypted, 0, outputBytes, 1 + extLength, encrypted.length);

        Path encryptedFile = getEncryptedFilePath(inputFile);
        Files.write(encryptedFile, outputBytes);
        Files.delete(inputFile);
    }

    /**
     * Decrypts the content of the given encrypted file and restores original extension.
     *
     * @param encryptedFile Path to the encrypted input file.
     * @param password      Password to derive the AES decryption key.
     * @throws Exception If decryption or file I/O fails.
     */
    public void decrypt(Path encryptedFile, String password) throws Exception {
        String fileName = encryptedFile.toString();
        if (!fileName.endsWith(ENCRYPTED_EXTENSION)) {
            throw new Exception("Encrypted file must have " + ENCRYPTED_EXTENSION + " extension.");
        }

        byte[] fileBytes = Files.readAllBytes(encryptedFile);

        int extLength = fileBytes[0] & 0xFF;
        byte[] extBytes = new byte[extLength];
        System.arraycopy(fileBytes, 1, extBytes, 0, extLength);
        String originalExtension = new String(extBytes, StandardCharsets.UTF_8);

        byte[] encryptedContent = new byte[fileBytes.length - 1 - extLength];
        System.arraycopy(fileBytes, 1 + extLength, encryptedContent, 0, encryptedContent.length);

        byte[] decrypted = CryptoUtils.decrypt(encryptedContent, password);

        Path decryptedFile = getDecryptedFilePath(encryptedFile, originalExtension);
        Files.write(decryptedFile, decrypted);
        Files.delete(encryptedFile);
    }

    private Path getEncryptedFilePath(Path originalFile) {
        String originalName = originalFile.getFileName().toString();
        int lastDotIndex = originalName.lastIndexOf('.');
        String baseName = (lastDotIndex == -1) ? originalName : originalName.substring(0, lastDotIndex);
        return originalFile.resolveSibling(baseName + ENCRYPTED_EXTENSION);
    }

    private Path getDecryptedFilePath(Path encryptedFile, String originalExtension) {
        String encryptedName = encryptedFile.getFileName().toString();
        if (encryptedName.endsWith(ENCRYPTED_EXTENSION)) {
            String baseName = encryptedName.substring(0, encryptedName.length() - ENCRYPTED_EXTENSION.length());
            return encryptedFile.resolveSibling(baseName + originalExtension);
        }
        return encryptedFile;
    }
}
