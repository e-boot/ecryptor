package com.eboot.encryptor.utils;

import static com.eboot.encryptor.utils.Constants.ENCRYPTED_EXTENSION;

public class Messages {


    public static final String LOCKING = " \n \uD83D\uDD12 Locking file: \n ";
    public static final String UNLOCKING = "\n \uD83D\uDD13 Unlocking file: \n";

    // Password
    public static final String ENTER_PASSWORD = "Enter password: ";
    public static final String CONFIRM_PASSWORD = "Confirm password: ";
    public static final String PASSWORD_DONT_MATCH = " ❌ Passwords don't match. Please try again";
    public static final String TOO_MANY_ATTEMPTS = " ❌ To many failed attempts. Aborting task.";


    // Commands
    public static final String NO_COMMAND_PROVIDED = "No command provided. Try 'help' for usage.";
    public static final String UNKNOWN_COMMAND =" ❌ Unknown Command";





    public static final String PAYLOAD_TOO_SHORT = " ⚠ Payload too short to extract extension length.";;
    public static final String DATA_TOO_SHORT = " ⚠ Encrypted data is too short to contain salt and IV";
    public static final String ENCRYPTION_FAILED = "\n ❌ Encryption failed.\n";

    public static final String DECRYPTION_FAILED = "\n ❌ Decryption failed: Wrong password or corrupted file.\n ";
    public static final String KEY_FAILED = "Key derivation failed.";

    // Extensions

    public static final String NOT_NULL = " must not be null.";


    public static final String FILE_UNLOCKED = " \n\"✅ File unlocked successfully → ";
    public static final String FILE_LOCKED = " \n ✅ File locked successfully →  ";
    public static final String FIle_DO_NOT_EXIST = " ❌ The indicated file don't exist: ";


    public static final int EXT_LENGTH_BYTE_SIZE = 1;
    public static final int MAX_EXTENSION_LENGTH = 255;


    public static final  String WRONG_EXTENSION = "File must end with " + ENCRYPTED_EXTENSION;
    public static final String EXTENSION_TOO_LONG = ("Extension too long. Max supported length is " + MAX_EXTENSION_LENGTH + " bytes.");
}
