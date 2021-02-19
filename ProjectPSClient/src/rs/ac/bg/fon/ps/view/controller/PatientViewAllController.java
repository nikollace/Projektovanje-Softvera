/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.PacijentTableModel;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewPatientsTM;

/**
 *
 * @author nikollace
 */
public class PatientViewAllController implements SetProperties {

    private final FrmViewPatientsTM frmViewPatients;
    List<Pacijent> zahtevaniPacijenti;
    List<Pacijent> patients;

    public PatientViewAllController(FrmViewPatientsTM form) {
        this.frmViewPatients = form;
        zahtevaniPacijenti = new ArrayList<>();

        try {
            Pacijent p = new Pacijent();
            patients = Communication.getInstance().getAllPatients(p);
        } catch (CommunicationException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
        } catch (Exception ex) {
            //ex.printStackTrace();
            //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            //System.exit(1);
        }
        addActionListeners();
    }

    public void openForm() {
        readConfigProperties();
        frmViewPatients.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewPatients.setVisible(true);
    }

    private void prepareView() {
        frmViewPatients.setTitle("View patients");
    }

    private void addActionListeners() {
        frmViewPatients.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UcitajPacijenta();
            }

            private void UcitajPacijenta() {
                int row = frmViewPatients.getTblPacijent().getSelectedRow();
                if (row >= 0) {
                    Pacijent patient = ((PacijentTableModel) frmViewPatients.getTblPacijent().getModel()).getPatientAt(row);
                    MainCordinator.getInstance().addParam(Constants.PARAM_PATIENT, patient);
                    MainCordinator.getInstance().openPatientDetailsPatientForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewPatients, "You must select a patient", "PATIENT DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        frmViewPatients.getBtnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmViewPatients.dispose();
            }
        });

        frmViewPatients.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblPatients();
            }
        });

        frmViewPatients.getTxtSearchValue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                NadjiPacijenta();
                PacijentTableModel ptm = (PacijentTableModel) frmViewPatients.getTblPacijent().getModel();
                ptm.isprazniListu();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                NadjiPacijenta();
                PacijentTableModel ptm = (PacijentTableModel) frmViewPatients.getTblPacijent().getModel();
                ptm.isprazniListu();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                NadjiPacijenta();
                PacijentTableModel ptm = (PacijentTableModel) frmViewPatients.getTblPacijent().getModel();
                ptm.isprazniListu();
            }
        });
    }

    private void NadjiPacijenta() {
        String searchValue = frmViewPatients.getTxtSearchValue().getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date datum;
        String datumRodjenja;
        PacijentTableModel ptm = (PacijentTableModel) frmViewPatients.getTblPacijent().getModel();
        boolean nasao = false;
        for (Pacijent patient : patients) {

            datum = patient.getDatumRodjenja();
            datumRodjenja = sdf.format(datum);

            if (patient.getIme().startsWith(searchValue) || patient.getPrezime().startsWith(searchValue)
                    || patient.getEmail().startsWith(searchValue) || patient.getAdresa().startsWith(searchValue)
                    || datumRodjenja.contains(searchValue)) {
                ptm.azurirajListu(patient);
                nasao = true;
            }
        }
        if (!nasao) {
            JOptionPane.showMessageDialog(frmViewPatients, "Sistem ne moze da pronadje pacijenta!");
        }
    }

    private void fillTblPatients() {
        List<Pacijent> patients;
        Pacijent p = new Pacijent();
        try {
            patients = Communication.getInstance().getAllPatients(p);
            PacijentTableModel ptm = new PacijentTableModel(patients);
            frmViewPatients.getTblPacijent().setModel(ptm);
        } catch (CommunicationException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
        } catch (Exception ex) {
            //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            //JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            //System.exit(1);
        }
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");

                prop.load(input);

                frmViewPatients.getBtnCancel().setText(prop.getProperty("btnCancel"));
                frmViewPatients.getBtnDetails().setText(prop.getProperty("btnDetails"));
                frmViewPatients.getLblSearchPatient().setText(prop.getProperty("lblSearchPatient"));
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
