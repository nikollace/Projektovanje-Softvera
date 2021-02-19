/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.crud.AddException;
import rs.ac.bg.fon.ps.exceptions.crud.DeleteException;
import rs.ac.bg.fon.ps.exceptions.crud.EditException;
import rs.ac.bg.fon.ps.exceptions.crud.GetException;
import rs.ac.bg.fon.ps.repository.db.DbConnectionFactory;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.repository.db.DbRepozitorijum;

/**
 *
 * @author nikollace
 */
public class RepositoryDBGeneric implements DbRepozitorijum<OpstiDomenskiObjekat> {

    @Override
    public void dodaj(OpstiDomenskiObjekat entity) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "INSERT INTO " + entity.vratiImeTabele() + " (" + entity.vratiImenaKolonaZaUnos() + ")" + " VALUES (" + entity.vratiVrednostiAtributa() + ")";

            System.out.println(query);

            PreparedStatement pstatement = connection.prepareStatement(query);
            pstatement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = pstatement.getGeneratedKeys();
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                entity.postaviId(id);
            }
            pstatement.close();
            rsKey.close();
        } catch (SQLException ex) {
            throw new AddException("Greska prilikom ubacivanja " + entity.vratiImeTabele() + " u bazu!", ex);
        }
    }

    @Override
    public void izmeni(OpstiDomenskiObjekat entity) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "UPDATE " + entity.vratiImeTabele() + " SET " + entity.postaviVrednostiAtributa() + " WHERE " + entity.vratiUslov();
            System.out.println(query);
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.executeUpdate();
            pstat.close();
        } catch (SQLException ex) {
            throw new EditException("Greska prilikom azuriranja " + entity.vratiImeTabele() + "!", ex);
        }
    }

    @Override
    public void obrisi(OpstiDomenskiObjekat entity) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "DELETE FROM " + entity.vratiImeTabele() + " WHERE " + entity.vratiUslov();

            System.out.println(query);

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException ex) {
            throw new DeleteException("Greska prilikom brisanja " + entity.vratiImeTabele() + " iz baze!", ex);
        }
    }

    @Override
    public void nadjiSve(OpstiDomenskiObjekat entity, List<OpstiDomenskiObjekat> list) throws CRUDException {
        try {
            
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "SELECT " + entity.vratiAtributeZaPronalazanje() + " FROM " + entity.vratiSlozenoImeTabele();
            System.out.println(query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                entity = entity.vratiNoviKursor(rs);

                list.add(entity);
            }
            //statement.close();
        } catch (SQLException ex) {
            throw new GetException("Greska prilikom citanja " + entity.vratiImeTabele() + " iz baze!", ex);
        }
    }
    
    @Override
    public void nadjiSveSaUslovom(OpstiDomenskiObjekat entity, List<OpstiDomenskiObjekat> list) throws CRUDException {
        list.clear();
        try {
            
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "SELECT " + entity.vratiAtributeZaPronalazanje() + " FROM " + entity.vratiSlozenoImeTabele() + " HAVING " + entity.vratiSlozenUslov();
            System.out.println(query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                entity = entity.vratiNoviKursor(rs);

                list.add(entity);
            }
            statement.close();
        } catch (SQLException ex) {
            throw new GetException("Greska prilikom citanja " + entity.vratiImeTabele() + " iz baze!", ex);
        }
    }

    @Override
    public void ucitaj(OpstiDomenskiObjekat entity) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "SELECT " + entity.vratiAtributeZaPronalazanje() + " FROM " + entity.vratiSlozenoImeTabele() + " WHERE " + entity.vratiSlozenUslov();

            System.out.println(query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                entity.postaviNoviKursor(entity.vratiNoviKursor(rs));
                statement.close();
            } else {
                statement.close();
                throw new CRUDException("That entity doesn't exist!");
            }
           
        } catch (SQLException ex) {
            throw new GetException("Greska prilikom citanja " + entity.vratiImeTabele() + " iz baze!", ex);
        }
    }
}
