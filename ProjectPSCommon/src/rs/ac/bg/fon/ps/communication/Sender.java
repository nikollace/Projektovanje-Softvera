/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;

/**
 *
 * @author nikollace
 */
public class Sender {
    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }
    
    public void send(Object value) throws Exception {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.writeObject(value);
            out.flush();
        }catch(IOException e){
            //e.printStackTrace();
            throw new CommunicationException("Greska prilikom primanja objekta!\n"+e.getMessage());
        }
    }
}
