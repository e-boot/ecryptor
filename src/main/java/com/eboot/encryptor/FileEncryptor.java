package com.eboot.encryptor;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class FileEncryptor {

    private static SecretKey getKeyFromPassword(String password) throws Exception{
        byte[] key = password.getBytes(StandardCharsets.UTF_8);

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);

        return new SecretKeySpec(Arrays.copyOf(key,16),"AES");
    }


    public static void encrypt(String inputFile, String outputFile, String password) throws Exception{
        SecretKey secretKey = getKeyFromPassword(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        try(FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            CipherOutputStream cos = new CipherOutputStream(fos,cipher)
        ){
            byte[] buffer = new byte[4096];
            int read;
            while((read = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, read);
            }
        }
        System.out.println("File encrypted successfully");
    }


    public static void decrypt(String inputFile, String outputFile, String password) throws Exception{
        SecretKey secretKey = getKeyFromPassword(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey);

        try(FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            CipherInputStream cis = new CipherInputStream(fis,cipher)){

            byte[] buffer = new byte[4096];
            int read;
            while ((read = cis.read(buffer)) != -1){
                fos.write(buffer,0,read);
            }
        }
         System.out.println("File decrypted successfully");
    }

}
