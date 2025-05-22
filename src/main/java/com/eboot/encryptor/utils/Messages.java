package com.eboot.encryptor.utils;

import static com.eboot.encryptor.utils.Constants.ENCRYPTED_EXTENSION;

public class Messages {

    // Password
    public static final String ENTER_PASSWORD = "Enter password: ";
    public static final String CONFIRM_PASSWORD = "Confirm password: ";
    public static final String PASSWORD_DONT_MATCH = "Passwords don't match. Retry";
    public static final String TOO_MANY_ATTEMPTS = "To many attempts. Aborting task.";


    // Commands
    public static final String NO_COMAND_PROVIDED = "No command provided. Use 'help'";
    public static final String UNKNOWN_COMMAND =" Unknown Command";



    public static final  String WRONG_EXTENSION = "File must end with " + ENCRYPTED_EXTENSION;

    public static final String ENCRYPTION_FAILED = "Encryption failed.";
    public static final String DATA_TOO_SHORT = "Encrypted data is too short to contain salt and IV";

    public static final String DECRYPTION_FAILED = "Decryption failed: Wrong password or corrupted file. ";
    public static final String KEY_FAILED = "Key derivation failed.";

    // Extensions

    public static final String NOT_NULL = " must not be null.";


    public static final String FILE_UNLOCKED = "✅ File successfully unlocked: ";
    public static final String FILE_LOCKED = "✅ File successfully locked: ";
    public static final String FIle_DO_NOT_EXIST = "The indicated file don't exist: ";

}
