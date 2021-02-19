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
public class DodajPacijenta extends GenerickaOperacija {

    @Override
    protected void preduslovi(Object patient) throws PatientException {
        
        if(patient == null || !(patient instanceof Pacijent)){
            throw new PatientException("Nisu validni podaci za pacijenta!");
        } 
        
        if(((Pacijent)patient).getIme().isEmpty() || ((Pacijent)patient).getIme().equals("Unesite ime...") 
                || ((Pacijent)patient).getIme().equals("Ime ne sme da bude prazno!") ||  ((Pacijent)patient).getIme().equals("Ime sme da sadrzi samo slova!"))
            throw new PatientException("Nisu validni podaci za pacijenta!");
        if(((Pacijent)patient).getPrezime().isEmpty() || ((Pacijent)patient).getPrezime().equals("Unesite prezime...") 
                || ((Pacijent)patient).getPrezime().equals("Prezime ne sme da bude prazno!") || ((Pacijent)patient).getPrezime().equals("Prezime sme da sadrzi samo slova!"))
            throw new PatientException("Nisu validni podaci za pacijenta!");
        if(((Pacijent)patient).getEmail().isEmpty() || ((Pacijent)patient).getEmail().equals("Unesite email...") 
                || ((Pacijent)patient).getEmail().equals("Email ne sme da bude prazan!") || ((Pacijent)patient).getEmail().equals("Email mora da bude u validnom email obliku!"))
            throw new PatientException("Nisu validni podaci za pacijenta!");
        if(((Pacijent)patient).getAdresa().isEmpty() || ((Pacijent)patient).getAdresa().equals("Unesite adresu...") 
                || ((Pacijent)patient).getAdresa().equals("Adresa ne sme da bude prazna!") || ((Pacijent)patient).getAdresa().equals("Adresa sme da sadrzi samo slova!"))
            throw new PatientException("Nisu validni podaci za pacijenta!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        repository.dodaj((Pacijent)param);
    }
}
