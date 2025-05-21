package com.eboot.encryptor.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileEncryptorTest {

    @TempDir
    Path tempDir;

    @Test
    void encryptAndDecrypt_roundTripWork() throws Exception {
        String originalContest = "This is a test.";
        Path originalFile = tempDir.resolve("test.txt");
        Files.writeString(originalFile, originalContest);

        FileEncryptor encryptor = new FileEncryptor();

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
}