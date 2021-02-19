/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.validation;

import rs.ac.bg.fon.ps.view.form.FrmPatient;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author nikollace
 */
public interface Validation {
    public void validate(FrmPatient forma, String pattern, String variable);
    public void formModeSetter(FrmPatient forma, FormMode formMode);
}
