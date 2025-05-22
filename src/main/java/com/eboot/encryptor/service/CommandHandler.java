package com.eboot.encryptor.service;


import com.eboot.encryptor.service.commands.Command;
import com.eboot.encryptor.service.commands.CommandRegistry;
import com.eboot.encryptor.utils.Messages;

public class CommandHandler {

    private final CommandRegistry registry = new CommandRegistry();


    public void handle(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println(Messages.NO_COMAND_PROVIDED);
        return;
        }

        String cmdName = args[0];
        Command cmd = registry.getCommand(cmdName);

        if(cmd == null){
            System.err.println(Messages.UNKNOWN_COMMAND +cmdName);
            registry.getCommand("help").execute(new String[0]);
            return;
        }
        cmd.execute(args);

    }

}