package com.eboot.encryptor.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that parses command-line arguments for a CLI application.
 * It expects a mode followed by pairs of flags and values.
 * Example usage: encrypt -in input.txt -out output.txt
 */
public class ArgumentParser {
    private final String mode;
    private final Map<String, String> flags = new HashMap<>();



    /**
     * Constructs the ArgumentParser and parses the input arguments.
     *
     * @param args The command-line arguments.
     * @throws IllegalArgumentException If the argument count is incorrect or a flag is missing a value.
     */
    public ArgumentParser(String[] args) throws  IllegalArgumentException{
    if(args.length < 5 || args.length % 2 == 0 ) {
        throw  new IllegalArgumentException("Invalid argument count.");
    }


    this.mode = args[0].toLowerCase();
    for(int i = 1; i < args.length; i +=2){
        if(i + 1 >= args.length){
            throw  new IllegalArgumentException("Missing value for " + args[i]);
        }
        flags.put(args[i], args[i +1]);
    }
}


    /**
     * Returns the mode specified in the arguments.
     *
     * @return The operation mode (e.g., "encrypt" or "decrypt")
     */
    public String getMode() {
        return mode;
    }


    /**
     * Returns the absolute and normalized input file path specified with the -in flag.
     *
     * @return The input file path
     */
    public Path getInputPath(){
        return Paths.get(flags.get("-in")).toAbsolutePath().normalize();
    }

    /**
     * Returns the absolute and normalized output file path specified with the -out flag.
     *
     * @return The output file path
     */
    public Path getOutputPath(){
        return  Paths.get(flags.get("-out")).toAbsolutePath().normalize();
    }
}
