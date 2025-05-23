package com.eboot.encryptor.core;
import static  org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CryptoHelperTest {

    private static final String TEST_PASSWORD = "strong-password";
    private static final byte [] SAMPLE_DATA = "this is a secret shhhh".getBytes();


    @Test
    @DisplayName("Encrypt and Decrypt should return original data")
    void testEncryptThenDecrypt() throws Exception {
        byte[] encrypted = CryptoHelper.encrypt(SAMPLE_DATA, TEST_PASSWORD);
        byte[] decrypted = CryptoHelper.decrypt(encrypted, TEST_PASSWORD);

        assertArrayEquals(SAMPLE_DATA,decrypted);
    }


    @ParameterizedTest(name = "Wrong password \"{0}\" should fail decryption")
    @ValueSource(strings = {"wrong1", "123456", "", " "})
    void testDecryptWithWrongPassword(String wrongPassword) throws Exception{
    byte [] encrypted = CryptoHelper.encrypt(SAMPLE_DATA, TEST_PASSWORD);
    Exception exception = assertThrows(Exception.class, () -> CryptoHelper.decrypt(encrypted,wrongPassword));

    assertTrue(exception.getMessage().contains("Decryption failed"));
    }




}

