package com.eboot.ecryptor.utils;

import java.nio.file.Path;
import java.util.Objects;
import static com.eboot.ecryptor.utils.Constants.ENCRYPTED_EXTENSION;

/**
 * Utility class for handling file name extensions
 */
public class ExtensionHelper {


    /**
     * Extracts the extension of a file.
     * Returns an empty string if no extension exists.
     *
     * @param filePath Path to extract the extension from.
     * @return The file extension, e.g., ".txt"
     */
    public static String extractExtension(Path filePath) {
        Objects.requireNonNull(filePath, " File Path " + Messages.NOT_NULL);

        String name = filePath.getFileName().toString();
        int dotIndex = name.lastIndexOf('.');
        return dotIndex == -1 ? "" : name.substring(dotIndex);
    }


    /**
     * Generates an encrypted file path by replacing the original extension.
     *
     * @param originalFile Path of the original input file.
     * @return Path with encrypted extension.
     */
    public static Path getEncryptedPath(Path originalFile) {
        Objects.requireNonNull(originalFile, "Original file path " + Messages.NOT_NULL);

        String baseName = stripExtension(originalFile.getFileName().toString());
        return originalFile.resolveSibling(baseName + ENCRYPTED_EXTENSION);
    }


    /**
     * Generates a decrypted file path by restoring the original extension.
     *
     * @param encryptedFile      Path to the encrypted file.
     * @param originalExtension  Original file extension to restore (e.g., ".txt").
     * @return Path with the original extension.
     */
    public static Path getDecryptedPath(Path encryptedFile, String originalExtension ) {
        Objects.requireNonNull(encryptedFile, "Encrypted file path " + Messages.NOT_NULL);
        Objects.requireNonNull(originalExtension, "Original extension " + Messages.NOT_NULL);

        String fileName = encryptedFile.getFileName().toString();
        String baseName = fileName.substring(0, fileName.length() - ENCRYPTED_EXTENSION.length());
        return encryptedFile.resolveSibling(baseName + originalExtension);
    }

    // Removes the extension (e.g., "file.txt" → "file"
    private static String stripExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
