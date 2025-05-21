package com.eboot.encryptor.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


/**
 * Utility class file operations: read, write, delete
 */
public class FileHelper {


    /**
     * Reads the content of a file into a byte array.
     *
     * @param path Path to the file.
     * @return Byte array with the file's content.
     * @throws IOException If an I/O error occurs.
     */
    public static byte[] readBytes(Path path) throws IOException {
        Objects.requireNonNull(path,"Path must not be null");
        return Files.readAllBytes(path);
    }



    /**
     * Writes byte data to a file at the given path.
     *
     * @param path Path to the file.
     * @param data Data to write.
     * @throws IOException If an I/O error occurs.
     */
    public static void writeBytes(Path path, byte[] data) throws IOException {
        Objects.requireNonNull(path,"Path must not be null");
        Objects.requireNonNull(data,"Data must not be null");
        Files.write(path, data);
    }


    /**
     * Deletes the file at the specified path.
     *
     * @param path Path to the file.
     * @throws IOException If an I/O error occurs.
     */
    public static void deleteFile(Path path) throws IOException {
        Objects.requireNonNull(path,"Path must not be null");
        Files.delete(path);
    }
}
