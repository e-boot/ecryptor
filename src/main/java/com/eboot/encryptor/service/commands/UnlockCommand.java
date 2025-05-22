package com.eboot.encryptor.service.commands;

import com.eboot.encryptor.cli.PasswordReader;
import com.eboot.encryptor.service.FileEncryptor;

import java.nio.file.Files;
import java.nio.file.Path;

public class UnlockCommand implements Command{

    FileEncryptor encryptor = new FileEncryptor();

    @Override
    public String name() {
        return "unlock";
    }

    @Override
    public String description() {
        return "Unlocks an encrypted file";
    }

    @Override
    public void execute(String[] args) throws Exception {

        if(args.length < 2 ) {
            System.err.println("Usage: app unlock <filename>");
            return;
        }

        Path encryptedFile =Path.of(args[1]);

        if(!Files.exists(encryptedFile)){
            System.out.println("File doesn't exist: " + encryptedFile);
            return;
        }

        String password = PasswordReader.prompt("Enter password: ");
        encryptor.decrypt(encryptedFile, password);
        System.out.println("✅ File successfully unlocked: " + encryptedFile.getFileName());
    }
}
