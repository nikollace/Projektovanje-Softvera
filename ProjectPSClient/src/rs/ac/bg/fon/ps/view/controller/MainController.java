/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmMain;

/**
 *
 * @author nikollace
 */
public class MainController implements SetProperties {

    private final FrmMain frmMain;
    public boolean registrovan = false;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        prepareView();
        addActionListener();
    }

    public void openForm() {
        readConfigProperties();
        Stomatolog stomatolog;
        if (!registrovan) {
            stomatolog = (Stomatolog) MainCordinator.getInstance().getParam(Constants.CURRENT_USER);
        } else {
            stomatolog = (Stomatolog) MainCordinator.getInstance().getParam(Constants.REGISTERED_USER);
        }
        frmMain.getMenuCurrentUser().setText(stomatolog.getIme() + " " + stomatolog.getPrezime());
        frmMain.setVisible(true);
    }

    private void addActionListener() {

        frmMain.miPatientNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miPatientNewActionPerformed(e);
            }

            private void miPatientNewActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewPatientForm();
            }
        });

        frmMain.miPatientShowAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miPatientShowAllActionPerformed(e);
            }

            private void miPatientShowAllActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllPatientForm();
            }
        });

        frmMain.miMedicalExaminationAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openMedicalExaminationForm();
            }
        });

        frmMain.miMedicalExaminationViewAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllMedicalExaminationsForm();
            }
        });

        frmMain.miLogOutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!MainCordinator.getInstance().getMainContoller().registrovan) {

                        JOptionPane.showConfirmDialog(frmMain, "Dovidjenja " + MainCordinator.getInstance().getParam("CURRENT_USER") + "!");
                        Communication.getInstance().logOut(MainCordinator.getInstance().getParam("CURRENT_USER"));

                        MainCordinator.getInstance().openLoginForm();
                        frmMain.dispose();
                        //System.exit(1);
                    } else {

                        JOptionPane.showConfirmDialog(frmMain, "Dovidjenja " + MainCordinator.getInstance().getParam("REGISTERED_USER") + "!");
                        Communication.getInstance().logOut(MainCordinator.getInstance().getParam("REGISTERED_USER"));

                        MainCordinator.getInstance().openLoginForm();
                        frmMain.dispose();
                        //System.exit(1);
                    }
                } catch (SocketException ex) {
//                    ex.printStackTrace();
//                    MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });

        frmMain.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (!MainCordinator.getInstance().getMainContoller().registrovan) {

                    JOptionPane.showConfirmDialog(frmMain, "Dovidjenja " + MainCordinator.getInstance().getParam("CURRENT_USER") + "!");
                    try {
                        Communication.getInstance().logOut(MainCordinator.getInstance().getParam("CURRENT_USER"));
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    MainCordinator.getInstance().openLoginForm();
                    frmMain.dispose();
                    //System.exit(1);
                } else {

                    JOptionPane.showConfirmDialog(frmMain, "Dovidjenja " + MainCordinator.getInstance().getParam("REGISTERED_USER") + "!");
                    try {
                        Communication.getInstance().logOut(MainCordinator.getInstance().getParam("REGISTERED_USER"));
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    MainCordinator.getInstance().openLoginForm();
                    frmMain.dispose();
                    //System.exit(1);
                }
            }
        });

    }

    public FrmMain getFrmMain() {
        return frmMain;
    }

    private void prepareView() {
        frmMain.setLocationRelativeTo(null);
        frmMain.setResizable(false);
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");

                prop.load(input);

                frmMain.getMenuPatient().setText(prop.getProperty("menuPatient"));
                frmMain.getMenuMedicalExamination().setText(prop.getProperty("menuMedicalExamination"));
                frmMain.getMenuCurrentUser().setText(prop.getProperty("menuCurrentUser"));
                frmMain.getMenuAboutUs().setText(prop.getProperty("menuAboutUs"));
                frmMain.getMiPatientNew().setText(prop.getProperty("miPatientNew"));
                frmMain.getMiShowPatient().setText(prop.getProperty("miShowPatient"));
                frmMain.getMiScheduleMedicalExamination().setText(prop.getProperty("miScheduleMedicalExamination"));
                frmMain.getMiShowMedicalExamination().setText(prop.getProperty("miShowMedicalExamination"));
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
