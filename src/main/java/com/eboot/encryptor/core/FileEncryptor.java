package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.*;

import java.nio.file.Path;

import static com.eboot.encryptor.utils.Constants.ENCRYPTED_EXTENSION;

public class FileEncryptor {

    public void encrypt(Path inputFile, String password) throws Exception {
        byte[] originalContent = FileHelper.readBytes(inputFile);
        byte[] encryptedContent = CryptoHelper.encrypt(originalContent, password);

        String originalExtension = ExtensionHelper.extractExtension(inputFile);
        byte[] payload = PayloadBuilder.buildPayload(originalExtension, encryptedContent);

        Path encryptedFile = ExtensionHelper.getEncryptedPath(inputFile);
        FileHelper.writeBytes(encryptedFile, payload);
        FileHelper.deleteFile(inputFile);
    }


    public void decrypt(Path encryptedFile, String password) throws Exception {
       validateEncryptedExtension(encryptedFile);

        byte[] payload = FileHelper.readBytes(encryptedFile);
        String originalExtension = PayloadBuilder.extractExtension(payload);
        byte[] encryptedContent = PayloadBuilder.extractContent(payload);

        byte[] decryptedContent = CryptoHelper.decrypt(encryptedContent, password);

        Path outputFile = ExtensionHelper.getDecryptedPath(encryptedFile, originalExtension);

        FileHelper.writeBytes(outputFile, decryptedContent);
        FileHelper.deleteFile(encryptedFile);
    }

    // helper function
    private void validateEncryptedExtension(Path file) {
    if(!file.toString().endsWith(ENCRYPTED_EXTENSION)){
        throw new IllegalArgumentException(Messages.WRONG_EXTENSION);
    }
    }
}
