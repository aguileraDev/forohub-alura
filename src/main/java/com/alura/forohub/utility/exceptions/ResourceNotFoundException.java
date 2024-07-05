package com.alura.forohub.utility.exceptions;


/**
 * @author Manuel Aguilera / @aguileradev
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

}
