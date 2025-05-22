package com.eboot.encryptor.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileEncryptorTest {

    FileEncryptor encryptor = new FileEncryptor();
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Encrypt and decrypt should return original content")
    void encryptAndDecrypt_roundTripWork() throws Exception {
        String originalContest = "This is a test.";
        Path originalFile = tempDir.resolve("test.txt");
        Files.writeString(originalFile, originalContest);



        //Encrypt
        encryptor.encrypt(originalFile, "mypassword");
        Path encryptedFile = tempDir.resolve("test.enc");
        assertTrue(Files.exists(encryptedFile));
        assertFalse(Files.exists(originalFile));

        //Decrypt
        encryptor.decrypt(encryptedFile, "mypassword");
        Path decryptedFile = tempDir.resolve("test.txt");
        assertTrue(Files.exists(decryptedFile));

        String decryptedContent = Files.readString(decryptedFile);
        assertEquals(originalContest, decryptedContent);

    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "longerPassword123!", "!@#_$%"})
    @DisplayName("Encryption with different passwords should work")
    void encryptDecryptWithVariousPasswords(String password) throws Exception {
        String originalContent = "Some secure content.";
        Path originalFile = tempDir.resolve("secure.txt");
        Files.writeString(originalFile, originalContent);

        encryptor.encrypt(originalFile, password);
        Path encryptedFile = tempDir.resolve("secure.enc");

        encryptor.decrypt(encryptedFile, password);
        Path decryptedFile = tempDir.resolve("secure.txt");

        assertEquals(originalContent, Files.readString(decryptedFile));
    }

    @Test
    @DisplayName("Decryption with wrong password should fail")
    void decryptWithWrongPasswordThrows() throws Exception{
        String content = "Secret suff";
        Path originalFile = tempDir.resolve("secret.txt");
        Files.writeString(originalFile,content);

        encryptor.encrypt(originalFile,"corrrect-password");
        Path encryptedFile = tempDir.resolve("secret.enc");

        Exception exception = assertThrows(Exception.class, ()
                -> encryptor.decrypt(encryptedFile, "wrong-password")
        );

        assertTrue(exception.getMessage().toLowerCase().contains("decryption failed"));

    }

    @Test
    @DisplayName("Decrypting a tampered encrypted file should fail")
    void tamperedEncryptedFile_shouldFail() throws Exception {
        String content = "Sensitive data";
        Path originalFile = tempDir.resolve("data.txt");
        Files.writeString(originalFile, content);

        encryptor.encrypt(originalFile, "pass123");
        Path encryptedFile = tempDir.resolve("data.enc");

        // Tamper with the encrypted file
        byte[] tampered = Files.readAllBytes(encryptedFile);
        tampered[10] = (byte) (tampered[10] ^ 0xFF); // flip a byte
        Files.write(encryptedFile, tampered);

        assertThrows(Exception.class, () -> encryptor.decrypt(encryptedFile, "pass123"));
    }

    @Test
    @DisplayName("Encrypting a nonexistent file should throw IOException")
    void encryptingNonexistentFile_shouldThrow() {
        Path fakeFile = tempDir.resolve("doesnotexist.txt");

        assertThrows(IOException.class, () -> encryptor.encrypt(fakeFile, "password"));
    }

    @Test
    @DisplayName("Decrypting a nonexistent file should throw IOException")
    void decryptingNonexistentFile_shouldThrow() {
        Path fakeFile = tempDir.resolve("doesnotexist.enc");

        assertThrows(IOException.class, () -> encryptor.decrypt(fakeFile, "password"));
    }
}