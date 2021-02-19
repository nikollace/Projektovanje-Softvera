/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmPatient;
import rs.ac.bg.fon.ps.view.util.FormMode;
import rs.ac.bg.fon.ps.view.validation.Validate;

/**
 *
 * @author nikollace
 */
public class PatientController implements SetProperties {

    private final FrmPatient frmPatient;

    public PatientController(FrmPatient frmPatient) {
        this.frmPatient = frmPatient;
        addActionListenersTxt();
        addActionListeners();
    }

    private void addActionListeners() {
        
        frmPatient.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                ProveriSave ps = new ProveriSave();
            }
        });
        
        frmPatient.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateForm();
                
                int reply = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate pacijenta?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    save();
                }
            }

            private void save() {
                try {
                    Pacijent patient = new Pacijent();
                    patient.setIme(frmPatient.getTxtFirstName().getText().trim());
                    patient.setPrezime(frmPatient.getTxtLastName().getText().trim());
                    patient.setEmail(frmPatient.getTxtEmail().getText().trim());
                    patient.setAdresa(frmPatient.getTxtAddress().getText().trim());
                    patient.setDatumRodjenja(frmPatient.getDateDatumRodjenja().getDate());

                    //Controller.getInstance().addPatient(patient);
                    Communication.getInstance().addPatient(patient);
                    JOptionPane.showMessageDialog(frmPatient, "Sistem je zapamtio pacijenta.");
                    frmPatient.dispose();
                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                    //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmPatient, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                    //System.exit(1);
                } catch (Exception ex) {
//                    Logger.getLogger(FrmPatient.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmPatient, "Sistem ne moze da zapamti pacijenta.");
                }
            }
        });

        frmPatient.addEnableChangesBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmPatient.addCancelBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmPatient.dispose();
            }
        });

        frmPatient.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pacijenta?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    delete();
                }
            }

            private void delete() {
                Pacijent patient = makePatientFromForm();

                try {
                    Communication.getInstance().deletePatient(patient);
                    JOptionPane.showMessageDialog(frmPatient, "Patient deleted successfully!\n", "Delete patient", JOptionPane.INFORMATION_MESSAGE);
                    frmPatient.dispose();
                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                    //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmPatient, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                    //System.exit(1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmPatient, "Error deleting patient!\n" + ex.getMessage(), "Delete patient", JOptionPane.ERROR_MESSAGE);
                    //Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmPatient.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate izmene nad pacijentom?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    edit();
                }
            }

            private void edit() {
                Pacijent patient = makePatientFromForm();

                try {
                    Communication.getInstance().editPatient(patient);
                    JOptionPane.showMessageDialog(frmPatient, "Sistem je zapamtio pacijenta!\n", "Edit patient", JOptionPane.INFORMATION_MESSAGE);
                    frmPatient.dispose();
                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                    //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmPatient, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                    // System.exit(1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmPatient, "Sistem ne moze da zapamti pacijenta!\n", "Edit patient", JOptionPane.ERROR_MESSAGE);
                    //Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    class ProveriSave extends Thread {

        public ProveriSave() {
            start();
        }

        @Override
        public void run() {
            while (true) {
                if (!new Validate().blokirajSave(frmPatient)) {
                    frmPatient.getBtnSave().setEnabled(true);

                } else {
                    frmPatient.getBtnSave().setEnabled(false);
                }
            }
        }
    }

    public void openForm(FormMode formMode) {
        readConfigProperties();
        frmPatient.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView(formMode);
        frmPatient.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        //designComponents(formMode);
        setupComponents(formMode);
        frmPatient.setLocationRelativeTo(null);
        frmPatient.setResizable(false);
    }

    private void validateForm() {
        validateFirstName();
        validateLastName();
        validateAddress();
        validateEmail();
    }

    private void setupComponents(FormMode formMode) {
        new Validate().formModeSetter(frmPatient, formMode);
    }

    private Pacijent makePatientFromForm() {
        Pacijent patient = new Pacijent();

        patient.setIme(frmPatient.getTxtFirstName().getText().trim());
        patient.setPrezime(frmPatient.getTxtLastName().getText().trim());
        patient.setEmail(frmPatient.getTxtEmail().getText().trim());
        patient.setAdresa(frmPatient.getTxtAddress().getText().trim());
        patient.setDatumRodjenja(frmPatient.getDateDatumRodjenja().getDate());
        patient.postaviId(Long.parseLong(frmPatient.getTxtID().getText()));
        return patient;
    }

    private void validateFirstName() {
        new Validate().validate(frmPatient, "[^A-Za-z]", "Firstname");
    }

    private void validateLastName() {
        new Validate().validate(frmPatient, "[^A-Za-z]", "Lastname");
    }

    private void validateAddress() {
        new Validate().validate(frmPatient, "[^A-Za-z0-9/]", "Address");
    }

    private void validateEmail() {
        new Validate().validate(frmPatient, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", "Email");
    }

    private void addActionListenersTxt() {

        frmPatient.getTxtFirstName().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmPatient, "", "FirstnameClick");
            }
        });

        frmPatient.getTxtLastName().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmPatient, "", "LastnameClick");
            }
        });

        frmPatient.getTxtEmail().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmPatient, "", "EmailClick");
            }
        });

        frmPatient.getTxtAddress().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Validate().validate(frmPatient, "", "AddressClick");
            }
        });
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");

                prop.load(input);

                frmPatient.getBtnCancel().setText(prop.getProperty("btnCancel"));
                frmPatient.getBtnDelete().setText(prop.getProperty("btnDelete"));
                frmPatient.getBtnEdit().setText(prop.getProperty("btnEdit"));
                frmPatient.getBtnEnableChanges().setText(prop.getProperty("btnEnableChanges"));
                frmPatient.getBtnSave().setText(prop.getProperty("btnSave"));
                frmPatient.getTxtFirstName().setText(prop.getProperty("txtFirstName"));
                frmPatient.getTxtLastName().setText(prop.getProperty("txtLastName"));
                frmPatient.getTxtEmail().setText(prop.getProperty("txtEmail"));
                frmPatient.getTxtAddress().setText(prop.getProperty("txtAddress"));
                frmPatient.getLblFirstName().setText(prop.getProperty("lblFirstName"));
                frmPatient.getLblLastName().setText(prop.getProperty("lblLastName"));
                frmPatient.getLblEmail().setText(prop.getProperty("lblEmail"));
                frmPatient.getLblAddress().setText(prop.getProperty("lblAddress"));
                frmPatient.getLblDateBirth().setText(prop.getProperty("lblDateBirth"));
            }
        } catch (IOException ex) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
