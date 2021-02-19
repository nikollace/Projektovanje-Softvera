/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.domain.Termin;
import rs.ac.bg.fon.ps.operation.GenerickaOperacija;
import rs.ac.bg.fon.ps.operation.dentist.DodajStomatologa;
import rs.ac.bg.fon.ps.operation.dentist.UcitajListuStomatologa;
import rs.ac.bg.fon.ps.operation.dentist.UcitajStomatologa;
import rs.ac.bg.fon.ps.operation.medicalExamination.DodajPregled;
import rs.ac.bg.fon.ps.operation.medicalExamination.ObrisiPregled;
import rs.ac.bg.fon.ps.operation.medicalExamination.IzmeniPregled;
import rs.ac.bg.fon.ps.operation.medicalExamination.UcitajListuPregleda;
import rs.ac.bg.fon.ps.operation.medicalItem.ObrisiStavku;
import rs.ac.bg.fon.ps.operation.medicalItem.UcitajListuStavki;
import rs.ac.bg.fon.ps.operation.patient.DodajPacijenta;
import rs.ac.bg.fon.ps.operation.patient.ObrisiPacijenta;
import rs.ac.bg.fon.ps.operation.patient.IzmeniPacijenta;
import rs.ac.bg.fon.ps.operation.patient.UcitajListuPacijenata;
import rs.ac.bg.fon.ps.operation.termin.DodajTermin;
import rs.ac.bg.fon.ps.operation.termin.UcitajListuTermina;

/**
 *
 * @author nikollace
 */
public class Kontroler {
    private static Kontroler instance;

    
    private Kontroler() {
        
    }
    
    public static Kontroler getInstance(){
        if(instance==null)
            instance = new Kontroler();
        return instance;
    }
    
    public void prijava(Stomatolog stomatolog) throws Exception {
        GenerickaOperacija operation = new UcitajStomatologa();
        operation.execute(stomatolog);
    }
    
    public void registracija(Stomatolog stomatolog) throws Exception {
        GenerickaOperacija operation = new DodajStomatologa();
        operation.execute(stomatolog);
    }
    
    public void vratiSveStomatologe(List<Stomatolog> stomatolozi) throws Exception {
        GenerickaOperacija operation = new UcitajListuStomatologa();
        operation.execute(stomatolozi);
    }
    
    public void vratiSvePreglede(List<Pregled> pregledi) throws Exception{
        GenerickaOperacija operation = new UcitajListuPregleda();
        operation.execute(pregledi);
    }
    
    public void vratiSvePacijente(List<Pacijent> pacijenti) throws Exception{
        GenerickaOperacija operation = new UcitajListuPacijenata();
        operation.execute(pacijenti);
    }
    
    public void vratiSveTermine(List<Termin> termini) throws Exception{
        GenerickaOperacija operation = new UcitajListuTermina();
        operation.execute(termini);
    }
    
     public void vratiSveStavke(List<StavkaPregleda> stavke) throws Exception {
        GenerickaOperacija operation = new UcitajListuStavki();
        operation.execute(stavke);
    }
    
    public void dodajPacijenta(Pacijent patient) throws Exception {
         GenerickaOperacija operation = new DodajPacijenta();
         operation.execute(patient);
    }

    public void izmeniPacijenta(Pacijent patient) throws Exception {
        GenerickaOperacija operation = new IzmeniPacijenta();
        operation.execute(patient);
    }

    public void obrisiPacijenta(Pacijent patient) throws Exception {
        GenerickaOperacija operation = new ObrisiPacijenta();
        operation.execute(patient);
    }  
    
    //Termin
    public void dodajTermin(Termin termin) throws Exception {
        GenerickaOperacija operation = new DodajTermin();
        operation.execute(termin);
    }

    public void dodajPregled(Pregled medicalExaminationAdd) throws Exception {
        GenerickaOperacija operation = new DodajPregled();
        operation.execute(medicalExaminationAdd); 
    }

    public void izmeniPregled(Pregled medicalExaminationEdit) throws Exception {
        GenerickaOperacija operation = new IzmeniPregled();
        operation.execute(medicalExaminationEdit);
    }

    public void obrisiPregled(Pregled medicalExaminationDelete) throws Exception {
        GenerickaOperacija operation = new ObrisiPregled();
        operation.execute(medicalExaminationDelete);
        for (StavkaPregleda stavkaPregleda : medicalExaminationDelete.getStavkePregleda()) {
            GenerickaOperacija operation2 = new ObrisiStavku();
            operation2.execute(stavkaPregleda);
        }
    }

   
}
