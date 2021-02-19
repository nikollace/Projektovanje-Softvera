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
public class MedicalItemException extends UserException {
    public MedicalItemException() {
    }

    public MedicalItemException(String message) {
        super(message);
    }

    public MedicalItemException(Throwable cause) {
        super(cause);
    }

     public MedicalItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
