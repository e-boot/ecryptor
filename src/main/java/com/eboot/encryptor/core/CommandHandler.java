package com.eboot.encryptor.core;


import java.nio.file.Path;

public class CommandHandler {

private final   FileEncryptor fileEncryptor = new FileEncryptor();


   public void encryptFile(Path inputFile, Path outputFile, String password) throws Exception{
       fileEncryptor.encrypt(inputFile, outputFile, password);
       System.out.println("File encrypted with success " + outputFile);
   }

   public void decryptFile(Path inputFile, Path outputFile, String password) throws Exception{
       fileEncryptor.decrypt(inputFile, outputFile, password);
       System.out.println("File decrypted with success " + outputFile);
   }

}
