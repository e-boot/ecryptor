package com.eboot.encryptor.service.commands;

public interface Command {
    String name (); // encrypt/decrypt
    String description();

    void execute(String[] args) throws  Exception;
}
