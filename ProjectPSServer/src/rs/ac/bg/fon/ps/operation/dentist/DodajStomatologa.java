/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.dentist;

import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.DentistException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class DodajStomatologa extends GenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws DentistException {
        if(param == null || !(param instanceof Stomatolog))
            throw new DentistException("Podaci za stomatologa nisu validni!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        repository.dodaj((Stomatolog)param);
    }   
}