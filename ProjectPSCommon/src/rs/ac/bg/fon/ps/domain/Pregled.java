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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nikollace
 */
public class Pregled implements OpstiDomenskiObjekat{
    private Long id;
    private double ukupnaCena;
    private double ukupnoTrajanje;
    private Stomatolog stomatolog;
    private Pacijent pacijent;
    private Termin termin;
    
    private List<StavkaPregleda> stavkePregleda;
    
    public Pregled() {
        this.stavkePregleda = new ArrayList<>();
        ukupnaCena = 0;
    }
    
    public Pregled(Long id){
        this.id = id;
    }

    public Pregled(Long id, double ukupnaCena, double ukupnoTrajanje, Stomatolog stomatolog, Pacijent pacijent, Termin termin) {
        this.id = id;
        this.ukupnaCena = ukupnaCena;
        this.ukupnoTrajanje = ukupnoTrajanje;
        this.stomatolog = stomatolog;
        this.pacijent = pacijent;
        this.termin = termin;
    }

    public double getUkupnoTrajanje() {
        return ukupnoTrajanje;
    }

    public void setUkupnoTrajanje(double ukupnoTrajanje) {
        this.ukupnoTrajanje = ukupnoTrajanje;
    }

    public Long getId() {
        return id;
    }

    public void postaviId(Long id) {
        this.id = id;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Stomatolog getStomatolog() {
        return stomatolog;
    }

    public void setStomatolog(Stomatolog stomatolog) {
        this.stomatolog = stomatolog;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public List<StavkaPregleda> getStavkePregleda() {
        return stavkePregleda;
    }

    public void setStavkePregleda(List<StavkaPregleda> stavkePregleda) {
        this.stavkePregleda = stavkePregleda;
    }
    
    @Override
    public String toString() {
        return "Pregled izvrsio: " + stomatolog.getIme() +" " + stomatolog.getPrezime() +
                "\nPregledani pacijent: " + pacijent.getIme() + " " + pacijent.getPrezime() +
                "\nUkupna cena: " + ukupnaCena + "\tUkupno trajanje: " + ukupnoTrajanje;
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
        final Pregled other = (Pregled) obj;
        if (Double.doubleToLongBits(this.ukupnaCena) != Double.doubleToLongBits(other.ukupnaCena)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ukupnoTrajanje) != Double.doubleToLongBits(other.ukupnoTrajanje)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.stomatolog, other.stomatolog)) {
            return false;
        }
        if (!Objects.equals(this.pacijent, other.pacijent)) {
            return false;
        }
        if (!Objects.equals(this.termin, other.termin)) {
            return false;
        }
        return true;
    }

    @Override
    public String vratiImeTabele() {
        return "pregled";
    }
    
    @Override
    public String vratiUslov() {
        return "id = " + id;
    }

    @Override
    public String vratiSlozenUslov() {
        return "stomatolog_id = " + stomatolog.getId();
    }

    @Override
    public String vratiVrednostiAtributa() {
        return ukupnaCena + ", " + ukupnoTrajanje + ", " + stomatolog.getId()
                + ", " + pacijent.getId() + ", " + "'" + new java.sql.Date(termin.getDatumTermina().getTime()) + "'"
                + ", " + "'" + termin.getVremeOd()+ "'";
    }

    @Override
    public String postaviVrednostiAtributa() {
        return "id=" + id + ", " + "ukupnaCena=" + ukupnaCena + ", " + "ukupnoTrajanje=" + ukupnoTrajanje 
                + ", " + "stomatolog=" + stomatolog.getId() + ", " + "pacijent=" + pacijent.getId()
                + ", " + "terminDatum=" + "'" + new java.sql.Date(termin.getDatumTermina().getTime()) 
                + "'" +", " + "terminVremeOd=" + "'" +termin.getVremeOd() + "'";
    }

    @Override
    public OpstiDomenskiObjekat vratiNoviKursor(ResultSet rs) throws SQLException {
        return new Pregled(rs.getLong("pregled_id"), rs.getDouble("pregled_ukupnaCena"), rs.getDouble("pregled_ukupnoTrajanje"),
                new Stomatolog(rs.getLong("stomatolog_id"), rs.getString("stomatolog_ime"), 
                        rs.getString("stomatolog_prezime"), rs.getString("stomatolog_email"),
                        rs.getString("stomatolog_password"), rs.getDate("stomatolog_datumRodjenja"), rs.getString("stomatolog_adresa")),
                new Pacijent(rs.getLong("pacijent_id"), rs.getString("pacijent_ime"), 
                        rs.getString("pacijent_prezime"), rs.getString("pacijent_email"),
                        rs.getDate("pacijent_datumRodjenja"), rs.getString("pacijent_adresa")), 
                new Termin(rs.getDate("termin_datum"), rs.getString("termin_vremeOd"), rs.getString("termin_vremeDo")));
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
        return "pregled\n" +
                "JOIN stomatolog\n" +
                "ON pregled.stomatolog = stomatolog.id\n" +
                "JOIN pacijent\n" +
                "ON pregled.pacijent = pacijent.id\n" +
                "JOIN termin\n" +
                "ON pregled.terminDatum = termin.datum\n" +
                "AND pregled.terminVremeOd = termin.vremeOd";
    }

    @Override
    public String vratiImenaKolonaZaUnos() {
        return "ukupnaCena, ukupnoTrajanje, stomatolog, pacijent, terminDatum, terminVremeOd";
    }

    @Override
    public String vratiAtributeZaPronalazanje() {
        return "pregled.id AS 'pregled_id', \n" +
                            "pregled.ukupnaCena AS 'pregled_ukupnaCena',\n" +
                            "pregled.ukupnoTrajanje AS 'pregled_ukupnoTrajanje',\n" +
                            "stomatolog.id AS 'stomatolog_id'," +
                            "stomatolog.ime AS 'stomatolog_ime',\n" +
                            "stomatolog.prezime AS 'stomatolog_prezime',\n" +
                            "stomatolog.email AS 'stomatolog_email',\n" +
                            "stomatolog.password AS 'stomatolog_password',\n" +
                            "stomatolog.datumRodjenja AS 'stomatolog_datumRodjenja',\n" +
                            "stomatolog.adresa AS 'stomatolog_adresa',\n" +
                            "pacijent.id AS 'pacijent_id',\n" +
                            "pacijent.ime AS 'pacijent_ime',\n" +
                            "pacijent.prezime AS 'pacijent_prezime',\n" +
                            "pacijent.email AS 'pacijent_email',\n" +
                            "pacijent.adresa AS 'pacijent_adresa',\n" +
                            "pacijent.datumRodjenja AS 'pacijent_datumRodjenja',\n" +
                            "termin.datum AS 'termin_datum',\n" +
                            "termin.vremeOd AS 'termin_vremeOd',\n" +
                            "termin.vremeDo AS 'termin_vremeDo'\n";
    }

    @Override
    public void postaviNoviKursor(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
