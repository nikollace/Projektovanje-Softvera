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
public class PatientException extends UserException {
    public PatientException() {
    }

    public PatientException(String message) {
        super(message);
    }

    public PatientException(Throwable cause) {
        super(cause);
    }

     public PatientException(String message, Throwable cause) {
        super(message, cause);
    }
}
