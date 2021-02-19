/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.exceptions.user.LoginException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmLogin;

/**
 *
 * @author nikollace
 */
public class LoginController implements SetProperties {

    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListenerTxt();
        prepareView();
        addActionListener();
    }

    public void openForm() {
        frmLogin.setVisible(true);
        readConfigProperties();
    }

    private void addActionListener() {

        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser(e);
            }

            private void loginUser(ActionEvent e) {
                //resetovanje forme

                try {
                    String email = frmLogin.getTxtEmail().getText().trim();
                    String password = String.valueOf(frmLogin.getTxtPassword().getPassword());
                    //validacija forme
                    validateForm();

                    Stomatolog stomatolog = Communication.getInstance().login(email, password);

                    JOptionPane.showMessageDialog(
                            frmLogin,
                            "Uspesno ste se prijavili na sistem!",
                            "Prijava", JOptionPane.INFORMATION_MESSAGE
                    );
                    frmLogin.dispose();
                    MainCordinator.getInstance().addParam(Constants.CURRENT_USER, stomatolog);
                    MainCordinator.getInstance().openMainForm();

                } catch (CommunicationException ex) {
                    //ex.printStackTrace();
                    //MainCordinator.getInstance().getMainContoller().getFrmMain().dispose();
                    JOptionPane.showMessageDialog(frmLogin, "Server is down!", "Server down", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (LoginException ex) {
                    JOptionPane.showMessageDialog(frmLogin, ex.getMessage(), "Greska prilikom prijavljivanja! Login", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmLogin, "Neuspesno prijavljivanje na sistem!", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void prepareView() {
        frmLogin.setTitle("Login");
        frmLogin.setLocationRelativeTo(null);
        frmLogin.getTxtPassword().setEchoChar((char) 0);
        frmLogin.getLblEmail().requestFocus();

    }

    private void validateForm() {
        validateEmail();
        validatePassword();
    }

    private void validateEmail() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (frmLogin.getTxtEmail().getText().trim().isEmpty()
                    || frmLogin.getTxtEmail().getText().equals("Enter your email...")) {
                frmLogin.getTxtEmail().setText("You should enter your email!");
                frmLogin.getTxtEmail().setForeground(Color.red);
            }
        } else {
            if (frmLogin.getTxtEmail().getText().trim().isEmpty()
                    || frmLogin.getTxtEmail().getText().equals("Unesite email...")) {
                frmLogin.getTxtEmail().setText("Morate uneti email!");
                frmLogin.getTxtEmail().setForeground(Color.red);
            }
        }

    }

    private void validatePassword() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            if (String.valueOf(frmLogin.getTxtPassword().getPassword()).isEmpty() || String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Enter your password...")) {
                frmLogin.getTxtPassword().setText("You should enter password!");
                frmLogin.getTxtPassword().setForeground(Color.red);
                frmLogin.getTxtPassword().setEchoChar((char) 0);
                frmLogin.getCbShowPassword().setEnabled(false);
            }
        } else {
            if (String.valueOf(frmLogin.getTxtPassword().getPassword()).isEmpty() || String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Unesite sifru...")) {
                frmLogin.getTxtPassword().setText("Morate uneti sifru!");
                frmLogin.getTxtPassword().setForeground(Color.red);
                frmLogin.getTxtPassword().setEchoChar((char) 0);
                frmLogin.getCbShowPassword().setEnabled(false);
            }
        }
    }

    private void addActionListenerTxt() {
        frmLogin.getTxtEmail().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MainCordinator.getInstance().isIsEnglish()) {
                    if (frmLogin.getTxtEmail().getText().equals("You should enter your email!") || frmLogin.getTxtEmail().getText().equals("Enter your email...")) {
                        frmLogin.getTxtEmail().setText("");
                        frmLogin.getTxtEmail().setForeground(Color.black);
                    }
                } else {
                    if (frmLogin.getTxtEmail().getText().equals("Morate uneti email!") || frmLogin.getTxtEmail().getText().equals("Unesite email...")) {
                        frmLogin.getTxtEmail().setText("");
                        frmLogin.getTxtEmail().setForeground(Color.black);
                    }
                }

            }
        });

        frmLogin.getTxtPassword().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!frmLogin.getCbShowPassword().isSelected()) {
                    frmLogin.getTxtPassword().setEchoChar('•');
                }

                if (MainCordinator.getInstance().isIsEnglish()) {
                    if (String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("You should enter password!")
                            || String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Enter your password...")) {
                        frmLogin.getTxtPassword().setForeground(Color.black);
                        frmLogin.getTxtPassword().setText("");
                        frmLogin.getCbShowPassword().setEnabled(true);
                    }
                } else {
                    if (String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Morate uneti sifru!")
                            || String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Unesite sifru...")) {
                        frmLogin.getTxtPassword().setForeground(Color.black);
                        frmLogin.getTxtPassword().setText("");
                        frmLogin.getCbShowPassword().setEnabled(true);
                    }
                }
            }
        });

        frmLogin.addPasswordFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!frmLogin.getCbShowPassword().isSelected()) {
                    frmLogin.getTxtPassword().setEchoChar('•');
                }

                if (MainCordinator.getInstance().isIsEnglish()) {
                    if (String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("You should enter password!")
                            || String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Enter your password...")) {
                        frmLogin.getTxtPassword().setForeground(Color.black);
                        frmLogin.getTxtPassword().setText("");
                        frmLogin.getCbShowPassword().setEnabled(true);
                    }
                } else {
                    if (String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Morate uneti sifru!")
                            || String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Unesite sifru...")) {
                        frmLogin.getTxtPassword().setForeground(Color.black);
                        frmLogin.getTxtPassword().setText("");
                        frmLogin.getCbShowPassword().setEnabled(true);
                    }
                }
            }
        });

        frmLogin.addCbPasswordMouseClikedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!frmLogin.getCbShowPassword().isSelected() && (!String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Unesite sifru...")
                        || !String.valueOf(frmLogin.getTxtPassword().getPassword()).equals("Enter your password..."))) {
                    frmLogin.getTxtPassword().setEchoChar('•');
                } else {
                    frmLogin.getTxtPassword().setEchoChar('\u0000');
                }
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
                frmLogin.getLblEmail().setText(prop.getProperty("lblEmail"));
                frmLogin.getLblPassword().setText(prop.getProperty("lblPassword"));
                frmLogin.getTxtEmail().setText(prop.getProperty("txtEmail"));
                frmLogin.getTxtPassword().setText(prop.getProperty("txtPassword"));
                frmLogin.getBtnLogin().setText(prop.getProperty("btnLogin"));
                frmLogin.getCbShowPassword().setText(prop.getProperty("cbShowPassword"));
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
