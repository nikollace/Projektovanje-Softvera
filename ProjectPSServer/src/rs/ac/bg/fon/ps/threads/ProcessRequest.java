/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.threads;

import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.controller.Kontroler;
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.domain.Termin;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author nikollace
 */
public class ProcessRequest extends Thread{
    Socket socket;
    Sender sender;
    Receiver receiver;
    
    public ProcessRequest(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        while(!socket.isClosed()){
            Request request = null;
            try {
                request = (Request)receiver.receive();
            } catch (Exception ex) {
                //Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Response response = new Response();
            try{
                switch(request.getOperation()){
                    case LOGIN:
                        Stomatolog stomatologLogin = (Stomatolog)request.getArgument();
                        Kontroler.getInstance().prijava(stomatologLogin);
                        stomatologLogin.setStatus("Prijavljen");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM. HH:mm:ss");
                        stomatologLogin.setVremePrijavljivanja(sdf.format(new Date()));
                        stomatologLogin.setIpAdresa(InetAddress.getLocalHost().getHostAddress());
                        response.setResult(stomatologLogin);
                        MainCordinator.getInstance().addUser(stomatologLogin);
                        break;
                    case REGISTER:
                        Stomatolog stomatologRegister = (Stomatolog)request.getArgument();
                        Kontroler.getInstance().registracija(stomatologRegister);
                        stomatologRegister.setStatus("Prijavljen");
                        sdf = new SimpleDateFormat("dd.MM. HH:mm:ss");
                        stomatologRegister.setVremePrijavljivanja(sdf.format(new Date()));
                        stomatologRegister.setIpAdresa(InetAddress.getLocalHost().getHostAddress());
                        response.setResult(stomatologRegister);
                        MainCordinator.getInstance().addUser(stomatologRegister);
                        break;
                    case LOGOUT: 
                        Stomatolog stomatologlogOut = (Stomatolog)request.getArgument();
                        MainCordinator.getInstance().removeUser(stomatologlogOut);
                        break;
                    case GET_ALL_PATIENTS:
                        List<Pacijent> pacijenti = new ArrayList<>();
                        Kontroler.getInstance().vratiSvePacijente(pacijenti);
                        response.setResult(pacijenti);
                        break;
                    case GET_ALL_DENTISTS:
                        List<Stomatolog> stomatolozi = new ArrayList<>();
                        Kontroler.getInstance().vratiSveStomatologe(stomatolozi);
                        response.setResult(stomatolozi);
                        break;
                    case GET_ALL_MEDICAL_EXAMINATIONS:
                        List<Pregled> pregledi = new ArrayList<>();
                        pregledi.add((Pregled)request.getArgument());
                        Kontroler.getInstance().vratiSvePreglede(pregledi);
                        response.setResult(pregledi);
                        break;
                    case GET_ALL_MEDICAL_EXAMINATION_ITEMS:
                        List<StavkaPregleda> stavke = new ArrayList<>();
                        stavke.add((StavkaPregleda)request.getArgument());
                        Kontroler.getInstance().vratiSveStavke(stavke);
                        response.setResult(stavke);
                        break;
                    case GET_ALL_TERMINS:
                        List<Termin> termini = new ArrayList<>();
                        Kontroler.getInstance().vratiSveTermine(termini);
                        response.setResult(termini);
                        break;
                    case ADD_PATIENT:
                        Pacijent patientAdd = (Pacijent)request.getArgument();
                        Kontroler.getInstance().dodajPacijenta(patientAdd);
                        break;
                    case EDIT_PATIENT:
                        Pacijent patientEdit = (Pacijent)request.getArgument();
                        Kontroler.getInstance().izmeniPacijenta(patientEdit);
                        break;
                    case DELETE_PATIENT:
                        Pacijent patientDelete = (Pacijent)request.getArgument();
                        Kontroler.getInstance().obrisiPacijenta(patientDelete);
                        break;
                    case ADD_TERMIN:
                        Termin terminAdd = (Termin)request.getArgument();
                        Kontroler.getInstance().dodajTermin(terminAdd);
                        break;
                    case ADD_MEDICAL_EXAMINATION:
                        Pregled medicalExaminationAdd = (Pregled)request.getArgument();
                        Kontroler.getInstance().dodajPregled(medicalExaminationAdd);
                        response.setResult(medicalExaminationAdd);
                        break;
                    case EDIT_MEDICAL_EXAMINATION:
                        Pregled medicalExaminationEdit = (Pregled)request.getArgument();
                        Kontroler.getInstance().izmeniPregled(medicalExaminationEdit);
                        //response.setResult(medicalExaminationEdit); // TO CHECK
                        break;
                    case DELETE_MEDICAL_EXAMINATION:
                        Pregled medicalExaminationDelete = (Pregled)request.getArgument();
                        Kontroler.getInstance().obrisiPregled(medicalExaminationDelete);
                        //response.setResult(medicalExaminationDelete); // TO CHECK
                        break;
                }
            } catch(Exception e){
                //e.printStackTrace();
                response.setException(e);
            }
            try {
                sender.send(response);
            } catch (Exception ex) {
//                ex.printStackTrace();
//                Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
