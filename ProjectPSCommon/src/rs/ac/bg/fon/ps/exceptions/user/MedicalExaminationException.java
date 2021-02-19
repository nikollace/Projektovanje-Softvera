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
public class MedicalExaminationException extends UserException {
    public MedicalExaminationException() {
    }

    public MedicalExaminationException(String message) {
        super(message);
    }

    public MedicalExaminationException(Throwable cause) {
        super(cause);
    }

     public MedicalExaminationException(String message, Throwable cause) {
        super(message, cause);
    }
}
