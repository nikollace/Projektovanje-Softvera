/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Termin;

/**
 *
 * @author nikollace
 */
public class MemorijskiRepozitorijumTermin {
    private List<Termin> termini;

    public MemorijskiRepozitorijumTermin() {
        termini = new ArrayList<>();
        
    }
    
    public List<Termin> getAll(){
        return termini;
    }
}
