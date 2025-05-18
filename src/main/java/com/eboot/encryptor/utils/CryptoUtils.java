package com.eboot.encryptor.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Utility class for basic AES encryption and decryption operations.
 */
public class CryptoUtils {

    /**
     * Generates an AES SecretKey from a given password using SHA-256 hashing.
     *
     * @param password The password used to derive the AES key.
     * @return A SecretKey object for AES encryption/decryption.
     * @throws Exception If the hashing or key generation fails.
     */
    public static SecretKey getKeyFromPassword(String password) throws Exception{
        byte[] key = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        return new SecretKeySpec(Arrays.copyOf(key,16),"AES");
    }

    /**
     * Encrypts the given byte array using AES and the provided secret key.
     *
     * @param data The plain data to encrypt.
     * @param key  The AES secret key.
     * @return The encrypted data as a byte array.
     * @throws Exception If encryption fails.
     */
    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }



    /**
     * Decrypts the given byte array using AES and the provided secret key.
     *
     * @param encryptedData The data to decrypt (ciphertext).
     * @param key           The AES secret key.
     * @return The decrypted (original) data as a byte array.
     * @throws Exception If decryption fails.
     */
    public static byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }
}
