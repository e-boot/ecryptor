package com.eboot.ecryptor.service.commands;

import com.eboot.ecryptor.utils.PasswordReader;
import com.eboot.ecryptor.core.FileEncryptor;

import java.nio.file.Path;
import java.util.Scanner;

public class LockCommand implements Command {

    private final FileEncryptor encryptor = new FileEncryptor();
    private final PasswordReader pwdReader = new PasswordReader(System.console(),new Scanner(System.in));

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
        String password = pwdReader.readAndConfirmPassword();
        encryptor.encrypt(input, password);
    }
}
