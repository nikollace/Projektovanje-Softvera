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
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Termin;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.ScheduledTerminsTableModel;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmScheduledTermins;

/**
 *
 * @author nikollace
 */
public class ScheduledTerminsController implements SetProperties {

    private final FrmScheduledTermins frmScheduledTermins;
    List<Termin> termins;
    List<Termin> trazeniTermini;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ScheduledTerminsController(FrmScheduledTermins frmScheduledTermins) {
        this.frmScheduledTermins = frmScheduledTermins;

        trazeniTermini = new ArrayList<>();

        try {
            Termin t = new Termin();
            termins = Communication.getInstance().getAllTermins(t);
        } catch (CommunicationException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this.frmScheduledTermins, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            JOptionPane.showMessageDialog(frmViewPatients, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
        }catch (Exception ex) {
            //JOptionPane.showMessageDialog(this.frmScheduledTermins, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
//            MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
//            System.exit(1);
        }

        prepareView();
        addActionListener();
    }

    public void openForm() {
        frmScheduledTermins.setVisible(true);
        readConfigProperties();
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");

                prop.load(input);

                frmScheduledTermins.getLblSearchValue().setText(prop.getProperty("lblSearchValue"));
                frmScheduledTermins.getBtnCancel().setText(prop.getProperty("btnCancel"));
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

    private void prepareView() {
        frmScheduledTermins.setLocationRelativeTo(null);
        frmScheduledTermins.setTitle("Zakazani termini");
        frmScheduledTermins.setResizable(false);
    }

    private void addActionListener() {
        frmScheduledTermins.addCancelForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmScheduledTermins.dispose();
            }
        });

        frmScheduledTermins.getTxtSearchValue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                textChanged();
                ScheduledTerminsTableModel ptm = (ScheduledTerminsTableModel) frmScheduledTermins.getTblScheduledTermins().getModel();
                ptm.isprazniListu();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                textChanged();
                ScheduledTerminsTableModel ptm = (ScheduledTerminsTableModel) frmScheduledTermins.getTblScheduledTermins().getModel();
                ptm.isprazniListu();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                textChanged();
                ScheduledTerminsTableModel ptm = (ScheduledTerminsTableModel) frmScheduledTermins.getTblScheduledTermins().getModel();
                ptm.isprazniListu();
            }
        });

        frmScheduledTermins.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblScheduledTermins();
            }

            private void fillTblScheduledTermins() {
                ScheduledTerminsTableModel medicalExaminationsViewAllTableModel = new ScheduledTerminsTableModel(termins);
                frmScheduledTermins.getTblScheduledTermins().setModel(medicalExaminationsViewAllTableModel);
            }
        });
    }

    private void textChanged() {
        String searchValue = frmScheduledTermins.getTxtSearchValue().getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date datum;
        String datumTermina;
        String vremeOd, vremeDo;
        boolean nasao = false;
        ScheduledTerminsTableModel ptm = (ScheduledTerminsTableModel) frmScheduledTermins.getTblScheduledTermins().getModel();
        for (Termin termin : termins) {

            datum = termin.getDatumTermina();
            datumTermina = sdf.format(datum);

            vremeOd = termin.getVremeOd();
            vremeDo = termin.getVremeDo();

            if (datumTermina.startsWith(searchValue) || vremeOd.startsWith(searchValue) || vremeDo.startsWith(searchValue)) {
                ptm.azurirajListu(termin);
                nasao = true;
            }
        }
        if (!nasao) {
            JOptionPane.showMessageDialog(frmScheduledTermins, "Sistem ne moze da nadje termin!");
        }
    }
}
