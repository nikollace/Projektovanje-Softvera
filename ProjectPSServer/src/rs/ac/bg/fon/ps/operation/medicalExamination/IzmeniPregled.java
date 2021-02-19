/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.medicalExamination;

import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.MedicalExaminationException;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;

/**
 *
 * @author nikollace
 */
public class IzmeniPregled  extends GenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws MedicalExaminationException {
        if(param == null || !(param instanceof Pregled))
            throw new MedicalExaminationException("Nisu validni podaci za pregled!");
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws CRUDException {
        repository.izmeni((Pregled)param);
        repository.obrisi((StavkaPregleda)((Pregled)param).getStavkePregleda().get(0));
        for (StavkaPregleda stavkaPregleda : ((Pregled)param).getStavkePregleda()) {
            repository.dodaj(stavkaPregleda);
        }
    }
    
}
