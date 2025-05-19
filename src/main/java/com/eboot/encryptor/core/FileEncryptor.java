package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.CryptoUtils;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptor {

    private static final String ENCRYPTED_EXTENSION = ".enc";


    public void encrypt(Path inputFile, String password) throws Exception{
        byte[] fileBytes = Files.readAllBytes(inputFile);

        SecretKey key = CryptoUtils.getKeyFromPassword(password);
        byte[] encrypted = CryptoUtils.encrypt(fileBytes,key);


        //
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
        outputBytes[0] = (byte) extLength; // Store length of extension in first byte
        System.arraycopy(extBytes, 0, outputBytes, 1, extLength); // Store extension bytes
        System.arraycopy(encrypted, 0, outputBytes, 1 + extLength, encrypted.length); // Store encrypted data

       Path encryptedFile = getEncryptedFilePath(inputFile);
        Files.write(encryptedFile,outputBytes);
        Files.delete(inputFile);
    }



    public void decrypt(Path encryptedFile, String password) throws Exception{
        String fileName = encryptedFile.toString();
        if(!fileName.endsWith(ENCRYPTED_EXTENSION)){
            throw new Exception("Encrypted file must have " + ENCRYPTED_EXTENSION + " extension.");
        }
        byte[] fileBytes = Files.readAllBytes(encryptedFile);


        // Read extension length from first byte (unsigned)
        int extLength = fileBytes[0] & 0xFF;

        // Read extension bytes
        byte[] extBytes = new byte[extLength];
        System.arraycopy(fileBytes, 1, extBytes, 0, extLength);
        String originalExtension = new String(extBytes, StandardCharsets.UTF_8);

        // Extract encrypted content bytes (rest of the file after header)
        byte[] encryptedContent = new byte[fileBytes.length - 1 - extLength];
        System.arraycopy(fileBytes, 1 + extLength, encryptedContent, 0, encryptedContent.length);


      SecretKey key = CryptoUtils.getKeyFromPassword(password);
      byte[] decrypted = CryptoUtils.decrypt(encryptedContent, key);

        // Compose output file path with original extension restored
        Path decryptedFile = getDecryptedFilePath(encryptedFile, originalExtension);
        Files.write(decryptedFile, decrypted);
        Files.delete(encryptedFile);

    }



    private Path getEncryptedFilePath(Path originalFile) {
        String originalName = originalFile.getFileName().toString();
        int lastDotIndex = originalName.lastIndexOf('.');
        String baseName = (lastDotIndex == -1) ? originalName : originalName.substring(0, lastDotIndex);
        return originalFile.resolveSibling(baseName + ".enc");
    }



    private Path getDecryptedFilePath(Path encryptedFile, String originalExtension) {
        String encryptedName = encryptedFile.getFileName().toString();
        if (encryptedName.endsWith(ENCRYPTED_EXTENSION)) {
            String baseName = encryptedName.substring(0, encryptedName.length() - ENCRYPTED_EXTENSION.length()); // remove ".enc"
            return encryptedFile.resolveSibling(baseName + originalExtension);
        }
        return encryptedFile; // fallback
    }
}
