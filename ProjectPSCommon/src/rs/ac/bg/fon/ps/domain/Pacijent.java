/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.io.Serializable;
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
public class Pacijent implements OpstiDomenskiObjekat{
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private Date datumRodjenja;
    private String adresa;

    public Pacijent() {
    }

    public Pacijent(Long id, String ime, String prezime, String email, Date datumRodjenja, String adresa) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
    }
    
    public Pacijent(Long id){
        this.id = id;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Long getId() {
        return id;
    }

    public void postaviId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
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
        final Pacijent other = (Pacijent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String vratiImeTabele() {
        return "pacijent";
    }
    
    @Override
    public String vratiUslov() {
        return "id = " + id;
    }

    @Override
    public String vratiSlozenUslov() {
        return "id = " + id;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return "'" + ime + "'" + ", " + "'" + prezime + "'" + ", " + "'" + email + "'" 
                + ", " + "'" + adresa + "'" +  ", " + "'" + new java.sql.Date(konvert(datumRodjenja).getTime()) + "'";
    }

    @Override
    public String postaviVrednostiAtributa() {
        return "ime=" + "'" + ime + "'" + ", " + "prezime=" + "'" + prezime + "'" + ", " + "email=" + "'" + email + "'" 
                + ", " + "adresa=" + "'" + adresa + "'" + ", " + "datumrodjenja=" + "'" + new java.sql.Date(konvert(datumRodjenja).getTime()) + "'";
    }

    @Override
    public OpstiDomenskiObjekat vratiNoviKursor(ResultSet rs) throws SQLException {
        return new Pacijent(rs.getLong("id"),rs.getString("ime"),rs.getString("prezime"),rs.getString("email") ,rs.getDate("datumrodjenja"), rs.getString("adresa"));
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
        return "pacijent";
    }

    @Override
    public String vratiAtributeZaPronalazanje() {
        return "*";
    }

    @Override
    public String vratiImenaKolonaZaUnos() {
        return "ime, prezime, email, adresa, datumrodjenja";
    }

    @Override
    public void postaviNoviKursor(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
