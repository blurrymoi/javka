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
public class NotYetException extends RuntimeException{
    
    public NotYetException(String msg) {
        super(msg);
    }

    public NotYetException(Throwable cause) {
        super(cause);
    }

    public NotYetException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
