/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.validation;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmPatient;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author nikollace
 */
public class Validate implements Validation {
   
    @Override
    public void validate(FrmPatient frmPatient, String pattern, String variable) {

        switch (variable) {
            case "Firstname":
                firstname(frmPatient, pattern);
                break;
            case "Lastname":
                lastname(frmPatient, pattern);
                break;
            case "Email":
                email(frmPatient, pattern);
                break;
            case "Address":
                address(frmPatient, pattern);
                break;
            case "FirstnameClick":
                firstnameClick(frmPatient);
                break;
            case "LastnameClick":
                lastnameClick(frmPatient);
                break;
            case "EmailClick":
                emailClick(frmPatient);
                break;
            case "AddressClick":
                addressClick(frmPatient);
                break;
            default: System.out.println("Ne radi validate switch!!");
        }
    }
    
    @Override
    public void formModeSetter(FrmPatient frmPatient, FormMode formMode) {
        switch(formMode){
            case FORM_ADD:
                frmPatient.getBtnCancel().setVisible(true);
                frmPatient.getBtnDelete().setVisible(false);
                frmPatient.getBtnEdit().setVisible(false);
                frmPatient.getBtnEnableChanges().setVisible(false);
                frmPatient.getBtnSave().setVisible(true);

                frmPatient.getTxtFirstName().setEnabled(true);
                frmPatient.getTxtLastName().setEnabled(true);
                frmPatient.getTxtEmail().setEnabled(true);
                frmPatient.getTxtAddress().setEnabled(true);
                frmPatient.getTxtID().setVisible(false);
                frmPatient.getLblId().setVisible(false);
                break;
            case FORM_VIEW:
                frmPatient.getBtnCancel().setVisible(true);
                frmPatient.getBtnDelete().setVisible(true);
                frmPatient.getBtnEdit().setVisible(false);
                frmPatient.getBtnEnableChanges().setVisible(true);
                frmPatient.getBtnSave().setVisible(false);

                //zabrani izmenu vrednosti
                frmPatient.getTxtFirstName().setEnabled(false);
                frmPatient.getTxtLastName().setEnabled(false);
                frmPatient.getTxtEmail().setEnabled(false);
                frmPatient.getTxtAddress().setEnabled(false);
                frmPatient.getDateDatumRodjenja().setEnabled(false);
                frmPatient.getTxtID().setVisible(false);
                frmPatient.getLblId().setVisible(false);

                //get product
                Pacijent patient = (Pacijent) MainCordinator.getInstance().getParam(Constants.PARAM_PATIENT);
                frmPatient.getTxtFirstName().setText(patient.getIme() + "");
                frmPatient.getTxtLastName().setText(patient.getPrezime());
                frmPatient.getTxtEmail().setText(patient.getEmail());
                frmPatient.getTxtAddress().setText(patient.getAdresa());
                frmPatient.getDateDatumRodjenja().setDate(patient.getDatumRodjenja());
                frmPatient.getTxtID().setText(String.valueOf(patient.getId()));
                break;
            case FORM_EDIT:
                frmPatient.getBtnCancel().setVisible(true);
                frmPatient.getBtnDelete().setVisible(false);
                frmPatient.getBtnEdit().setVisible(true);
                frmPatient.getBtnEnableChanges().setVisible(false);
                frmPatient.getBtnSave().setVisible(false);

                //zabrani izmenu vrednosti
                frmPatient.getTxtFirstName().setEnabled(true);
                frmPatient.getTxtLastName().setEnabled(true);
                frmPatient.getTxtEmail().setEnabled(false);
                frmPatient.getTxtAddress().setEnabled(true);
                frmPatient.getDateDatumRodjenja().setEnabled(true);
                frmPatient.getTxtID().setVisible(false);
                frmPatient.getLblId().setVisible(false);
                break;
        }
    }
    
    public boolean blokirajSave(FrmPatient frmPatient){
        if (frmPatient.getTxtFirstName().getText().trim().isEmpty()
            || frmPatient.getTxtFirstName().getText().equals("Enter your firstname...")
            || frmPatient.getTxtFirstName().getText().equals("Firstname shouldn't be empty!")
            || frmPatient.getTxtFirstName().getText().equals("Firstname must have only letters!")
            || frmPatient.getTxtFirstName().getText().equals("Ime ne sme da bude prazno!")
            || frmPatient.getTxtFirstName().getText().equals("Unesite ime...")
            || frmPatient.getTxtFirstName().getText().equals("Ime sme da sadrzi samo slova!")
            || frmPatient.getTxtLastName().getText().trim().isEmpty()
            || frmPatient.getTxtLastName().getText().equals("Enter your lastname...")
            || frmPatient.getTxtLastName().getText().equals("Last name shouldn't be empty!")
            || frmPatient.getTxtLastName().getText().equals("Lastname must have only letters!")
            || frmPatient.getTxtLastName().getText().equals("Prezime ne sme da bude prazno!")
            || frmPatient.getTxtLastName().getText().equals("Unesite prezime...")
            || frmPatient.getTxtLastName().getText().equals("Prezime sme da sadrzi samo slova!")   
            || frmPatient.getTxtAddress().getText().trim().isEmpty()
            || frmPatient.getTxtAddress().getText().equals("Enter your address...")
            || frmPatient.getTxtAddress().getText().equals("Address shouldn't be empty!")
            || frmPatient.getTxtAddress().getText().equals("Address must have only letters and numbers!")
            || frmPatient.getTxtAddress().getText().equals("Adresa ne sme da bude prazna!")
            || frmPatient.getTxtAddress().getText().equals("Unesite adresu...")
            || frmPatient.getTxtAddress().getText().equals("Adresa sme da sadrzi samo slova i brojeve!")
            || frmPatient.getTxtEmail().getText().trim().isEmpty()
            || frmPatient.getTxtEmail().getText().equals("Enter your address...")
            || frmPatient.getTxtEmail().getText().equals("Email shouldn't be empty!")
            || frmPatient.getTxtEmail().getText().equals("Email should be in valid form!")
            || frmPatient.getTxtEmail().getText().equals("Email ne sme da bude prazan!")
            || frmPatient.getTxtEmail().getText().equals("Unesite email...")
            || frmPatient.getTxtEmail().getText().equals("Email mora bude u validnom email obliku!")    ){
            return true;
        }
        return false;
    }

    public void firstname(FrmPatient frmPatient, String pattern) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtFirstName().getText() == null || frmPatient.getTxtFirstName().getText().trim().isEmpty()
                    || frmPatient.getTxtFirstName().getText().equals("Enter your firstname...")) {
                frmPatient.getTxtFirstName().setText("Firstname shouldn't be empty!");
                frmPatient.getTxtFirstName().setForeground(Color.red);
            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtFirstName().getText().equals("Firstname shouldn't be empty!")) {
                    m = p.matcher(frmPatient.getTxtFirstName().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmPatient.getTxtFirstName().setText("Firstname must have only letters!");
                        frmPatient.getTxtFirstName().setForeground(Color.red);
                    }
                }
            }
        } else {
            if (frmPatient.getTxtFirstName().getText() == null || frmPatient.getTxtFirstName().getText().trim().isEmpty()
                    || frmPatient.getTxtFirstName().getText().equals("Unesite ime...")) {
                frmPatient.getTxtFirstName().setText("Ime ne sme da bude prazno!");
                frmPatient.getTxtFirstName().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtFirstName().getText().equals("Ime ne sme da bude prazno!")) {
                    m = p.matcher(frmPatient.getTxtFirstName().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmPatient.getTxtFirstName().setText("Ime sme da sadrzi samo slova!");
                        frmPatient.getTxtFirstName().setForeground(Color.red);

                    }
                }
            }
        }
    }

    private void lastname(FrmPatient frmPatient, String pattern) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtLastName() == null || frmPatient.getTxtLastName().getText().trim().isEmpty()
                    || frmPatient.getTxtLastName().getText().equals("Enter lastname...")) {
                frmPatient.getTxtLastName().setText("Last name should't be empty!");
                frmPatient.getTxtLastName().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtLastName().getText().equals("Last name should't be empty!")) {
                    m = p.matcher(frmPatient.getTxtLastName().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmPatient.getTxtLastName().setText("Lastname must have only letters!");
                        frmPatient.getTxtLastName().setForeground(Color.red);
                    }
                }
            }
        } else {
            if (frmPatient.getTxtLastName() == null || frmPatient.getTxtLastName().getText().trim().isEmpty()
                    || frmPatient.getTxtLastName().getText().equals("Unesite prezime...")) {
                frmPatient.getTxtLastName().setText("Prezime ne sme da bude prazno!");
                frmPatient.getTxtLastName().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtLastName().getText().equals("Prezime ne sme da bude prazno!")) {
                    m = p.matcher(frmPatient.getTxtLastName().getText().trim());
                    boolean b = m.find();
                    if (b) {
                        frmPatient.getTxtLastName().setText("Prezime sme da sadrzi samo slova!");
                        frmPatient.getTxtLastName().setForeground(Color.red);
                    }
                }
            }
        }
    }

    private void email(FrmPatient frmPatient, String pattern) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtEmail() == null || frmPatient.getTxtEmail().getText().trim().isEmpty()
                    || frmPatient.getTxtEmail().getText().equals("Enter your email...")) {
                frmPatient.getTxtEmail().setText("Email shouldn't be empty!");
                frmPatient.getTxtEmail().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtEmail().getText().equals("Email shouldn't be empty!")) {
                    m = p.matcher(frmPatient.getTxtEmail().getText().trim());
                    boolean b = m.find();
                    if (!b) {
                        frmPatient.getTxtEmail().setText("Email should be in valid form!");
                        frmPatient.getTxtEmail().setForeground(Color.red);
                    }
                }
            }
        } else {
            if (frmPatient.getTxtEmail() == null || frmPatient.getTxtEmail().getText().trim().isEmpty()
                    || frmPatient.getTxtEmail().getText().equals("Unesite email...")) {
                frmPatient.getTxtEmail().setText("Email ne sme da bude prazan!");
                frmPatient.getTxtEmail().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtEmail().getText().equals("Email ne sme da bude prazan!")) {
                    m = p.matcher(frmPatient.getTxtEmail().getText().trim());
                    boolean b = m.find();
                    if (!b) {
                        frmPatient.getTxtEmail().setText("Email mora bude u validnom email obliku!");
                        frmPatient.getTxtEmail().setForeground(Color.red);
                    }
                }
            }
        }
    }

    private void address(FrmPatient frmPatient, String pattern) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtAddress() == null || frmPatient.getTxtAddress().getText().trim().isEmpty()
                    || frmPatient.getTxtAddress().getText().equals("Enter your address...")) {
                frmPatient.getTxtAddress().setText("Address shouldn't be empty!");
                frmPatient.getTxtAddress().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtAddress().getText().equals("Address shouldn't be empty!")) {
                    m = p.matcher(frmPatient.getTxtAddress().getText().trim());
                    boolean b = m.find();
                    if (!b) {
                        frmPatient.getTxtAddress().setText("Address must have only letters and numbers!");
                        frmPatient.getTxtAddress().setForeground(Color.red);
                    }
                }
            }
        } else {
            if (frmPatient.getTxtAddress() == null || frmPatient.getTxtAddress().getText().trim().isEmpty()
                    || frmPatient.getTxtAddress().getText().equals("Unesite adresu...")) {
                frmPatient.getTxtAddress().setText("Adresa ne sme da bude prazna!");
                frmPatient.getTxtAddress().setForeground(Color.red);

            } else {
                Pattern p = Pattern.compile(pattern);
                Matcher m;
                if (!frmPatient.getTxtAddress().getText().equals("Adresa ne sme da bude prazna!")) {
                    m = p.matcher(frmPatient.getTxtAddress().getText().trim());
                    boolean b = m.find();
                    if (!b) {
                        frmPatient.getTxtAddress().setText("Adresa sme da sadrzi samo slova i brojeve!");
                        frmPatient.getTxtAddress().setForeground(Color.red);
                    }
                }
            }
        }
    }

    private void firstnameClick(FrmPatient frmPatient) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtFirstName().getText().equals("Firstname must have only letters!")
                    || frmPatient.getTxtFirstName().getText().equals("Enter your firstname...")
                    || frmPatient.getTxtFirstName().getText().equals("Firstname shouldn't be empty!")) {
                frmPatient.getTxtFirstName().setText("");
                frmPatient.getTxtFirstName().setForeground(Color.black);
            }
        } else {
            if (frmPatient.getTxtFirstName().getText().equals("Ime sme da sadrzi samo slova!")
                    || frmPatient.getTxtFirstName().getText().equals("Unesite ime...")
                    || frmPatient.getTxtFirstName().getText().equals("Ime ne sme da bude prazno!")) {
                frmPatient.getTxtFirstName().setText("");
                frmPatient.getTxtFirstName().setForeground(Color.black);
            }
        }
    }

    private void lastnameClick(FrmPatient frmPatient) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtLastName().getText().equals("Lastname must have only letters!")
                    || frmPatient.getTxtLastName().getText().equals("Enter your lastname...")
                    || frmPatient.getTxtLastName().getText().equals("Lastname shouldn't be empty!")) {
                frmPatient.getTxtLastName().setText("");
                frmPatient.getTxtLastName().setForeground(Color.black);
            }
        } else {
            if (frmPatient.getTxtLastName().getText().equals("Prezime sme da sadrzi samo slova!")
                    || frmPatient.getTxtLastName().getText().equals("Unesite prezime...")
                    || frmPatient.getTxtLastName().getText().equals("Prezime ne sme da bude prazno!")) {
                frmPatient.getTxtLastName().setText("");
                frmPatient.getTxtLastName().setForeground(Color.black);
            }
        }
    }

    private void emailClick(FrmPatient frmPatient) {

        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtEmail().getText().equals("Email should be in valid form!")
                    || frmPatient.getTxtEmail().getText().equals("Enter your email...")
                    || frmPatient.getTxtEmail().getText().equals("Email shouldn't be empty!")) {
                frmPatient.getTxtEmail().setText("");
                frmPatient.getTxtEmail().setForeground(Color.black);
            }
        } else {
            if (frmPatient.getTxtEmail().getText().equals("Email mora bude u validnom email obliku!")
                    || frmPatient.getTxtEmail().getText().equals("Unesite email...")
                    || frmPatient.getTxtEmail().getText().equals("Email ne sme da bude prazan!")) {
                frmPatient.getTxtEmail().setText("");
                frmPatient.getTxtEmail().setForeground(Color.black);
            }
        }
    }

    private void addressClick(FrmPatient frmPatient) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmPatient.getTxtAddress().getText().equals("Address must have only letters and numbers!")
                    || frmPatient.getTxtAddress().getText().equals("Enter your address...")
                    || frmPatient.getTxtAddress().getText().equals("Address shouldn't be empty!")) {
                frmPatient.getTxtAddress().setText("");
                frmPatient.getTxtAddress().setForeground(Color.black);
            }
        } else {
            if (frmPatient.getTxtAddress().getText().equals("Adresa mora da sadrzi slova!")
                    || frmPatient.getTxtAddress().getText().equals("Unesite adresu...")
                    || frmPatient.getTxtAddress().getText().equals("Adresa ne sme da bude prazna!") 
                    || frmPatient.getTxtAddress().getText().equals("Adresa sme da sadrzi samo slova!")) {
                frmPatient.getTxtAddress().setText("");
                frmPatient.getTxtAddress().setForeground(Color.black);
            }
        }
    }
}
