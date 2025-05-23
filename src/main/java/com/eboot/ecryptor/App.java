package com.eboot.ecryptor;

import com.eboot.ecryptor.service.CommandHandler;

public class App {

    public static void main(String[] args) {
        try {
            CommandHandler handler = new CommandHandler();
            handler.handle(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}