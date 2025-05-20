package com.eboot.encryptor.service;


import java.nio.file.Path;

/**
 * Handles high-level commands such as encrypting and decrypting files.
 */
public class CommandHandler {

private final   FileEncryptor fileEncryptor = new FileEncryptor();


    /**
     * Encrypts a file using the provided password and saves the result to outputFile.
     *
     * @param inputFile  Path to the input file to encrypt.
     * @param password   Password used to generate encryption key.
     * @throws Exception If encryption fails.
     */
   public void encryptFile(Path inputFile, String password) throws Exception{
       fileEncryptor.encrypt(inputFile, password);
       System.out.println("File encrypted with success.");
   }

    /**
     * Decrypts a file using the provided password and saves the result to outputFile.
     * @param encryptedFile  Path to the encrypted input file.
     * @param password   Password used to generate decryption key.
     * @throws Exception If decryption fails.
     */
   public void decryptFile(Path encryptedFile, String password) throws Exception{
       fileEncryptor.decrypt(encryptedFile, password);
       System.out.println("File decrypted with success ");
   }

}
