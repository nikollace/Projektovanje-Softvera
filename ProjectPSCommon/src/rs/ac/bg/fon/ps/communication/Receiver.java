/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;

/**
 *
 * @author nikollace
 */
public class Receiver {
    private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }
    
    public Object receive() throws Exception {
        try{
            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
            return oin.readObject();
        }catch(IOException | ClassNotFoundException e){
            //e.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja objekta!\n"+e.getMessage());
        }
        
    }
}
