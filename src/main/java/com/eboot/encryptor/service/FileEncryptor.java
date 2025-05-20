package com.eboot.encryptor.service;

import com.eboot.encryptor.utils.CryptoHelper;
import com.eboot.encryptor.utils.ExtensionHelper;
import com.eboot.encryptor.utils.FileHelper;
import com.eboot.encryptor.utils.PayloadBuilder;

import java.nio.file.Path;

public class FileEncryptor {

    private static final String ENCRYPTED_EXTENSION = ".enc";

    public void encrypt(Path inputFile, String password) throws Exception {
        byte[] originalBytes = FileHelper.readBytes(inputFile);
        byte[] encryptedBytes = CryptoHelper.encrypt(originalBytes, password);

        String extension = ExtensionHelper.extractExtension(inputFile);
        byte[] payload = PayloadBuilder.buildPayload(extension, encryptedBytes);

        Path outputFile = ExtensionHelper.getEncryptedPath(inputFile, ENCRYPTED_EXTENSION);
        FileHelper.writeBytes(outputFile, payload);
        FileHelper.deleteFile(inputFile);
    }

    public void decrypt(Path encryptedFile, String password) throws Exception {
        if (!encryptedFile.toString().endsWith(ENCRYPTED_EXTENSION)) {
            throw new IllegalArgumentException("File must end with " + ENCRYPTED_EXTENSION);
        }

        byte[] payload = FileHelper.readBytes(encryptedFile);
        String extension = PayloadBuilder.extractExtension(payload);
        byte[] encryptedBytes = PayloadBuilder.extractContent(payload);

        byte[] decryptedBytes = CryptoHelper.decrypt(encryptedBytes, password);
        Path outputFile = ExtensionHelper.getDecryptedPath(encryptedFile, extension, ENCRYPTED_EXTENSION);

        FileHelper.writeBytes(outputFile, decryptedBytes);
        FileHelper.deleteFile(encryptedFile);
    }
}
