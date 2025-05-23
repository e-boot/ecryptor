package com.eboot.encryptor.service.commands;

import com.eboot.encryptor.utils.PasswordReader;
import com.eboot.encryptor.core.FileEncryptor;
import com.eboot.encryptor.utils.Messages;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class UnlockCommand implements Command{

    private FileEncryptor encryptor = new FileEncryptor();
    private PasswordReader pwdReader = new PasswordReader(System.console(), new Scanner(System.in));

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
            System.out.println(Messages.FIle_DO_NOT_EXIST  + encryptedFile);
            return;
        }

        String password = pwdReader.prompt("Enter password: ");
        encryptor.decrypt(encryptedFile, password);
    }
}
