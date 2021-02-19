/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author nikollace
 */
public interface Repozitorijum <T> {    
    public void dodaj(T param) throws CRUDException;
    public void izmeni(T param) throws CRUDException;
    public void obrisi(T param) throws CRUDException;
    public void nadjiSve(T param, List<T> listParam) throws CRUDException;
    public void nadjiSveSaUslovom(T param, List<T> listParam) throws CRUDException;
    public void ucitaj(T param) throws CRUDException;
}
