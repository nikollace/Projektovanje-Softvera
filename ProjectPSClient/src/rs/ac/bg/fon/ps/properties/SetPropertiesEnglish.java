/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author nikollace
 */
public class SetPropertiesEnglish {
    public static void main(String[] args) {
        Properties prop = new Properties();
	OutputStream output = null;

	try {

		output = new FileOutputStream("config_english.properties");

		// set the properties value
                //Login form
		prop.setProperty("lblEmail", "Email:");
		prop.setProperty("lblPassword", "Password:");
		prop.setProperty("btnLogin", "Login");
                prop.setProperty("cbShowPassword", "Show password");
                prop.setProperty("txtEmail", "Enter your email...");
                prop.setProperty("txtPassword", "Enter your password...");
                //Register form
                prop.setProperty("lblFirstName", "Firstname:");
		prop.setProperty("lblLastName", "Lastname:");
		prop.setProperty("lblEmail", "Email:");
                prop.setProperty("lblPassword", "Password:");
                prop.setProperty("lblConfirmPassword", "Confirm password:");
                prop.setProperty("lblAddress", "Address:");
                prop.setProperty("lblDateBirth", "Date birth:");
                prop.setProperty("btnRegister", "Register");
                prop.setProperty("btnStartForm", "Start form");
                //Main form
                prop.setProperty("menuPatient", "Patient");
		prop.setProperty("miPatientNew", "Add new");
		prop.setProperty("miShowPatient", "Show all");
                prop.setProperty("menuMedicalExamination", "Medical Examination");
                prop.setProperty("miScheduleMedicalExamination", "Schedule a medical examination");
                prop.setProperty("miShowMedicalExamination", "Show all");
                prop.setProperty("menuCurrentUser", "Currently using app");
                prop.setProperty("menuAboutUs", "About Us");
                //Patient form
                prop.setProperty("lblFirstName", "Firstname:");
		prop.setProperty("lblLastName", "Lastname:");
		prop.setProperty("lblEmail", "Email:");
                prop.setProperty("lblAddress", "Address:");
                prop.setProperty("lblDateBirth", "Date birth:");
                prop.setProperty("txtFirstName", "Enter your firstname...");
                prop.setProperty("txtLastName", "Enter your lastname...");
                prop.setProperty("txtEmail", "Enter your email...");
                prop.setProperty("txtAddress", "Enter your address...");
                prop.setProperty("btnSave", "Save");
                prop.setProperty("btnEnableChanges", "Enable changes");
                prop.setProperty("btnDelete", "Delete");
                prop.setProperty("btnEdit", "Edit");
                prop.setProperty("btnCancel", "Cancel");
                //Medical Examination form
                prop.setProperty("lblId", "ID:");
		prop.setProperty("lblTotalPrice", "Total price:");
		prop.setProperty("lblTotalDuration", "Total duration:");
                prop.setProperty("lblDentist", "Dentist:");
                prop.setProperty("lblPatient", "Choose patient:");
                prop.setProperty("lblTerminStart", "Start:");
                prop.setProperty("lblTerminEnd", "End:");
                prop.setProperty("lblDateTermin", "Date:");
                prop.setProperty("btnSaveTermin", "Save termin");
                prop.setProperty("btnDeleteItem", "Delete item");
                prop.setProperty("lblType", "Type:");
                prop.setProperty("lblPrice", "Price:");
                prop.setProperty("lblDuration", "Duration:");
                prop.setProperty("lblDescription", "Description:");
                prop.setProperty("btnAddItem", "Add item");
                prop.setProperty("btnEnableChanges", "Enable changes");
                prop.setProperty("btnEdit", "Edit");
                prop.setProperty("btnDelete", "Delete");
                prop.setProperty("btnCancel", "Cancel");
                prop.setProperty("btnSave", "Save");
                prop.setProperty("btnScheduledTermins", "Termins");
                
                //Scheduled Termins Form
                prop.setProperty("lblSearchValue", "Enter value to find termin:");
                prop.setProperty("btnCancel", "Cancel");
                //Medical Examination View All form
                prop.setProperty("btnDetails", "Details");
		prop.setProperty("btnCancel", "Cancel");
                prop.setProperty("lblSearch", "Enter value to find medical examination:");
                //Patient View All form
                prop.setProperty("btnDetails", "Details");
		prop.setProperty("btnCancel", "Cancel");
                prop.setProperty("lblSearchPatient", "Enter value to find patient:");
                
                //All JOptionPanes
                prop.setProperty("btnDetails", "Details");
		prop.setProperty("btnCancel", "Cancel");
                prop.setProperty("lblSearch", "Enter value to find medical examination:");
		// save properties to project root folder
		prop.store(output, null);

	} catch (IOException io) {
		
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				
			}
		}

	}
    }
}
