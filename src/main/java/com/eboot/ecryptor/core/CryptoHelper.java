package com.eboot.ecryptor.core;

import com.eboot.ecryptor.utils.Messages;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;

import static com.eboot.ecryptor.utils.Constants.*;
import static com.eboot.ecryptor.utils.Constants.CipherConfig.*;

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

            ByteBuffer buffer = ByteBuffer.allocate(salt.length + iv.length + encryptedData.length);
                buffer.put(salt);
                buffer.put(iv);
                buffer.put(encryptedData);
                return buffer.array();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(Messages.ENCRYPTION_FAILED, e);
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
                throw new IllegalArgumentException(Messages.DATA_TOO_SHORT);
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
        } catch (BadPaddingException | IllegalBlockSizeException  e) {
            throw new Exception(Messages.DECRYPTION_FAILED, e);
        }
    }

    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        char[] passwordCharts = password.toCharArray();
        try {
            PBEKeySpec spec = new PBEKeySpec(passwordCharts, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_DERIVATION);
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();
            return new SecretKeySpec(keyBytes, "AES");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(Messages.KEY_FAILED, e);
        }finally {
            Arrays.fill(passwordCharts, '\0'); // wipe password charts from memory
        }
    }

    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }
        random.nextBytes(bytes);
        return bytes;
    }
}
