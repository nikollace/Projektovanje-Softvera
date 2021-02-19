/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.patient;

import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.PatientException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class IzmeniPacijenta extends GenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws PatientException {
        if(param == null || !(param instanceof Pacijent))
            throw new PatientException("Nisu validni podaci za pacijenta!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        repository.izmeni((Pacijent)param);
    }
    
}
