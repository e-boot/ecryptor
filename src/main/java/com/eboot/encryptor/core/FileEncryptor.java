package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.CryptoUtils;
import com.eboot.encryptor.utils.ExtensionUtils;
import com.eboot.encryptor.utils.FileIOUtils;
import com.eboot.encryptor.utils.PayloadBuilder;

import java.nio.file.Path;

public class FileEncryptor {

    private static final String ENCRYPTED_EXTENSION = ".enc";

    public void encrypt(Path inputFile, String password) throws Exception {
        byte[] originalBytes = FileIOUtils.readBytes(inputFile);
        byte[] encryptedBytes = CryptoUtils.encrypt(originalBytes, password);

        String extension = ExtensionUtils.extractExtension(inputFile);
        byte[] payload = PayloadBuilder.buildPayload(extension, encryptedBytes);

        Path outputFile = ExtensionUtils.getEncryptedPath(inputFile, ENCRYPTED_EXTENSION);
        FileIOUtils.writeBytes(outputFile, payload);
        FileIOUtils.deleteFile(inputFile);
    }

    public void decrypt(Path encryptedFile, String password) throws Exception {
        if (!encryptedFile.toString().endsWith(ENCRYPTED_EXTENSION)) {
            throw new IllegalArgumentException("File must end with " + ENCRYPTED_EXTENSION);
        }

        byte[] payload = FileIOUtils.readBytes(encryptedFile);
        String extension = PayloadBuilder.extractExtension(payload);
        byte[] encryptedBytes = PayloadBuilder.extractContent(payload);

        byte[] decryptedBytes = CryptoUtils.decrypt(encryptedBytes, password);
        Path outputFile = ExtensionUtils.getDecryptedPath(encryptedFile, extension, ENCRYPTED_EXTENSION);

        FileIOUtils.writeBytes(outputFile, decryptedBytes);
        FileIOUtils.deleteFile(encryptedFile);
    }
}
