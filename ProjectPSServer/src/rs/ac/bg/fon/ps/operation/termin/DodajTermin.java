/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.termin;

import rs.ac.bg.fon.ps.domain.Termin;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.TerminException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class DodajTermin extends GenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws TerminException {
        if(param == null || !(param instanceof Termin))
            throw new TerminException("Nisu validni podaci za termin!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        //repository.delete((Termin)param);
        repository.dodaj((Termin)param);
    }
    
}
