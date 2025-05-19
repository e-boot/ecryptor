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

    if(args.length < 1) {
        throw  new IllegalArgumentException("No arguments provided.");

    }


    this.mode = args[0].toLowerCase();
    if((args.length -1 )% 2 != 0) {
        throw new IllegalArgumentException("Flags and values must be in pairs");
    }

    for(int i = 1; i < args.length; i +=2){
        String flag = args[1];
        if(i + 1 >= args.length){
            throw  new IllegalArgumentException("Missing value for " + args[i]);
        }
        String value = args[i+1];
        flags.put(flag,value);
    }

    if(!flags.containsKey("-in")){
        throw new IllegalArgumentException("Missing required flag: -in <file>");
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
    public Path getOutputPath() {
        String outPath = flags.get("-out");
        if (outPath == null) {
            return null;
        }
        return Paths.get(outPath).toAbsolutePath().normalize();
    }
}
