/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmStart;

/**
 *
 * @author nikollace
 */
public class StartController {
    private FrmStart frmStart;

    public StartController(FrmStart frmStart) {
        this.frmStart = frmStart;
        prepareView();
        addActionListener();
    }

    public void openForm() {
        frmStart.setVisible(true);
    }

    private void addActionListener() {
        frmStart.getLblRegister().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                frmStart.dispose();
                MainCordinator.getInstance().openRegisterForm();
            } 
            
            @Override
            public void mouseEntered(MouseEvent evt) {
                frmStart.getLblRegister().setForeground(Color.green);
            } 
            
            @Override
            public void mouseExited(MouseEvent evt) {
                frmStart.getLblRegister().setForeground(Color.YELLOW);
            }
        });
        
        frmStart.getLblLogIn().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) { 
                frmStart.dispose();
                MainCordinator.getInstance().openLoginForm();
            } 
            
            @Override
            public void mouseEntered(MouseEvent evt) {
                frmStart.getLblLogIn().setForeground(Color.red);
            } 
            
            @Override
            public void mouseExited(MouseEvent evt) {
                frmStart.getLblLogIn().setForeground(Color.ORANGE);
            }
        });
        
        frmStart.addRbSerbianLanguageActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLanguage();
            }
        });
        
        frmStart.addRbEnglishLanguageActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLanguage();
            }
        });
    }

    private void prepareView() {
        frmStart.setTitle("Welcome to the dentist app!");
        frmStart.setLocationRelativeTo(null);
        frmStart.setResizable(false);
    }

    private void validateLanguage() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(frmStart.getRbEnglish());
        bg.add(frmStart.getRbSerbian());
        
        if(frmStart.getRbEnglish().isSelected())
            MainCordinator.getInstance().setIsEnglish(true);
        if(frmStart.getRbSerbian().isSelected())
            MainCordinator.getInstance().setIsEnglish(false);
    }
    
}
