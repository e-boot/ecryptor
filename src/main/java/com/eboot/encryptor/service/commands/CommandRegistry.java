package com.eboot.encryptor.service.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandRegistry(){
        register(new LockCommand());
        register(new UnlockCommand());
        register(new HelpCommand(this));
    }

    private void register(Command command) {
        commandMap.put(command.name(), command);
    }

    public Command getCommand(String name){
        return commandMap.get(name);
    }

    public Iterable<Command> getAllCommands(){
        return commandMap.values();
    }

}
