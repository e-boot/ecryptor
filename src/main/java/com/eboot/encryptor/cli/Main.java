package com.eboot.encryptor.cli;

import com.eboot.encryptor.service.CommandHandler;

public class Main {

    public static void main(String[] args) {
        try {
            CommandHandler handler = new CommandHandler();
            handler.handle(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}