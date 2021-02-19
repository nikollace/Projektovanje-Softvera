/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exceptions.user;

import rs.ac.bg.fon.ps.exceptions.UserException;

/**
 *
 * @author nikollace
 */
public class RegisterException extends UserException {
    public RegisterException() {
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }

     public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
