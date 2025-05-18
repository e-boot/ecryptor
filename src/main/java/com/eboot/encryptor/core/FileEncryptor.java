package com.eboot.encryptor.core;

import com.eboot.encryptor.utils.CryptoUtils;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptor {


    public void encrypt(Path inputFile, Path outputFile, String password) throws Exception{
        byte[] fileBytes = Files.readAllBytes(inputFile);
        SecretKey key = CryptoUtils.getKeyFromPassword(password);
        byte[] encrypted = CryptoUtils.encrypt(fileBytes,key);
        Files.write(outputFile ,encrypted);
    }


    public void decrypt(Path inputFile, Path outputFile, String password) throws Exception{
      byte[] fileBytes = Files.readAllBytes(inputFile);
      SecretKey key = CryptoUtils.getKeyFromPassword(password);
      byte[] decrypted = CryptoUtils.decrypt(fileBytes, key);
      Files.write(outputFile, decrypted);
    }

}
