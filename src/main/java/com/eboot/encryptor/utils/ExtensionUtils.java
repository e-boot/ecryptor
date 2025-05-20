package com.eboot.encryptor.utils;

import java.nio.file.Path;

public class ExtensionUtils {

    public static String extractExtension(Path path) {
        String name = path.getFileName().toString();
        int index = name.lastIndexOf('.');
        return index == -1 ? "" : name.substring(index);
    }

    public static Path getEncryptedPath(Path inputFile, String encryptedExtension) {
        String baseName = stripExtension(inputFile.getFileName().toString());
        return inputFile.resolveSibling(baseName + encryptedExtension);
    }

    public static Path getDecryptedPath(Path encryptedFile, String extension, String encryptedExtension) {
        String name = encryptedFile.getFileName().toString();
        String baseName = name.substring(0, name.length() - encryptedExtension.length());
        return encryptedFile.resolveSibling(baseName + extension);
    }

    private static String stripExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
