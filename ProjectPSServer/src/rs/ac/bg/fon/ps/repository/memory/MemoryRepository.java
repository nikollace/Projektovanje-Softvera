/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repozitorijum;

/**
 *
 * @author nikollace
 */
public interface MemoryRepository<T> extends Repozitorijum<T>{

    public default void connect() throws SQLException {
        
    }

    public default void disconnect() throws SQLException {
        
    }

    public default void commit() throws SQLException {
    }

    public default void rollback() throws SQLException {
    } 
}
