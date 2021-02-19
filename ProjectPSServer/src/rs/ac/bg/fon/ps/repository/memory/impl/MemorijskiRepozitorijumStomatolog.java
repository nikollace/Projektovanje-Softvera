/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author nikollace
 */
public class MemorijskiRepozitorijumStomatolog implements MemoryRepository<Stomatolog>{
    private List<Stomatolog> stomatolozi;

    public MemorijskiRepozitorijumStomatolog() {
        stomatolozi = new ArrayList<>();
    }

    @Override
    public void dodaj(Stomatolog param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void izmeni(Stomatolog param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void obrisi(Stomatolog param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nadjiSve(Stomatolog param, List<Stomatolog> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nadjiSveSaUslovom(Stomatolog param, List<Stomatolog> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ucitaj(Stomatolog param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
