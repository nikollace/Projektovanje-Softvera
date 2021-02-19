/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.io.Serializable;
import java.net.InetAddress;
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
public class Stomatolog implements OpstiDomenskiObjekat{
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private String password;
    private Date datumRodjenja;
    private String adresa;
    
    private String status = "Odjavljen";
    private String vremePrijavljivanja="";
    private String vremeOdjavljivanja="";
    private String ipAdresa="";

    public Stomatolog() {
        
    }

    public Stomatolog(Long id, String ime, String prezime, String email, String password, Date datumRodjenja, String adresa) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
    }
    
    public Stomatolog(String ime, String prezime, String email, String password, Date datumRodjenja, String adresa) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
    }
    
    public Stomatolog(Long id){
        this.id = id;
    }

    public String getVremePrijavljivanja() {
        return vremePrijavljivanja;
    }

    public void setVremePrijavljivanja(String vremePrijavljivanja) {
        this.vremePrijavljivanja = vremePrijavljivanja;
    }

    public String getVremeOdjavljivanja() {
        return vremeOdjavljivanja;
    }

    public void setVremeOdjavljivanja(String vremeOdjavljivanja) {
        this.vremeOdjavljivanja = vremeOdjavljivanja;
    }

    public String getIpAdresa() {
        return ipAdresa;
    }

    public void setIpAdresa(String ipAdresa) {
        this.ipAdresa = ipAdresa;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        final Stomatolog other = (Stomatolog) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String vratiImeTabele() {
        return "stomatolog";
    }
    
     @Override
    public String vratiUslov() {
        return "id = " + id;
    }

    @Override
    public String vratiSlozenUslov() {
        return "email ='" + email + "' AND password='" + password + "'";
    }

    @Override
    public String vratiVrednostiAtributa() {
        return "'" + ime + "'" + ", " + "'" + prezime + "'" + ", " + "'" + email + "'" + ", " + "'" + password + "'"
                + ", " + "'" + new java.sql.Date(konvert(datumRodjenja).getTime()) + "'" + ", " + "'" + adresa + "'";
    }

    @Override
    public String postaviVrednostiAtributa() {
        return "ime=" + "'" + ime + "'" + ", " + "prezime=" + "'" + prezime + "'" + ", " + "email=" 
                + "'" + email + "'" + ", " + "password=" + "'" + password + "'"
                + ", " + "adresa=" + "'" + adresa + "'"+ ", " + "datumrodjenja=" + "'" + new java.sql.Date(konvert(datumRodjenja).getTime()) + "'";
    }

    @Override
    public OpstiDomenskiObjekat vratiNoviKursor(ResultSet rs) throws SQLException {
        return new Stomatolog(rs.getLong("id"), rs.getString("ime"), rs.getString("prezime")
                , rs.getString("email"), rs.getString("password"), rs.getDate("datumrodjenja"), rs.getString("adresa"));
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
        return "stomatolog";
    }

    @Override
    public String vratiImenaKolonaZaUnos() {
        return "ime, prezime, email, password, datumrodjenja, adresa";
    }

    @Override
    public String vratiAtributeZaPronalazanje() {
        return "*";
    }

    @Override
    public void postaviNoviKursor(OpstiDomenskiObjekat entity) {
        Stomatolog privremeni = (Stomatolog)entity;
        this.id = privremeni.getId();
        this.ime = privremeni.getIme();
        this.prezime = privremeni.getPrezime();
        this.email = privremeni.getEmail();
        this.adresa = privremeni.getAdresa();
        this.datumRodjenja = privremeni.getDatumRodjenja();
    }

   
}
