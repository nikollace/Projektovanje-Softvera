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
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewAllMedicalExamination;
import rs.ac.bg.fon.ps.view.component.table.MedicalExaminationViewAllTableModel;
import rs.ac.bg.fon.ps.view.constant.Constants;

/**
 *
 * @author nikollace
 */
public class MedicalExaminationViewAllController implements SetProperties {

    private FrmViewAllMedicalExamination frmViewAllMedicalExamination;
    List<Pregled> medicalExaminations;
    List<Pregled> trazeniPregledi;
    Stomatolog s;

    public MedicalExaminationViewAllController(FrmViewAllMedicalExamination frmViewAllMedicalExamination) {
        this.frmViewAllMedicalExamination = frmViewAllMedicalExamination;

        trazeniPregledi = new ArrayList<>();

        if (!MainCordinator.getInstance().getMainContoller().registrovan) {
            s = (Stomatolog) MainCordinator.getInstance().getParam("CURRENT_USER");
        } else {
            s = (Stomatolog) MainCordinator.getInstance().getParam("REGISTERED_USER");
        }

        try {
            Pregled p = new Pregled();
            p.setStomatolog(s);
            medicalExaminations = Communication.getInstance().getAllMedicalExaminations(p);
        } catch (CommunicationException ex) {
            //ex.printStackTrace();
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            JOptionPane.showMessageDialog(frmViewAllMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(frmViewAllMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            //System.exit(1);
        }
        addActionListeners();
    }

    public void openForm() {
        readConfigProperties();
        frmViewAllMedicalExamination.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewAllMedicalExamination.setVisible(true);
    }

    private void prepareView() {
        frmViewAllMedicalExamination.setTitle("View medical examinations");
        frmViewAllMedicalExamination.getPnlEmptyValue().setVisible(false);
        frmViewAllMedicalExamination.getLblTuzna().setVisible(false);
    }

    private void addActionListeners() {
        frmViewAllMedicalExamination.btnDetailsAddAcionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UcitajPregled();
            }

            private void UcitajPregled() {
                int selectedRow = frmViewAllMedicalExamination.getTblMedicalExaminations().getSelectedRow();
                if (selectedRow >= 0) {
                    Pregled medicalExamination = ((MedicalExaminationViewAllTableModel) frmViewAllMedicalExamination.getTblMedicalExaminations().
                            getModel()).getMedicalExaminationAt(selectedRow);
                    MainCordinator.getInstance().addParam(Constants.PARAM_MEDICAL_EXAMINATION, medicalExamination);
                    MainCordinator.getInstance().openMedicalExaminationDetailsForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewAllMedicalExamination, "You must select a medical examination", "Medical examination DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmViewAllMedicalExamination.btnCancelAddAcionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmViewAllMedicalExamination.dispose();
            }
        });

        frmViewAllMedicalExamination.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblViewAllMedicals();
            }
        });

        frmViewAllMedicalExamination.getTxtSearchValue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                NadjiPregled();
                MedicalExaminationViewAllTableModel ptm = (MedicalExaminationViewAllTableModel) frmViewAllMedicalExamination.getTblMedicalExaminations().getModel();
                ptm.isprazniListu();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                NadjiPregled();
                MedicalExaminationViewAllTableModel ptm = (MedicalExaminationViewAllTableModel) frmViewAllMedicalExamination.getTblMedicalExaminations().getModel();
                ptm.isprazniListu();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                NadjiPregled();
                MedicalExaminationViewAllTableModel ptm = (MedicalExaminationViewAllTableModel) frmViewAllMedicalExamination.getTblMedicalExaminations().getModel();
                ptm.isprazniListu();
            }
        });
    }

    private void NadjiPregled() {
        String searchValue = frmViewAllMedicalExamination.getTxtSearchValue().getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date datum;
        String datumTermina;
        String vremeOd, vremeDo;
        boolean nasao = false;
        MedicalExaminationViewAllTableModel ptm = (MedicalExaminationViewAllTableModel) frmViewAllMedicalExamination.getTblMedicalExaminations().getModel();
        for (Pregled medical : medicalExaminations) {

            datum = medical.getTermin().getDatumTermina();
            datumTermina = sdf.format(datum);

            vremeOd = medical.getTermin().getVremeOd();
            vremeDo = medical.getTermin().getVremeDo();

            if (medical.getPacijent().getIme().startsWith(searchValue) || medical.getPacijent().getPrezime().startsWith(searchValue)
                    || medical.getStomatolog().getIme().startsWith(searchValue) || medical.getStomatolog().getPrezime().startsWith(searchValue)
                    || datumTermina.startsWith(searchValue) || vremeOd.startsWith(searchValue) || vremeDo.startsWith(searchValue)) {
                ptm.azurirajListu(medical);
                nasao = true;
            }
        }
        if (!nasao) {
            JOptionPane.showMessageDialog(frmViewAllMedicalExamination, "Sistem ne moze da nadje pregled!");
        }
    }

    private void fillTblViewAllMedicals() {
        List<Pregled> medicalExaminations;

        try {
            Pregled p = new Pregled();
            p.setStomatolog(s);
            medicalExaminations = Communication.getInstance().getAllMedicalExaminations(p);
            if (medicalExaminations.isEmpty()) {
                frmViewAllMedicalExamination.getPnlEmptyValue().setVisible(true);
                frmViewAllMedicalExamination.getTblMedicalExaminations().setVisible(false);
                frmViewAllMedicalExamination.getLblTuzna().setVisible(true);
                emptyTable(frmViewAllMedicalExamination);
            } else {
                MedicalExaminationViewAllTableModel medicalExaminationsViewAllTableModel = new MedicalExaminationViewAllTableModel(medicalExaminations);
                frmViewAllMedicalExamination.getTblMedicalExaminations().setModel(medicalExaminationsViewAllTableModel);
            }
        } catch (CommunicationException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(frmViewAllMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(frmViewAllMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            //System.exit(1);
        }
    }

    public void refresh() {
        fillTblViewAllMedicals();
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");

                prop.load(input);

                frmViewAllMedicalExamination.getBtnCancel().setText(prop.getProperty("btnCancel"));
                frmViewAllMedicalExamination.getBtnDetails().setText(prop.getProperty("btnDetails"));
                frmViewAllMedicalExamination.getLblSearch().setText(prop.getProperty("lblSearch"));
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

    private void emptyTable(FrmViewAllMedicalExamination frmViewAllMedicalExamination) {
        ThreadRipper threadRipper = new ThreadRipper(frmViewAllMedicalExamination);
    }

    private class ThreadRipper extends Thread {

        FrmViewAllMedicalExamination frmViewAllMedicalExamination;
        char[] recenica = new char[]{'N', 'E', 'M', 'A', 'V', 'R', 'E', 'D', 'N', 'O', 'S', 'T', 'I',
            'Z', 'A', 'P', 'R', 'I', 'K', 'A', 'Z', 'I', 'V', 'A', 'N', 'J', 'E', ':', '('};

        String dopuna = "";

        public ThreadRipper(FrmViewAllMedicalExamination frmViewAllMedicalExamination) {
            this.frmViewAllMedicalExamination = frmViewAllMedicalExamination;
            start();
        }

        @Override
        public void run() {
            while (true) {
                for (char c : recenica) {
                    dopuna += String.valueOf(c);
                    frmViewAllMedicalExamination.getTxtEmptyTable().setText(dopuna + " ");
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(MedicalExaminationViewAllController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dopuna = "";
            }
        }

    }
}
