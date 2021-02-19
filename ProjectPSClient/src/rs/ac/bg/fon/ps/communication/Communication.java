/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.net.Socket;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.domain.Termin;

/**
 *
 * @author nikollace
 */
public class Communication {
    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private static Communication instance;
    
    private Communication() throws Exception{
        socket = new Socket("localhost", 7868);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    public static Communication getInstance() throws Exception{
        if(instance ==null){
            instance = new Communication();
        }
        return instance;
    }
    
    public Stomatolog login(String email, String password) throws Exception{
        Stomatolog stomatolog = new Stomatolog();
        stomatolog.setEmail(email);
        stomatolog.setPassword(password);
        Request request = new Request(Operations.LOGIN, stomatolog);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (Stomatolog)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public void logOut(Object param) throws Exception {
        Request request = new Request(Operations.LOGOUT, param);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
             
        } else {
            throw response.getException();
        }
    }
    
    public Stomatolog register(String firstName, String lastName, String email, String password, Date birthday, String adresa) throws Exception {
        Stomatolog stomatolog = new Stomatolog(firstName, lastName, email, password, birthday, adresa);
        Request request = new Request(Operations.REGISTER, stomatolog);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (Stomatolog)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Stomatolog> getAllDentists(String condition) throws Exception{
        Request request = new Request(Operations.GET_ALL_DENTISTS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Stomatolog>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Pacijent> getAllPatients(Pacijent condition) throws Exception{
        Request request = new Request(Operations.GET_ALL_PATIENTS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Pacijent>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Termin> getAllTermins(Termin condition) throws Exception{
        Request request = new Request(Operations.GET_ALL_TERMINS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Termin>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<Pregled> getAllMedicalExaminations(Pregled condition) throws Exception{
        Request request = new Request(Operations.GET_ALL_MEDICAL_EXAMINATIONS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<Pregled>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<StavkaPregleda> getAllMedicalExaminationItems(StavkaPregleda condition) throws Exception {
        Request request = new Request(Operations.GET_ALL_MEDICAL_EXAMINATION_ITEMS, condition);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            return (List<StavkaPregleda>)response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public void addPatient(Pacijent patient) throws Exception {
        Request request = new Request(Operations.ADD_PATIENT, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }

    public void editPatient(Pacijent patient) throws Exception {
        Request request = new Request(Operations.EDIT_PATIENT, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }

    public void deletePatient(Pacijent patient) throws Exception {
        Request request = new Request(Operations.DELETE_PATIENT, patient);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    //Termin
    
    public void addTermin(Termin termin) throws Exception {
        Request request = new Request(Operations.ADD_TERMIN, termin);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    
    public void editTermin(Termin termin) throws Exception {
        Request request = new Request(Operations.EDIT_TERMIN, termin);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    
     public void deleteTermin(Termin termin) throws Exception{
        Request request = new Request(Operations.DELETE_TERMIN, termin);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        } else {
            throw response.getException();
        }
    }
    
    public void addMedicalExamination(Pregled medicalExamination) throws Exception {
        Request request = new Request(Operations.ADD_MEDICAL_EXAMINATION, medicalExamination);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Pregled newMedicalExamination = (Pregled)response.getResult();
            medicalExamination.postaviId(newMedicalExamination.getId());
        } else {
            throw response.getException();
        }
    }
    
    public void editMedicalExamination(Pregled medicalExamination) throws Exception {
        Request request = new Request(Operations.EDIT_MEDICAL_EXAMINATION, medicalExamination);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Pregled newMedicalExamination = (Pregled)response.getResult();
            //medicalExamination.setId(newMedicalExamination.getId());
        } else {
            throw response.getException();
        }
    }
    
    public void deleteMedicalExamination(Pregled medicalExamination) throws Exception {
        Request request = new Request(Operations.DELETE_MEDICAL_EXAMINATION, medicalExamination);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            Pregled newMedicalExamination = (Pregled)response.getResult();
            //medicalExamination.setId(newMedicalExamination.getId());
        } else {
            throw response.getException();
        }
    }

    
}
