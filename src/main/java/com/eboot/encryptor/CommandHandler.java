package com.eboot.encryptor;


public class CommandHandler {

private final   FileEncryptor fileEncryptor = new FileEncryptor();


   public void encryptFile(String inputPath, String outputPath, String password) throws Exception{
       fileEncryptor.encrypt(inputPath, outputPath, password);
       System.out.println("File encrypted with success " + outputPath);
   }

   public void decryptFile(String inputPath, String outputPath, String password) throws Exception{
       fileEncryptor.decrypt(inputPath, outputPath, password);
       System.out.println("File decrypted with success " + outputPath);
   }

}
