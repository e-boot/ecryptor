package com.eboot.encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class CryptoUtils {

    // Method to generate AES key from password using SHA-256
    public static SecretKey getKeyFromPassword(String password) throws Exception {
        byte[] key = password.getBytes(StandardCharsets.UTF_8);

        // Use SHA-256 to hash the password
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);

        // Return AES key (using the first 16 bytes for AES-128)
        return new SecretKeySpec(Arrays.copyOf(key, 16), "AES");
    }


    // Method to encrypt or decrypt the data based on cipher mode (ENCRYPT_MODE or DECRYPT_MODE)
    public static byte[] process(int cipherMode, SecretKey key, byte[] inputBytes) throws Exception {
        // Initialize the AES cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipherMode, key);

        // Process (encrypt/decrypt) the input bytes
        return cipher.doFinal(inputBytes);
    }


}