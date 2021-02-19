/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBGeneric;
import rs.ac.bg.fon.ps.repository.Repozitorijum;
import rs.ac.bg.fon.ps.repository.db.DbRepozitorijum;

/**
 *
 * @author nikollace
 */
public abstract class GenerickaOperacija {
    protected final Repozitorijum repository;

    public GenerickaOperacija() {
        this.repository = new RepositoryDBGeneric();
    }
    
    public final void execute(Object param) throws Exception{
        try{
            preduslovi(param);
            pokreniTransakciju();
            izvrsiOperaciju(param);
            potvrdiTransakciju();
        } catch(Exception ex){
            ex.printStackTrace();
            ponistiTransakciju();
            throw ex;
        }
        finally{
            prekiniKonekciju();
        }
    }

    protected abstract void preduslovi(Object param) throws Exception;

    private void pokreniTransakciju() throws SQLException{
        ((DbRepozitorijum)repository).povezi();
    }

    protected abstract void izvrsiOperaciju(Object param) throws Exception;

    private void potvrdiTransakciju() throws SQLException {
        ((DbRepozitorijum)repository).potvrdiTransakciju();
    }

    private void ponistiTransakciju() throws SQLException {
        ((DbRepozitorijum)repository).ponistiTransakciju();
    }

    private void prekiniKonekciju() throws SQLException {
        ((DbRepozitorijum)repository).prekiniVezu();
    }
}
