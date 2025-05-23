package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.*;

import java.nio.file.Path;

import static com.eboot.encryptor.utils.Constants.ENCRYPTED_EXTENSION;

public class FileEncryptor {



    public void encrypt(Path inputFile, String password) throws Exception {
        byte[] originalBytes = FileHelper.readBytes(inputFile);
        byte[] encryptedBytes = CryptoHelper.encrypt(originalBytes, password);

        String extension = ExtensionHelper.extractExtension(inputFile);
        byte[] payload = PayloadBuilder.buildPayload(extension, encryptedBytes);

        Path outputFile = ExtensionHelper.getEncryptedPath(inputFile);
        FileHelper.writeBytes(outputFile, payload);
        FileHelper.deleteFile(inputFile);
    }


    public void decrypt(Path encryptedFile, String password) throws Exception {
        if (!encryptedFile.toString().endsWith(ENCRYPTED_EXTENSION)) {
            throw new IllegalArgumentException(Messages.WRONG_EXTENSION);
        }

        byte[] payload = FileHelper.readBytes(encryptedFile);
        String extension = PayloadBuilder.extractExtension(payload);
        byte[] encryptedBytes = PayloadBuilder.extractContent(payload);

        byte[] decryptedBytes = CryptoHelper.decrypt(encryptedBytes, password);
        Path outputFile = ExtensionHelper.getDecryptedPath(encryptedFile, extension);

        FileHelper.writeBytes(outputFile, decryptedBytes);
        FileHelper.deleteFile(encryptedFile);
    }
}
