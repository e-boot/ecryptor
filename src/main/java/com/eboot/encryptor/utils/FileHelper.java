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
        Objects.requireNonNull(path, "Path " + Messages.NOT_NULL);
        if (!Files.exists(path)) throw new IOException(Messages.FIle_DO_NOT_EXIST + path);
        if(!Files.isReadable(path)) throw  new IOException("File is not readable : " +path);
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
        Objects.requireNonNull(path,"Path " + Messages.NOT_NULL);
        Objects.requireNonNull(data,"Data " + Messages.NOT_NULL);
        Files.write(path, data);
    }


    /**
     * Deletes the file at the specified path.
     *
     * @param path Path to the file.
     * @throws IOException If an I/O error occurs.
     */
    public static void deleteFile(Path path) throws IOException {
        if(!Files.exists(path)){

        }
        Objects.requireNonNull(path,"Path " + Messages.NOT_NULL);
        Files.delete(path);
    }
}
