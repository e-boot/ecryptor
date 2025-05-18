package com.eboot.encryptor;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Arrays;

public class FileEncryptor {

    private static SecretKey getKeyFromPassword(String password) throws Exception{
        byte[] key = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        return new SecretKeySpec(Arrays.copyOf(key,16),"AES");
    }


    public void encrypt(String inputPath, String outputPath, String password) throws Exception{
        SecretKey key = getKeyFromPassword(password);

        byte[] fileBytes = Files.readAllBytes(new File(inputPath).toPath());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        Files.write(new File(outputPath).toPath(), encryptedBytes);
    }


    public void decrypt(String inputFile, String outputFile, String password) throws Exception{
        SecretKey key = getKeyFromPassword(password);

        byte[] fileBytes = Files.readAllBytes(new File(inputFile).toPath());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(fileBytes);

        Files.write(new File(outputFile).toPath(), decryptedBytes);
    }

}
