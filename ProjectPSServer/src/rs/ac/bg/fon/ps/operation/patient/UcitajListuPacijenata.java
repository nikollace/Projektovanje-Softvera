/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.patient;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.PatientException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class UcitajListuPacijenata extends GenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws PatientException {
        if(param == null)
            throw new PatientException("Nisu validni podaci za pacijenta!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        repository.nadjiSve(new Pacijent(), (List<Pacijent>) param);
    }
}
