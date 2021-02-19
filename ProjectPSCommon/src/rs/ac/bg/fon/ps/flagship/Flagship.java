/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.flagship;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.security.auth.login.LoginException;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.exceptions.user.RegisterException;

/**
 *
 * @author nikollace
 */
public class Flagship {
    
    private static Flagship instance;
    
    private Flagship() {
    }
    
    public static Flagship getInstance(){
        if(instance == null){
            instance = new Flagship();
            return instance;
        }
        return instance;
    }
    
    public Stomatolog login(List<Stomatolog> stomatolozi, String email, String password) throws Exception {
        for (Stomatolog stomatolog : stomatolozi) {
            if(stomatolog.getEmail().equals(email))
                if(stomatolog.getPassword().equals(password)){
                    stomatolog.setStatus("Prijavljen");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM. HH:mm:ss");
                    stomatolog.setVremePrijavljivanja(sdf.format(new Date()));
                    stomatolog.setIpAdresa(InetAddress.getLocalHost().getHostAddress());
                    return stomatolog;
                }
                else 
                    throw new LoginException("Nepostojeca sifra!");
        }
        throw new LoginException("Nepostojeci korisnik!");
    }
    
    public Stomatolog register(List<Stomatolog> stomatolozi, String firstName, String lastName, String email, String password, Date birthday, String adresa) throws Exception{
        for (Stomatolog stomatolog : stomatolozi) {
            if(stomatolog.getEmail().equals(email))
                throw new RegisterException("Stomotolog sa unetim email-om vec postoji u bazi.");    
        }
        Stomatolog s = new Stomatolog(firstName, lastName, email, password, birthday, adresa);
        s.setStatus("Prijavljen");
        return s;
    }
    
    public void postaviStatusAdd(List<Stomatolog> allUsers, List<Stomatolog> activeUsers){
        for (Stomatolog allUser : allUsers) {
            for (Stomatolog activeUser : activeUsers) {
                if(allUser.equals(activeUser)){
                    allUser.setStatus(activeUser.getStatus());
                    allUser.setVremePrijavljivanja(activeUser.getVremePrijavljivanja());
                    allUser.setVremeOdjavljivanja(activeUser.getVremeOdjavljivanja());
                    allUser.setIpAdresa(activeUser.getIpAdresa());
                }
            }
        }
    }
    
    public void postaviStatusRemove(Stomatolog s, List<Stomatolog> allUsers, SimpleDateFormat sdf){
        for (Stomatolog allUser : allUsers) {
            if(allUser.equals(s)){
                allUser.setStatus("Odjavljen");
                allUser.setVremeOdjavljivanja(sdf.format(new Date()));
            }
        }
    }
    
}
