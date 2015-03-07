/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

/**
 *
 * @author Barbora
 */
public class NoServiceException extends RuntimeException{
    
    public NoServiceException(String msg) {
        super(msg);
    }

    public NoServiceException(Throwable cause) {
        super(cause);
    }

    public NoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
