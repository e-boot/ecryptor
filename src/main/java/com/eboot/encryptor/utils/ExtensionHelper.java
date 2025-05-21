package com.eboot.encryptor.utils;

import java.nio.file.Path;
import java.util.Objects;
import static com.eboot.encryptor.utils.Constants.ENCRYPTED_EXTENSION;


/**
 * Utility class for handling file name extensions and generating
 * paths for encrypted/decrypted files.
 */
public class ExtensionHelper {



    /**
     * Extracts extension of a file.
     * Returns an empty string if no extension exists.
     *
     * @param path Path to extract the extension from.
     * @return The file extension, e.g., ".txt"
     */
    public static String extractExtension(Path path) {
        Objects.requireNonNull(path, "Path must not be null");

        String name = path.getFileName().toString();
        int index = name.lastIndexOf('.');
        return index == -1 ? "" : name.substring(index);
    }


    /**
     * Creates encrypted file path, replace it the original extension with encrypted extension
     *
     * @param inputFile Path of the original input file.
     * @return Path with encrypted extension.
     */
    public static Path getEncryptedPath(Path inputFile) {
        Objects.requireNonNull(inputFile, "Input file must not be null");

        String baseName = stripExtension(inputFile.getFileName().toString());
        return inputFile.resolveSibling(baseName + ENCRYPTED_EXTENSION);
    }


    /**
     * Creates decrypted file path, restoring the original extension.
     *
     * @param encryptedFile Path to the encrypted file.
     * @param originalExtension Original file extension to restore.
     * @return Path with the original extension.
     */
    public static Path getDecryptedPath(Path encryptedFile, String originalExtension ) {
        Objects.requireNonNull(encryptedFile, "Encrypted file must not be null");
        Objects.requireNonNull(originalExtension, "Original extension must not be null");

        String name = encryptedFile.getFileName().toString();
        String baseName = name.substring(0, name.length() - ENCRYPTED_EXTENSION.length());
        return encryptedFile.resolveSibling(baseName + originalExtension);
    }

    // Removes the extension (e.g., "file.txt" → "file"
    private static String stripExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
