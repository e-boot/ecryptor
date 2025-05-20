package com.eboot.encryptor.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

    public static byte[] readBytes(Path path) throws Exception {
        return Files.readAllBytes(path);
    }

    public static void writeBytes(Path path, byte[] data) throws Exception {
        Files.write(path, data);
    }

    public static void deleteFile(Path path) throws Exception {
        Files.delete(path);
    }
}
