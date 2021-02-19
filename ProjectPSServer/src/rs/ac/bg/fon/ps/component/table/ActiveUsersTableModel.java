/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.component.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Stomatolog;

/**
 *
 * @author nikollace
 */
public class ActiveUsersTableModel extends AbstractTableModel{
    String[] columnNames = new String[]{"Name", "Email", "Prijava", "Odjava" ,"IP adresa", "Status"};
    Class[] classNames = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class};
    
    List<Stomatolog> activeUsers;

    public ActiveUsersTableModel(List<Stomatolog> activeUsers) {
        this.activeUsers = activeUsers;
    }

    @Override
    public int getRowCount() {
        return activeUsers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Stomatolog user = activeUsers.get(rowIndex);
        
        switch(columnIndex){
            case 0: return user.getIme() + " " + user.getPrezime();
            case 1: return user.getEmail();
            case 2: return user.getVremePrijavljivanja();
            case 3: return user.getVremeOdjavljivanja();
            case 4: return user.getIpAdresa();
            case 5: return user.getStatus(); 
            default: return "n/a";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classNames[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public void prosiriListu(List<Stomatolog> activeUsers){
        this.activeUsers = activeUsers;
    }
    
}
