package com.eboot.encryptor.core;


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
     * @param outputFile Path to the output encrypted file.
     * @param password   Password used to generate encryption key.
     * @throws Exception If encryption fails.
     */
   public void encryptFile(Path inputFile, Path outputFile, String password) throws Exception{
       fileEncryptor.encrypt(inputFile, outputFile, password);
       System.out.println("File encrypted with success " + outputFile);
   }

    /**
     * Decrypts a file using the provided password and saves the result to outputFile.
     *
     * @param inputFile  Path to the encrypted input file.
     * @param outputFile Path to the output decrypted file.
     * @param password   Password used to generate decryption key.
     * @throws Exception If decryption fails.
     */
   public void decryptFile(Path inputFile, Path outputFile, String password) throws Exception{
       fileEncryptor.decrypt(inputFile, outputFile, password);
       System.out.println("File decrypted with success " + outputFile);
   }

}
