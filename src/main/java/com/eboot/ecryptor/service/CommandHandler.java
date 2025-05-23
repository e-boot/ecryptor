package com.eboot.ecryptor.service;


import com.eboot.ecryptor.service.commands.Command;
import com.eboot.ecryptor.service.commands.CommandRegistry;
import com.eboot.ecryptor.utils.Messages;

/**
 * Handles CLI command parsing and execution.
 */
public class CommandHandler {



    private final CommandRegistry registry;

    public CommandHandler(){
        this.registry = new CommandRegistry();
    }

    public CommandHandler(CommandRegistry registry){
        this.registry = registry;
    }

    /**
     * Parses and executes the appropriate command based on CLI input.
     *
     * @param args Command-line arguments
     * @throws Exception if a command fails to execute
     */
    public void handle(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println(Messages.NO_COMMAND_PROVIDED);
        return;
        }

        String commandName = args[0];
        Command command = registry.getCommand(commandName);

        if(command == null){
            System.err.println(Messages.UNKNOWN_COMMAND + " " + commandName);
            System.out.println();
            registry.getCommand("help").execute(new String[0]);
            return;
        }
        command.execute(args);

    }

}