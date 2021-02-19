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
public class DentistException extends UserException {
    public DentistException() {
    }

    public DentistException(String message) {
        super(message);
    }

    public DentistException(Throwable cause) {
        super(cause);
    }

     public DentistException(String message, Throwable cause) {
        super(message, cause);
    }
}
