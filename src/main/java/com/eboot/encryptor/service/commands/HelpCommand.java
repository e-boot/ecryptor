package com.eboot.encryptor.service.commands;

public class HelpCommand implements Command {

    private final CommandRegistry registry;

    public HelpCommand(CommandRegistry registry){
        this.registry = registry;
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "Show available commands.";
    }

    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("Available commands: ");
        for(Command cmd : registry.getAllCommands()) {
            System.out.println( " " + cmd.name() + " - " + cmd.description());
        }
    }
}
