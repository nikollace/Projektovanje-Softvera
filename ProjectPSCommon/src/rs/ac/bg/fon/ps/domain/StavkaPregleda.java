/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import rs.ac.bg.fon.ps.domain.Pregled;

/**
 *
 * @author nikollace
 */
public class StavkaPregleda implements OpstiDomenskiObjekat{
    private Pregled pregled;
    private int rbr;
    private String nazivPregleda;
    private double cena;
    private double trajanje;
    private String opis;

    public StavkaPregleda() {
    }

    public StavkaPregleda(Pregled pregled, int rbr, String nazivPregleda, double cena, double trajanje, String opis) {
        this.pregled = pregled;
        this.rbr = rbr;
        this.nazivPregleda = nazivPregleda;
        this.cena = cena;
        this.trajanje = trajanje;
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getRbr() {
        return rbr;
    }

    public void setRbr(int rbr) {
        this.rbr = rbr;
    }

    public String getNazivPregleda() {
        return nazivPregleda;
    }

    public void setNazivPregleda(String nazivPregleda) {
        this.nazivPregleda = nazivPregleda;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(double trajanje) {
        this.trajanje = trajanje;
    }

    public Pregled getPregled() {
        return pregled;
    }

    public void setPregled(Pregled pregled) {
        this.pregled = pregled;
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
        final StavkaPregleda other = (StavkaPregleda) obj;
        if (this.rbr != other.rbr) {
            return false;
        }
        if (Double.doubleToLongBits(this.cena) != Double.doubleToLongBits(other.cena)) {
            return false;
        }
        if (Double.doubleToLongBits(this.trajanje) != Double.doubleToLongBits(other.trajanje)) {
            return false;
        }
        if (!Objects.equals(this.nazivPregleda, other.nazivPregleda)) {
            return false;
        }
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        if (!Objects.equals(this.pregled, other.pregled)) {
            return false;
        }
        return true;
    }

    @Override
    public String vratiImeTabele() {
        return "stavkapregleda";
    }

    @Override
    public void postaviId(Long id) {
        pregled.postaviId(id);
    }

    @Override
    public String vratiSlozenUslov() {
        return "pregledId = " + pregled.getId();
    }
    
       @Override
    public String vratiUslov() {
        return "pregledId = " + pregled.getId();
    }

    @Override
    public String vratiVrednostiAtributa() {
        return pregled.getId() + ", " + rbr + ", " + "'" + nazivPregleda+ "'" + ", " + cena
                + ", " + trajanje + ", " + "'" + opis + "'";
    }

    @Override
    public String postaviVrednostiAtributa() {
        return "pregledId=" + pregled.getId() + ", " + "rbr=" + rbr + ", " + "nazivpregleda=" + "'" + nazivPregleda + "'" 
                + ", " + "cena=" + cena + ", " + "trajanje=" + trajanje
                + ", " + "opis=" + "'" + opis + "'";
    }

    @Override
    public OpstiDomenskiObjekat vratiNoviKursor(ResultSet rs) throws SQLException {
        return new StavkaPregleda(new Pregled(rs.getLong("pregledId")), rs.getInt("rbr")
                , rs.getString("nazivpregleda"), rs.getDouble("cena"), rs.getDouble("trajanje"), rs.getString("opis"));
    }

    @Override
    public String vratiSlozenoImeTabele() {
        return "stavkapregleda";
    }

    @Override
    public String vratiImenaKolonaZaUnos() {
        return "pregledId, rbr, nazivpregleda, cena, trajanje, opis";
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
