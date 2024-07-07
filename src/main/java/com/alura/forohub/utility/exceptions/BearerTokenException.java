package com.alura.forohub.utility.exceptions;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public class BearerTokenException extends RuntimeException{
    public BearerTokenException(String message){
        super(message);
    }
}
