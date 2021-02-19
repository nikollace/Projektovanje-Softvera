/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.domain.Termin;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.exceptions.user.TerminException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.MedicalExaminationTableModel;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmMedicalExamination;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author nikollace
 */
public class MedicalExaminationController implements SetProperties {

    private final FrmMedicalExamination frmMedicalExamination;
    MedicalExaminationTableModel model;
    boolean validanTermin = false;
    Termin termin;

    public MedicalExaminationController(FrmMedicalExamination frmMedicalExamination) {
        this.frmMedicalExamination = frmMedicalExamination;
        addActionListeners();
    }

    public FrmMedicalExamination getFrmMedicalExamination() {
        return frmMedicalExamination;
    }

    public void openForm(FormMode formMode) {
        readConfigProperties();
        fillForm();
        setupComponents(formMode);
        prepareView();

        frmMedicalExamination.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        frmMedicalExamination.setVisible(true);

        switch (formMode) {
            case FORM_ADD:
                fillTblMedicalExamination();
                break;
            case FORM_EDIT:
                prepareMedicalExaminationOnForm();
                break;
            case FORM_VIEW:
                prepareMedicalExaminationOnForm();
                break;
        }
    }

    private void prepareView() {
        frmMedicalExamination.setTitle("Add medical examination");
        frmMedicalExamination.setLocationRelativeTo(null);
        frmMedicalExamination.setResizable(false);
        Stomatolog stomatolog = null;
        if (MainCordinator.getInstance().getMainContoller().registrovan == true) {
            stomatolog = (Stomatolog) MainCordinator.getInstance().getParam("REGISTERED_USER");
        } else {
            stomatolog = (Stomatolog) MainCordinator.getInstance().getParam("CURRENT_USER");
        }

        //Pregled
        frmMedicalExamination.getTxtUkupnaCena().setText("0.0");
        frmMedicalExamination.getTxtUkupnoTrajanje().setText("0.0");
        frmMedicalExamination.getCbPacijenti().setSelectedIndex(-1);
        frmMedicalExamination.getTxtUlogovaniStomatolog().setText(stomatolog.getIme() + " " + stomatolog.getPrezime());

        //Stavka pregleda
        frmMedicalExamination.getTxtCenaPregleda().setText("0.0");
        frmMedicalExamination.getTxtCenaPregleda().grabFocus();
        frmMedicalExamination.getTxtCenaPregleda().setSelectionStart(0);
        frmMedicalExamination.getTxtTrajanjePregleda().setText("0.0");
    }

    private void addActionListeners() {
        frmMedicalExamination.addSacuvajTerminActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate termin?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    saveTermin();
                }
            }

            private void saveTermin() {

                termin = makeTerminFromForm();

                try {
                    if (validanTermin) {
                        validanTermin = false;
                        Communication.getInstance().addTermin(termin);

                        JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem je zapamtio termin!");
                    }
                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                    //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (Exception ex) {
//                    Logger.getLogger(MedicalExaminationController.class.getName()).log(Level.SEVERE, null, ex);
//                    JOptionPane.showMessageDialog(frmMedicalExamination, ex.getMessage());
                }

            }
        });

        //Dodavanje stavke pregleda
        frmMedicalExamination.addDodajStavkuPregledaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajStavku();
            }

            private void dodajStavku() {
                try {
                    String nazivPregleda = frmMedicalExamination.getTxtNazivPregleda().getText().trim();
                    String opis = frmMedicalExamination.getTaOpis().getText().trim();
                    double cena = Double.parseDouble(frmMedicalExamination.getTxtCenaPregleda().getText().trim());
                    double trajanje = Double.parseDouble(frmMedicalExamination.getTxtTrajanjePregleda().getText().trim());
                    if (opis.length() <= 50) {
                        MedicalExaminationTableModel model = (MedicalExaminationTableModel) frmMedicalExamination.getTblStavkaPregleda().getModel();
                        model.addMedicalExaminationItem(nazivPregleda, cena, trajanje, opis);
                        double cenaIzModela = model.getMedicalExamination().getUkupnaCena();
                        double trajenjeIzModela = model.getMedicalExamination().getUkupnoTrajanje();
                        frmMedicalExamination.getTxtUkupnaCena().setText(String.valueOf(cenaIzModela));
                        frmMedicalExamination.getTxtUkupnoTrajanje().setText(String.valueOf(trajenjeIzModela));
                        clearView();
                    } else {
                        JOptionPane.showMessageDialog(frmMedicalExamination, "Opis je predugacak. Maksimum je 50 karaktera. Stavka nije dodata.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Invalid medical examination data!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Brisanje stavke pregleda
        frmMedicalExamination.addIzbrisiStavkuPregledaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izbrisiStavku();
            }

            private void izbrisiStavku() {
                int rowIndex = frmMedicalExamination.getTblStavkaPregleda().getSelectedRow();
                MedicalExaminationTableModel model = (MedicalExaminationTableModel) frmMedicalExamination.getTblStavkaPregleda().getModel();
                if (rowIndex >= 0) {
                    model.removeMedicalExaminationItem(rowIndex);
                    double cenaIzModela = model.getMedicalExamination().getUkupnaCena();
                    double trajenjeIzModela = model.getMedicalExamination().getUkupnoTrajanje();
                    frmMedicalExamination.getTxtUkupnaCena().setText(String.valueOf(cenaIzModela));
                    frmMedicalExamination.getTxtUkupnoTrajanje().setText(String.valueOf(trajenjeIzModela));
                } else {
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Medical examination item is not selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Sacuvaj pregled
        frmMedicalExamination.addSaveMedicalExaminationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate pregled?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    saveMedicalExamination();
                }
            }

            private void saveMedicalExamination() {
                try {
                    MedicalExaminationTableModel model = (MedicalExaminationTableModel) frmMedicalExamination.getTblStavkaPregleda().getModel();
                    Pregled pregled = model.getMedicalExamination();
                    pregled.setPacijent((Pacijent) frmMedicalExamination.getCbPacijenti().getSelectedItem());
                    pregled.setTermin(termin);
                    termin = null;
                    if (MainCordinator.getInstance().getMainContoller().registrovan) {
                        pregled.setStomatolog((Stomatolog) MainCordinator.getInstance().getParam("REGISTERED_USER"));
                    } else {
                        pregled.setStomatolog((Stomatolog) MainCordinator.getInstance().getParam("CURRENT_USER"));
                    }
                    pregled.setUkupnaCena(Double.parseDouble(frmMedicalExamination.getTxtUkupnaCena().getText()));
                    pregled.setUkupnoTrajanje(Double.parseDouble(frmMedicalExamination.getTxtUkupnoTrajanje().getText()));
                    for (StavkaPregleda stavka : pregled.getStavkePregleda()) {
                        System.out.println(stavka);
                    }

                    Communication.getInstance().addMedicalExamination(pregled);
                    frmMedicalExamination.getTxtId().setText(String.valueOf(pregled.getId()));
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem je zapamtio pregled!");
                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                   // MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem ne moze da zapamti pregled!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Otvaranje forme sa terminima
        frmMedicalExamination.addScheduledTerminsBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openScheduledTerminsForm();
            }
        });

        //Prosirenje opis polja
        frmMedicalExamination.getTblStavkaPregleda().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                popUp(evt);
            }

            private void popUp(java.awt.event.MouseEvent evt) {
                MedicalExaminationTableModel m = (MedicalExaminationTableModel) frmMedicalExamination.getTblStavkaPregleda().getModel();
                String opis = m.getMedicalItemDescription(frmMedicalExamination.getTblStavkaPregleda().rowAtPoint(evt.getPoint()), frmMedicalExamination.getTblStavkaPregleda().columnAtPoint(evt.getPoint()));
                JOptionPane.showMessageDialog(frmMedicalExamination, opis);
            }
        });

        //Ponistenje prozora medical examination
        frmMedicalExamination.addPonistiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ponisti();
            }

            private void ponisti() {
                frmMedicalExamination.dispose();
            }
        });

        frmMedicalExamination.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmMedicalExamination.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMedicalExamination();
                refresh();
            }

            private void editMedicalExamination() {
                try {
                    MedicalExaminationTableModel model = (MedicalExaminationTableModel) frmMedicalExamination.getTblStavkaPregleda().getModel();
                    Pregled pregled = model.getMedicalExamination();
                    pregled.setPacijent((Pacijent) frmMedicalExamination.getCbPacijenti().getSelectedItem());
                    pregled.setTermin(termin);

                    if (MainCordinator.getInstance().getMainContoller().registrovan) {
                        pregled.setStomatolog((Stomatolog) MainCordinator.getInstance().getParam("REGISTERED_USER"));
                    } else {
                        pregled.setStomatolog((Stomatolog) MainCordinator.getInstance().getParam("CURRENT_USER"));
                    }
                    pregled.setUkupnaCena(Double.parseDouble(frmMedicalExamination.getTxtUkupnaCena().getText()));
                    pregled.setUkupnoTrajanje(Double.parseDouble(frmMedicalExamination.getTxtUkupnoTrajanje().getText()));

                    Communication.getInstance().editMedicalExamination(pregled);

                    JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem je zapamtio pregled!");

                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                   // MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem ne moze da zapamti pregled!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        frmMedicalExamination.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMedicalExamination();
                refresh();
            }

            private void deleteMedicalExamination() {
                MedicalExaminationTableModel model = (MedicalExaminationTableModel) frmMedicalExamination.getTblStavkaPregleda().getModel();
                Pregled pregled = model.getMedicalExamination();

                try {
                    Communication.getInstance().deleteMedicalExamination(pregled);
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem je obrisao pregled!");
                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                   // MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmMedicalExamination, "Sistem ne moze da obrise pregled!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void prepareMedicalExaminationOnForm() {
        Pregled medicalExamination = (Pregled) MainCordinator.getInstance().getParam("PARAM_MEDICAL_EXAMINATION");
        frmMedicalExamination.getTxtId().setText(String.valueOf(medicalExamination.getId()));
        frmMedicalExamination.getTxtUkupnaCena().setText(String.valueOf(medicalExamination.getUkupnaCena()));
        frmMedicalExamination.getTxtUkupnoTrajanje().setText(String.valueOf(medicalExamination.getUkupnoTrajanje()));
        frmMedicalExamination.getTxtUlogovaniStomatolog().setText(medicalExamination.getStomatolog().getIme() + " " + medicalExamination.getStomatolog().getPrezime());
        frmMedicalExamination.getCbPacijenti().setSelectedItem(medicalExamination.getPacijent());
        /*Izvlacimo sate i minute iz relacije termin*/
        String[] pocetakVreme = medicalExamination.getTermin().getVremeOd().split(":");
        String[] krajVreme = medicalExamination.getTermin().getVremeDo().split(":");
        frmMedicalExamination.getTxtPocetakSati().setText(pocetakVreme[0]);
        frmMedicalExamination.getTxtPocetakMinuti().setText(pocetakVreme[1]);
        frmMedicalExamination.getTxtKrajSati().setText(krajVreme[0]);
        frmMedicalExamination.getTxtKrajMinuti().setText(krajVreme[1]);
        frmMedicalExamination.getDateDatumTermina().setDate(medicalExamination.getTermin().getDatumTermina());

        try {
            StavkaPregleda stavka = new StavkaPregleda();
            Pregled p = new Pregled();
            p.postaviId(medicalExamination.getId());
            stavka.setPregled(p);
            //List<StavkaPregleda> stavkePregleda = pokupiStavku(String.valueOf(medicalExamination.getId()));
            List<StavkaPregleda> stavkePregleda = pokupiStavku(stavka);

            medicalExamination.setStavkePregleda(stavkePregleda);
            MedicalExaminationTableModel medicalExaminationTableModel = new MedicalExaminationTableModel(medicalExamination);
            frmMedicalExamination.getTblStavkaPregleda().setModel(medicalExaminationTableModel);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(MedicalExaminationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearView() {
        frmMedicalExamination.getDateDatumTermina().setDate(new Date());
        frmMedicalExamination.getTxtPocetakSati().setText("");
        frmMedicalExamination.getTxtPocetakMinuti().setText("");
        frmMedicalExamination.getTxtKrajSati().setText("");
        frmMedicalExamination.getTxtKrajMinuti().setText("");
    }

    private Termin makeTerminFromForm() {
        Termin termin = new Termin();

        Date datumPregleda = frmMedicalExamination.getDateDatumTermina().getDate();
        String vremeOd = frmMedicalExamination.getTxtPocetakSati().getText().trim() + ":"
                + frmMedicalExamination.getTxtPocetakMinuti().getText().trim();

        String vremeDo = frmMedicalExamination.getTxtKrajSati().getText().trim() + ":"
                + frmMedicalExamination.getTxtKrajMinuti().getText().trim();

        termin.setDatumTermina(datumPregleda);
        termin.setVremeOd(vremeOd);
        termin.setVremeDo(vremeDo);
        try {
            validanTermin = validateTermin(datumPregleda, frmMedicalExamination.getTxtPocetakSati().getText().trim(),
                    frmMedicalExamination.getTxtPocetakMinuti().getText().trim(), frmMedicalExamination.getTxtKrajSati().getText().trim(),
                    frmMedicalExamination.getTxtKrajMinuti().getText().trim());
        } catch (Exception ex) {
            Logger.getLogger(MedicalExaminationController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMedicalExamination, "Proverite unete vrednosti za datum i vreme termina!", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        return termin;
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmMedicalExamination.getTxtId().setEditable(false);
                frmMedicalExamination.getTxtUkupnaCena().setEditable(false);
                frmMedicalExamination.getTxtUkupnoTrajanje().setEditable(false);
                frmMedicalExamination.getTxtUlogovaniStomatolog().setEditable(false);
                frmMedicalExamination.getBtnEnableChanges().setVisible(false);
                frmMedicalExamination.getBtnEdit().setVisible(false);
                frmMedicalExamination.getBtnDelete().setVisible(false);
                break;
            case FORM_VIEW:
                frmMedicalExamination.getTxtId().setEditable(false);
                frmMedicalExamination.getTxtUkupnaCena().setEditable(false);
                frmMedicalExamination.getTxtUkupnoTrajanje().setEditable(false);
                frmMedicalExamination.getTxtUlogovaniStomatolog().setEditable(false);
                frmMedicalExamination.getBtnEnableChanges().setVisible(true);
                frmMedicalExamination.getBtnEdit().setVisible(false);
                frmMedicalExamination.getBtnDelete().setVisible(false);
                frmMedicalExamination.getBtnCancel().setVisible(true);
                frmMedicalExamination.getBtnSave().setVisible(false);
                frmMedicalExamination.getBtnSaveTermin().setVisible(false);
                frmMedicalExamination.getTxtPocetakMinuti().setEditable(false);
                frmMedicalExamination.getTxtPocetakSati().setEditable(false);
                frmMedicalExamination.getTxtKrajMinuti().setEditable(false);
                frmMedicalExamination.getTxtKrajSati().setEditable(false);
                frmMedicalExamination.getTxtCenaPregleda().setEditable(false);
                frmMedicalExamination.getTxtNazivPregleda().setEditable(false);
                frmMedicalExamination.getTxtTrajanjePregleda().setEditable(false);
                frmMedicalExamination.getTaOpis().setEditable(false);
                frmMedicalExamination.getBtnAddItem().setVisible(false);
                frmMedicalExamination.getBtnScheduledTermins().setVisible(false);
                frmMedicalExamination.getBtnDeleteItem().setVisible(false);
                frmMedicalExamination.getCbPacijenti().setVisible(false);
                frmMedicalExamination.getDateDatumTermina().setVisible(false);
                break;
            case FORM_EDIT:
                frmMedicalExamination.getTxtId().setEditable(false);
                frmMedicalExamination.getTxtUkupnaCena().setEditable(true);
                frmMedicalExamination.getTxtUkupnoTrajanje().setEditable(true);
                frmMedicalExamination.getTxtUlogovaniStomatolog().setEditable(true);
                frmMedicalExamination.getBtnEnableChanges().setVisible(false);
                frmMedicalExamination.getBtnEdit().setVisible(true);
                frmMedicalExamination.getBtnDelete().setVisible(true);
                frmMedicalExamination.getBtnCancel().setVisible(true);
                frmMedicalExamination.getBtnSave().setVisible(false);
                frmMedicalExamination.getBtnSaveTermin().setVisible(true);
                frmMedicalExamination.getBtnScheduledTermins().setVisible(true);
                frmMedicalExamination.getTxtPocetakMinuti().setEditable(true);
                frmMedicalExamination.getTxtPocetakSati().setEditable(true);
                frmMedicalExamination.getTxtKrajMinuti().setEditable(true);
                frmMedicalExamination.getTxtKrajSati().setEditable(true);
                frmMedicalExamination.getTxtCenaPregleda().setEditable(true);
                frmMedicalExamination.getTxtNazivPregleda().setEditable(true);
                frmMedicalExamination.getTxtTrajanjePregleda().setEditable(true);
                frmMedicalExamination.getTaOpis().setEditable(true);
                frmMedicalExamination.getBtnAddItem().setVisible(true);
                frmMedicalExamination.getBtnDeleteItem().setVisible(true);
                frmMedicalExamination.getCbPacijenti().setVisible(true);
                frmMedicalExamination.getDateDatumTermina().setVisible(true);
                break;
        }
    }

    private void fillForm() {
        Pacijent p = new Pacijent();
        try {
            frmMedicalExamination.getCbPacijenti().setModel(new DefaultComboBoxModel(Communication.getInstance().getAllPatients(p).toArray()));
        } catch (CommunicationException ex) {
            //ex.printStackTrace();
            //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
            JOptionPane.showMessageDialog(frmMedicalExamination, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception ex) {
//            Logger.getLogger(MedicalExaminationController.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(frmMedicalExamination, ex.getMessage());
        }
    }

    public void refresh() {
        prepareMedicalExaminationOnForm();
    }

    private void fillTblMedicalExamination() {
        model = new MedicalExaminationTableModel(new Pregled());
        frmMedicalExamination.getTblStavkaPregleda().setModel(model);
    }

    private boolean validateTermin(Date datumPregleda, String pocetakSati, String pocetakMinuti, String krajSati, String krajMinuti) throws TerminException {

        if (datumPregleda.after(new Date())) {

        } else {
            throw new TerminException("Datum ne sme biti iz proslosti!");
        }
        if ((Integer.parseInt(pocetakSati) < 23 && Integer.parseInt(pocetakSati) >= 0)
                && (Integer.parseInt(pocetakMinuti) < 60 && Integer.parseInt(pocetakMinuti) >= 0)
                && (Integer.parseInt(krajSati) < 23 && Integer.parseInt(krajSati) >= 0)
                && (Integer.parseInt(krajMinuti) < 60 && Integer.parseInt(krajMinuti) >= 0)) {
            if (Integer.parseInt(krajSati) < Integer.parseInt(pocetakSati)) {
                throw new TerminException("Kraj termina ne sme biti pre pocetka termina!");
            }
            return true;
        } else {
            throw new TerminException("Sati moraju biti izmedju 00-23!\nMinuti moraju biti izmedju 00-59");
        }

    }

    private List<StavkaPregleda> pokupiStavku(StavkaPregleda stavka) throws Exception {
        return Communication.getInstance().getAllMedicalExaminationItems(stavka);
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");

                prop.load(input);

                frmMedicalExamination.getBtnCancel().setText(prop.getProperty("btnCancel"));
                frmMedicalExamination.getBtnDelete().setText(prop.getProperty("btnDelete"));
                frmMedicalExamination.getBtnEdit().setText(prop.getProperty("btnEdit"));
                frmMedicalExamination.getBtnEnableChanges().setText(prop.getProperty("btnEnableChanges"));
                frmMedicalExamination.getBtnSave().setText(prop.getProperty("btnSave"));
                frmMedicalExamination.getBtnSaveTermin().setText(prop.getProperty("btnSaveTermin"));
                frmMedicalExamination.getBtnScheduledTermins().setText(prop.getProperty("btnScheduledTermins"));
                frmMedicalExamination.getBtnAddItem().setText(prop.getProperty("btnAddItem"));
                frmMedicalExamination.getBtnDeleteItem().setText(prop.getProperty("btnDeleteItem"));
                frmMedicalExamination.getLblDentist().setText(prop.getProperty("lblDentist"));
                frmMedicalExamination.getLblPatient().setText(prop.getProperty("lblPatient"));
                frmMedicalExamination.getLblTotalPrice().setText(prop.getProperty("lblTotalPrice"));
                frmMedicalExamination.getLblTotalDuration().setText(prop.getProperty("lblTotalDuration"));
                frmMedicalExamination.getLblTerminStart().setText(prop.getProperty("lblTerminStart"));
                frmMedicalExamination.getLblTerminEnd().setText(prop.getProperty("lblTerminEnd"));
                frmMedicalExamination.getLblDateTermin().setText(prop.getProperty("lblDateTermin"));
                frmMedicalExamination.getLblType().setText(prop.getProperty("lblType"));
                frmMedicalExamination.getLblDuration().setText(prop.getProperty("lblDuration"));
                frmMedicalExamination.getLblDescription().setText(prop.getProperty("lblDescription"));
                frmMedicalExamination.getLblPrice().setText(prop.getProperty("lblPrice"));
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
