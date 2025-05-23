package com.eboot.ecryptor.core;

import com.eboot.ecryptor.utils.*;

import java.io.IOException;
import java.nio.file.Path;

import static com.eboot.ecryptor.utils.Constants.ENCRYPTED_EXTENSION;

public class FileEncryptor {

    public void encrypt(Path inputFile, String password) throws Exception {
        System.out.println(Messages.LOCKING + inputFile.getFileName());
        long start = System.currentTimeMillis();

        try {
            byte[] originalContent = FileHelper.readBytes(inputFile);
            byte[] encryptedContent = CryptoHelper.encrypt(originalContent, password);

            String originalExtension = ExtensionHelper.extractExtension(inputFile);
            byte[] payload = PayloadBuilder.buildPayload(originalExtension, encryptedContent);

            Path encryptedFile = ExtensionHelper.getEncryptedPath(inputFile);
            FileHelper.writeBytes(encryptedFile, payload);
            FileHelper.deleteFile(inputFile);

            System.out.println(Messages.FILE_LOCKED + inputFile.getFileName()+ ENCRYPTED_EXTENSION);
        } catch (Exception e) {
                System.err.println(Messages.ENCRYPTION_FAILED + e.getMessage());
                throw e;

        }finally {
            long end = System.currentTimeMillis();
            System.out.println(("⏱️ Done in " + (end - start) + " ms"));
        }


    }


    public void decrypt(Path encryptedFile, String password) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println(Messages.UNLOCKING + encryptedFile.getFileName());

      try {
          validateEncryptedExtension(encryptedFile);

          byte[] payload = FileHelper.readBytes(encryptedFile);
          String originalExtension = PayloadBuilder.extractExtension(payload);
          byte[] encryptedContent = PayloadBuilder.extractContent(payload);

          byte[] decryptedContent = CryptoHelper.decrypt(encryptedContent, password);

          Path outputFile = ExtensionHelper.getDecryptedPath(encryptedFile, originalExtension);

          FileHelper.writeBytes(outputFile, decryptedContent);
          FileHelper.deleteFile(encryptedFile);
          System.out.println(Messages.FILE_UNLOCKED + outputFile.getFileName());
      } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Error: " + e.getMessage());
      }
      catch (IOException e){
          throw (e);
      }
      catch (Exception e) {
          throw  new Exception(Messages.DECRYPTION_FAILED, e);
      }finally {
          long end = System.currentTimeMillis();
          System.out.println("⏱️ Done in " + (end - start) + " ms");
      }

      }

    // helper function
    private void validateEncryptedExtension(Path file) {
    if(!file.toString().endsWith(ENCRYPTED_EXTENSION)){
        throw new IllegalArgumentException(Messages.WRONG_EXTENSION);
    }
    }
}
