/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikollace
 */
public class Termin implements OpstiDomenskiObjekat{
    private Date datumTermina;
    private String vremeOd;
    private String vremeDo;

    public Termin() {
    }

    public Termin(Date datumTermina, String vremeOd, String vremeDo) {
        this.datumTermina = datumTermina;
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }
    
    public Termin(Date datumTermina, String vremeOd) {
        this.datumTermina = datumTermina;
        this.vremeOd = vremeOd;
    }
    
    public String getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(String vremeDo) {
        this.vremeDo = vremeDo;
    }

    public Date getDatumTermina() {
        return datumTermina;
    }

    public void setDatumTermina(Date datumTermina) {
        this.datumTermina = datumTermina;
    }

    public String getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(String vremeOd) {
        this.vremeOd = vremeOd;
    }

    @Override
    public String toString() {
        return "Datum termina: " + datumTermina+ ", /nVreme: /t"+ 
                 vremeOd + " - " + vremeDo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Termin other = (Termin) obj;
        if (!Objects.equals(this.vremeOd, other.vremeOd)) {
            return false;
        }
        if (!Objects.equals(this.datumTermina, other.datumTermina)) {
            return false;
        }
        return true;
    }

    @Override
    public String vratiImeTabele() {
        return "termin";
    }

    @Override
    public void postaviId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String vratiUslov() {
        return "datum = " + datumTermina + " AND vremeOd = " + vremeOd; 
    }

    @Override
    public String vratiSlozenUslov() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "termin.datum >= " + "'" + sdf.format(new Date()) + "'";
    }

    @Override
    public String vratiVrednostiAtributa() {
        return "'" + new java.sql.Date(konvert(datumTermina).getTime()) + "'" + ", " + "'" + vremeOd + "'" + ", " + "'" + vremeDo + "'";
    }

    @Override
    public String postaviVrednostiAtributa() {
        return "datumTermina=" + "'" + new java.sql.Date(konvert(datumTermina).getTime()) + "'" + ", " + "vremeOd=" + "'" + vremeOd + "'" + ", " + "vremeDo=" + "'" + vremeDo + "'"; 
    }

    @Override
    public OpstiDomenskiObjekat vratiNoviKursor(ResultSet rs) throws SQLException {
        return new Termin(rs.getDate("datum"), rs.getString("vremeOd"), rs.getString("vremeDo"));
    }
    
    private Date konvert(Date datum){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datum);
        
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            Logger.getLogger(Stomatolog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String vratiSlozenoImeTabele() {
        return "termin";
    }

    @Override
    public String vratiImenaKolonaZaUnos() {
        return "datum, vremeOd, vremeDo";
    }

    @Override
    public String vratiAtributeZaPronalazanje() {
        return "*";
    }

    @Override
    public void postaviNoviKursor(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
