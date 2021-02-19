/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmRegister;

/**
 *
 * @author nikollace
 */
public class RegisterController implements SetProperties{
    private final FrmRegister frmRegister;

    public RegisterController(FrmRegister frmRegister) {
        this.frmRegister = frmRegister;
        prepareView();
        designComponents();
        addActionListener();
    }
    
    public void openForm() {
        readConfigProperties();
        frmRegister.setVisible(true);
        frmRegister.setTitle("Registracija");
    }

    private void addActionListener() {
        frmRegister.registerAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser(e);
            }

            private void registerUser(ActionEvent e) {
                try {
                    String firstName = frmRegister.getTxtFirstName().getText().trim();
                    String lastName = frmRegister.getTxtLastName().getText().trim();
                    String email = frmRegister.getTxtEmail().getText().trim();
                    String password = String.copyValueOf(frmRegister.getTxtPassword().getPassword());
                    String adresa = frmRegister.getTxtAddress().getText().trim();
                    Date birthday = frmRegister.getDateDateBirth().getDate();
                    //validacija forme
                    int provera = validateForm();
                    if(provera!=-1){                    
                        Stomatolog stomatolog = Communication.getInstance().register(firstName, lastName, email, password, birthday ,adresa);
                        JOptionPane.showMessageDialog(
                                frmRegister,
                                "Novi korisnik je dodat u sitem!",
                                "Registracija", JOptionPane.INFORMATION_MESSAGE
                        );                        
                        frmRegister.dispose();
                        MainCordinator.getInstance().addParam(Constants.REGISTERED_USER, stomatolog);
                        MainCordinator.getInstance().getMainContoller().registrovan = true;
                        MainCordinator.getInstance().openMainForm();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmRegister, "Sistem ne moze da sacuva podatke o korisniku!", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmRegister.addBackToStartForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmRegister.dispose();
                MainCordinator.getInstance().openStartForm();
            }
        });
    }

    private void prepareView() {
        frmRegister.setTitle("Register");
        frmRegister.setLocationRelativeTo(null);
        frmRegister.setResizable(false);
    }

    private void designComponents() {
        frmRegister.getBtnRegister().setForeground(Color.GREEN);   
    }
    
    private boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
    
    public int validateForm(){
        if(!isValidEmailAddress(frmRegister.getTxtEmail().getText())){
            JOptionPane.showMessageDialog(frmRegister, "Email invalid!","Error" ,JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if(frmRegister.getTxtFirstName().getText().isEmpty() || frmRegister.getTxtLastName().getText().isEmpty() ||
                frmRegister.getTxtEmail().getText().isEmpty() || frmRegister.getTxtAddress().getText().isEmpty()){
            JOptionPane.showMessageDialog(frmRegister, "Input fields can't be empty!","Error" ,JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if(frmRegister.getTxtPassword().getPassword().length < 6){
            JOptionPane.showMessageDialog(frmRegister, "Password must contains at least 6 characters!","Error" ,JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if(!String.copyValueOf(frmRegister.getTxtPassword().getPassword()).equals(String.copyValueOf(frmRegister.getTxtConfirmPassword().getPassword()))){
            JOptionPane.showMessageDialog(frmRegister, "Passwords doesn't match!","Error" ,JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return 0;
    }
    
    public void resetForm(){
        frmRegister.getTxtFirstName().setText("");
        frmRegister.getTxtLastName().setText("");
        frmRegister.getTxtEmail().setText("");
        frmRegister.getTxtPassword().setText("");
        frmRegister.getTxtConfirmPassword().setText("");
        frmRegister.getTxtAddress().setText("");
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try { 
            if(MainCordinator.getInstance().isIsEnglish()){
                input = new FileInputStream("config_english.properties");
             
  	    prop.load(input);
            
            frmRegister.getLblFirstName().setText(prop.getProperty("lblFirstName"));
            frmRegister.getLblLastName().setText(prop.getProperty("lblLastName"));
            frmRegister.getLblEmail().setText(prop.getProperty("lblEmail"));
	    frmRegister.getLblPassword().setText(prop.getProperty("lblPassword"));
            frmRegister.getLblConfirmPassword().setText(prop.getProperty("lblConfirmPassword"));
            frmRegister.getLblAddress().setText(prop.getProperty("lblAddress"));
            frmRegister.getBtnRegister().setText(prop.getProperty("btnRegister"));
            frmRegister.getBtnStartForm().setText(prop.getProperty("btnStartForm"));
            frmRegister.getLblDateBirth().setText(prop.getProperty("lblDateBirth"));
            }

	  } catch (IOException ex){} 
            finally 
              { if (input != null) 
                    {  try { input.close();} catch (IOException e) {}
                    }
	     }
    }
    
}
