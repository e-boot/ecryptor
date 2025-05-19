package com.eboot.encryptor.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

/**
 * Utility class for secure AES encryption/decryption using PBKDF2 and CBC mode.
 */
public class CryptoUtils {

    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 16;
    private static final int KEY_LENGTH = 128;
    private static final int ITERATIONS = 65536;

    /**
     * Encrypts data using AES/CBC with a key derived from a password.
     *
     * @param data     Plain data bytes.
     * @param password User-provided password.
     * @return Encrypted data containing salt + IV + ciphertext.
     * @throws Exception If encryption fails.
     */
    public static byte[] encrypt(byte[] data, String password) throws Exception {
        byte[] salt = generateRandomBytes(SALT_LENGTH);
        byte[] iv = generateRandomBytes(IV_LENGTH);
        SecretKey secretKey = deriveKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encryptedData = cipher.doFinal(data);

        ByteBuffer buffer = ByteBuffer.allocate(salt.length + iv.length + encryptedData.length);
        buffer.put(salt);
        buffer.put(iv);
        buffer.put(encryptedData);

        return buffer.array();
    }

    /**
     * Decrypts data using AES/CBC with a key derived from a password.
     *
     * @param encryptedData Data containing salt + IV + ciphertext.
     * @param password      Password used to derive the key.
     * @return Decrypted plain data.
     * @throws Exception If decryption fails.
     */
    public static byte[] decrypt(byte[] encryptedData, String password) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(encryptedData);

        byte[] salt = new byte[SALT_LENGTH];
        byte[] iv = new byte[IV_LENGTH];
        buffer.get(salt);
        buffer.get(iv);
        byte[] ciphertext = new byte[buffer.remaining()];
        buffer.get(ciphertext);

        SecretKey secretKey = deriveKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }

    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] generateRandomBytes(int length) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[length];
        SecureRandom sr = SecureRandom.getInstanceStrong();
        sr.nextBytes(bytes);
        return bytes;
    }
}
