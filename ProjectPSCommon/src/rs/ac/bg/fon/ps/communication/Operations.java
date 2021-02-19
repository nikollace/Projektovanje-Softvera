/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.Serializable;

/**
 *
 * @author nikollace
 */
public enum Operations implements Serializable{
    REGISTER,
    LOGIN,
    LOGOUT,
    GET_ALL_PATIENTS,
    GET_ALL_DENTISTS,
    GET_ALL_TERMINS,
    GET_ALL_MEDICAL_EXAMINATIONS,
    GET_ALL_MEDICAL_EXAMINATION_ITEMS,
    ADD_PATIENT,
    DELETE_PATIENT,
    EDIT_PATIENT,
    ADD_TERMIN,
    EDIT_TERMIN,
    DELETE_TERMIN,
    ADD_MEDICAL_EXAMINATION,
    EDIT_MEDICAL_EXAMINATION,
    DELETE_MEDICAL_EXAMINATION
}
