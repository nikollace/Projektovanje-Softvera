/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.medicalItem;

import java.util.List;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.MedicalItemException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class UcitajListuStavki extends GenerickaOperacija {

   @Override
    protected void preduslovi(Object param) throws MedicalItemException {
        if(param == null)
             throw new MedicalItemException("Nisu validni podaci za stavku!");
    }
    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        
        List<StavkaPregleda> stavke = (List<StavkaPregleda>) param;
        repository.nadjiSveSaUslovom(stavke.get(0), stavke);
        
        
        //repository.nadjiSveSaUslovom(new StavkaPregleda(), (List<StavkaPregleda>) param);
    }
    
}
