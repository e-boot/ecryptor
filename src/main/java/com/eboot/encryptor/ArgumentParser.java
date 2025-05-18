package com.eboot.encryptor;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {
    private final String mode;
    private final Map<String, String> flags = new HashMap<>();

    public ArgumentParser(String[] args) throws  IllegalArgumentException{
    if(args.length != 5) {
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

    public String getMode() {
        return mode;
    }

    public String getInputPath(){
        return flags.get("-in");
    }

    public String getOutputPath(){
        return  flags.get("-out");
    }
}
