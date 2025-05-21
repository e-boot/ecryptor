package com.eboot.encryptor.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;
import static com.eboot.encryptor.utils.Constants.*;
import static com.eboot.encryptor.utils.Constants.CipherConfig.*;

/**
 * Utility class for secure AES encryption/decryption using PBKDF2 and CBC mode.
 */
public class CryptoHelper {


    /**
     * Encrypts the given data using AES/CBC with a key derived from the given password.
     *
     * @param data  Plain byte array to encrypt.
     * @param password Password to derive the key from.
     * @return Encrypted data (salt + IV + ciphertext).
     * @throws Exception If encryption fails.
     */
    public static byte[] encrypt(byte[] data, String password) throws Exception {
        try{
            byte[] salt = generateRandomBytes(SALT_LENGTH);
            byte[] iv = generateRandomBytes(IV_LENGTH);
            SecretKey secretKey = deriveKey(password, salt);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encryptedData = cipher.doFinal(data);

        return ByteBuffer.allocate(salt.length + iv.length + encryptedData.length)
                .put(salt)
                .put(iv)
                .put(encryptedData)
                .array();

        } catch (Exception e) {
            System.err.println("Encryption failed: " + e.getMessage());
            throw new Exception("Encryption failed", e);
        }
    }

    /**
     * Decrypts the given encrypted data using AES/CBC with a key derived from the given password.
     *
     * @param encryptedData Encrypted data (salt + IV + ciphertext).
     * @param password      Password used to derive the key.
     * @return Decrypted byte array.
     * @throws Exception If decryption fails.
     */
    public static byte[] decrypt(byte[] encryptedData, String password) throws Exception {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(encryptedData);

            if(encryptedData.length < SALT_LENGTH + IV_LENGTH){
                throw new IllegalArgumentException("Encrypted data is too short to contain salt and IV");
            }

            byte[] salt = new byte[SALT_LENGTH];
            byte[] iv = new byte[IV_LENGTH];
            buffer.get(salt);
            buffer.get(iv);
            byte[] ciphertext = new byte[buffer.remaining()];
            buffer.get(ciphertext);

            SecretKey secretKey = deriveKey(password, salt);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            System.err.println("Decryption failed: " + e.getMessage());
            throw new Exception("Decryption failed ", e);
        }
    }

    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_DERIVATION);
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();
            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            System.err.println("Key derivation failed: " + e.getMessage());
            throw new Exception("Key derivation failed", e);
        }
    }

    private static byte[] generateRandomBytes(int length) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[length];
        SecureRandom.getInstanceStrong().nextBytes(bytes);
        return bytes;
    }
}
