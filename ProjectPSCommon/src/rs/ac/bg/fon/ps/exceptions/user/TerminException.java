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
public class TerminException extends UserException {
    public TerminException() {
    }

    public TerminException(String message) {
        super(message);
    }

    public TerminException(Throwable cause) {
        super(cause);
    }

     public TerminException(String message, Throwable cause) {
        super(message, cause);
    }
}
