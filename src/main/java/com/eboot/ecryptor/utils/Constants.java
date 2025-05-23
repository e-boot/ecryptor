package com.eboot.ecryptor.utils;


public final class Constants {
    private Constants (){} // prevent instantiation


    public static final int SALT_LENGTH = 16;
    public static final int IV_LENGTH = 16;
    public static final int KEY_LENGTH = 128;
    public static final int ITERATIONS = 65536;


    public static final String ENCRYPTED_EXTENSION = ".enc";

public static class CipherConfig{
    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String KEY_DERIVATION = "PBKDF2WithHmacSHA256";
}


}
