/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.cordinator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.controller.Kontroler;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.flagship.Flagship;
import rs.ac.bg.fon.ps.view.FrmStart;
import rs.ac.bg.fon.ps.view.controller.StartController;

/**
 *
 * @author nikollace
 */
public class MainCordinator {
    private static MainCordinator instance;
    private static List<Stomatolog> allUsers;
    private List<Stomatolog> activeUsers;
    
    public static MainCordinator getInstance(){
        if(instance == null){
            instance = new MainCordinator();
            return instance;
        }
        return instance;
    }

    private MainCordinator() {
        try {
                activeUsers = new ArrayList<>();
                allUsers = new ArrayList<>();
                Kontroler.getInstance().vratiSveStomatologe(allUsers);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(MainCordinator.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void openStartServer(){
        StartController startController = new StartController(new FrmStart());
        startController.openForm();
    }
    
    public void addUser(Stomatolog s) throws Exception {
        if(!activeUsers.contains(s)){
            activeUsers.add(s);
            Flagship.getInstance().postaviStatusAdd(allUsers, activeUsers);
        } else 
            throw new Exception("Korisnik je vec prijavljen na sistem!");
    }
    
    
    
    public void removeUser(Stomatolog s){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM. HH:mm:ss");
        Flagship.getInstance().postaviStatusRemove(s, allUsers, sdf);
        activeUsers.remove(s);
    }
    
    public List<Stomatolog> getUserList(){
        return allUsers;
    }
    
    public void clearList(){
        for (Stomatolog activeUser : activeUsers) {
            removeUser(activeUser);
        }
        activeUsers.clear();
    }
}
