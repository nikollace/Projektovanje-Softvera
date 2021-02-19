/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repozitorijum;

/**
 *
 * @author nikollace
 */
public interface DbRepozitorijum<T> extends Repozitorijum<T>{

    public default void povezi() throws SQLException {
        DbConnectionFactory.getInstance().getConnection();
    }

    public default void prekiniVezu() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    public default void potvrdiTransakciju() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().commit();
    }
    
    public default void ponistiTransakciju() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
