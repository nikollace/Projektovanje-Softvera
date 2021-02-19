/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Pacijent;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
/**
 *
 * @author nikollace
 */
public class PacijentTableModel extends AbstractTableModel{
    private List<Pacijent> pacijenti;
    private List<Pacijent> trazeniPacijenti;
    private String[] columnNames = new String[] {"Ime", "Prezime", "Email", "Adresa", "Datum rodjenja"};
    private Class[] columnClasses = new Class[] {String.class, String.class, String.class, String.class, Date.class};

    public PacijentTableModel(List<Pacijent> pacijenti) {
        this.pacijenti = pacijenti;
        trazeniPacijenti = new ArrayList<>();
        setEnglish();
    }
    
    private void setEnglish() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            columnNames = new String[]{"Firstname", "Lastname", "Email", "Address", "Date birth"};
        }
    }
   
    @Override
    public String getColumnName(int column) {
        if(column>columnNames.length)
            return "n/a";
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex>columnClasses.length)
            return Object.class;
        return columnClasses[columnIndex];
    }

    @Override
    public int getRowCount() {
        if(pacijenti == null)
            return 0;
        return pacijenti.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pacijent pacijent = pacijenti.get(rowIndex);
        switch(columnIndex){
            case 0: 
                return pacijent.getIme();
            case 1: 
                return pacijent.getPrezime();
            case 2: 
                return pacijent.getEmail();
            case 3: 
                return pacijent.getAdresa();
            case 4: 
                return pacijent.getDatumRodjenja();
            default:
                return "n/a";
            
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Pacijent pacijent = pacijenti.get(rowIndex);
        switch(columnIndex) {
            case 0:
                pacijent.setIme(value.toString());
                break;
            case 1:
                pacijent.setPrezime(value.toString());
                break;
            case 2:
                pacijent.setEmail(value.toString());
                break;
            case 3:
                pacijent.setAdresa(value.toString());
                break;
            case 4:
                pacijent.setDatumRodjenja((Date)value);
        }
    }    
    
    public void addPatient(Pacijent pacijent){
        this.pacijenti.add(pacijent);
        //fireTableDataChanged();
        
        fireTableRowsInserted(pacijenti.size()-1, pacijenti.size()-1);
    }
    
    public Pacijent deletePatient(int row){
        Pacijent izbrisaniPacijent = this.pacijenti.remove(row);
        this.fireTableRowsInserted(pacijenti.size()-1, pacijenti.size()-1);
        return izbrisaniPacijent;
    }
    
    public Pacijent getPatientAt(int rowIndex){
        return pacijenti.get(rowIndex);
    }

    public void refreshPatients() {
        fireTableDataChanged();
    } 
    
    public void isprazniListu(){
        trazeniPacijenti = new ArrayList<>();
    }
    
    public void azurirajListu(Pacijent patient){
        if(!trazeniPacijenti.contains(patient))
            trazeniPacijenti.add(patient);
        
        this.pacijenti = trazeniPacijenti;
        fireTableDataChanged();
    }
}
