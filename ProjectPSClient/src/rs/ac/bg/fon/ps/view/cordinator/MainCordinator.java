/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.cordinator;

import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.ps.view.controller.LoginController;
import rs.ac.bg.fon.ps.view.controller.MainController;
import rs.ac.bg.fon.ps.view.controller.MedicalExaminationController;
import rs.ac.bg.fon.ps.view.controller.MedicalExaminationViewAllController;
import rs.ac.bg.fon.ps.view.controller.PatientController;
import rs.ac.bg.fon.ps.view.controller.PatientViewAllController;
import rs.ac.bg.fon.ps.view.controller.RegisterController;
import rs.ac.bg.fon.ps.view.controller.ScheduledTerminsController;
import rs.ac.bg.fon.ps.view.controller.StartController;
import rs.ac.bg.fon.ps.view.form.FrmLogin;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.view.form.FrmMedicalExamination;
import rs.ac.bg.fon.ps.view.form.FrmPatient;
import rs.ac.bg.fon.ps.view.form.FrmRegister;
import rs.ac.bg.fon.ps.view.form.FrmScheduledTermins;
import rs.ac.bg.fon.ps.view.form.FrmStart;
import rs.ac.bg.fon.ps.view.form.FrmViewAllMedicalExamination;
import rs.ac.bg.fon.ps.view.form.FrmViewPatientsTM;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author nikollace
 */
public class MainCordinator {
    private static MainCordinator instance;
    private boolean isEnglish = false;

    public boolean isIsEnglish() {
        return isEnglish;
    }

    public void setIsEnglish(boolean isEnglish) {
        this.isEnglish = isEnglish;
    }
    
    private final MainController mainContoller;
    
    private final Map<String, Object> params;
    
    private MainCordinator() {
        mainContoller = new MainController(new FrmMain());
        params = new HashMap<>();
    }
    
    public static MainCordinator getInstance(){
        if(instance == null)
            instance = new MainCordinator();
        return instance;
    }
    
    public void openLoginForm() {
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openForm();
    }
    
    public void openRegisterForm() {
        RegisterController registerController = new RegisterController(new FrmRegister());
        registerController.openForm();
    }
    
    public void openStartForm(){
        StartController startController = new StartController(new FrmStart(null, true));
        startController.openForm();
    }
    
    public void openScheduledTerminsForm(){
        ScheduledTerminsController scheduledTerminsController = new ScheduledTerminsController(new FrmScheduledTermins());
        scheduledTerminsController.openForm();
    }
    
    public void addParam(String name, Object key){
        params.put(name, key);
    }
    
    public Object getParam(String name){
        return params.get(name);
    }

    public void openMainForm() {
        mainContoller.openForm();
    }

    public void openAddNewPatientForm() {
        PatientController patientController = new PatientController(new FrmPatient(mainContoller.getFrmMain(), true));
        patientController.openForm(FormMode.FORM_ADD);
    }
    
    public void openPatientDetailsPatientForm() {
        FrmPatient patientDetails = new FrmPatient(mainContoller.getFrmMain(), true);
        PatientController patientController = new PatientController(patientDetails);
        patientController.openForm(FormMode.FORM_VIEW);
    }
    
    public MainController getMainContoller() {
        return mainContoller;
    }

    public void openViewAllPatientForm() {
        FrmViewPatientsTM form = new FrmViewPatientsTM(mainContoller.getFrmMain(), true);
        PatientViewAllController patientViewAllController = new PatientViewAllController(form);
        patientViewAllController.openForm();
    }
    
    public void openMedicalExaminationForm(){
        MedicalExaminationController medicalExaminationController = new MedicalExaminationController(new FrmMedicalExamination());
        medicalExaminationController.openForm(FormMode.FORM_ADD);
    }
    
    public void openMedicalExaminationDetailsForm() {
        MedicalExaminationController medicalExaminationController = new MedicalExaminationController(new FrmMedicalExamination());
        medicalExaminationController.openForm(FormMode.FORM_VIEW);
    }

    public void openViewAllMedicalExaminationsForm() {
        FrmViewAllMedicalExamination frmViewAllMedicalExamination = new FrmViewAllMedicalExamination();
        MedicalExaminationViewAllController medicalExaminationViewAllController = new MedicalExaminationViewAllController(frmViewAllMedicalExamination);
        medicalExaminationViewAllController.openForm();
    }   
}
