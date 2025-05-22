package com.eboot.encryptor.service.commands;


public class CommandHandler {

    private final CommandRegistry registry = new CommandRegistry();


    public void handle(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println("No command provided. Use 'help'");
        return;
        }

        String cmdName = args[0];
        Command cmd = registry.getCommand(cmdName);

        if(cmd == null){
            System.err.println("Unknown command: " +cmdName);
            registry.getCommand("help").execute(new String[0]);
            return;
        }
        cmd.execute(args);

    }

}