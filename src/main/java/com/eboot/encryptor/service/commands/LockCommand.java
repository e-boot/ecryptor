package com.eboot.encryptor.service.commands;

import com.eboot.encryptor.utils.PasswordReader;
import com.eboot.encryptor.core.FileEncryptor;
import com.eboot.encryptor.utils.Messages;

import java.nio.file.Path;

public class LockCommand implements Command {

    private final FileEncryptor encryptor = new FileEncryptor();

    @Override
    public String name() {
        return "lock";
    }

    @Override
    public String description() {
        return "Locks a file. Usage: file-encryptor lock <filename>";
    }

    @Override
    public void execute(String[] args) throws Exception {
        if(args.length < 2 ) {
            System.err.println("Usage: file-encryptor lock <filename>");
            return;
        }

        Path input =Path.of(args[1]);
        String password = PasswordReader.readAndConfirmPassword();
        encryptor.encrypt(input, password);
        System.out.println(Messages.FILE_LOCKED);
    }
}
