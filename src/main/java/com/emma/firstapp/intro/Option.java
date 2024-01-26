package com.emma.firstapp.intro;
import lombok.Data;

@Data
public class Option {
     
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        FRAMEWORK, STARTER
    }

}
