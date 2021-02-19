/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.medicalExamination;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.MedicalExaminationException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class UcitajListuPregleda extends GenerickaOperacija {

   @Override
    protected void preduslovi(Object param) throws MedicalExaminationException {
        if(param == null)
            throw new MedicalExaminationException("Podaci nisu validni za pregled!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        List<Pregled> pregledi = (List<Pregled>) param;
        repository.nadjiSveSaUslovom(pregledi.get(0), pregledi);
    }
    
}
