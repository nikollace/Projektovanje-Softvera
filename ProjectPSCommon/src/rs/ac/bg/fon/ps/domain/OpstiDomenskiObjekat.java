/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nikollace
 */
public interface OpstiDomenskiObjekat extends Serializable{
    
    String vratiImeTabele();
    
    void postaviId(Long id);
    
    String vratiSlozenUslov();
    
    String vratiUslov();
    
    String vratiSlozenoImeTabele();
    
    String vratiImenaKolonaZaUnos();
    
    String vratiAtributeZaPronalazanje();

    String vratiVrednostiAtributa();
    
    String postaviVrednostiAtributa();
    
    OpstiDomenskiObjekat vratiNoviKursor(ResultSet rs) throws SQLException;
    
    public void postaviNoviKursor(OpstiDomenskiObjekat entity);

}
