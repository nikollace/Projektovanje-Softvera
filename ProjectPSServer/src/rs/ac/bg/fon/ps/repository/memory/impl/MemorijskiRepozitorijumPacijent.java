/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.crud.AddException;
import rs.ac.bg.fon.ps.exceptions.crud.DeleteException;

/**
 *
 * @author nikollace
 */
public class MemorijskiRepozitorijumPacijent implements MemoryRepository<Pacijent> {
    private List<Pacijent> pacijenti;

    public MemorijskiRepozitorijumPacijent() {
        pacijenti = new ArrayList<>();
    }
    
    @Override
    public void dodaj(Pacijent param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void izmeni(Pacijent param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void obrisi(Pacijent param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nadjiSve(Pacijent param, List<Pacijent> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nadjiSveSaUslovom(Pacijent param, List<Pacijent> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ucitaj(Pacijent param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
