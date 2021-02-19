/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Termin;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author nikollace
 */
public class ScheduledTerminsTableModel extends AbstractTableModel{
    
    private List<Termin> termins;
    private List<Termin> trazeniTermini = new ArrayList<>();
    
    String[] columnNames = new String[]{"Datum termina", "Vreme pocetka", "Vreme predvidjenog zavrsetka"};
    Class[] classNames = new Class[]{String.class, String.class, String.class};

    public ScheduledTerminsTableModel(List<Termin> termins) {
        this.termins = termins;
        setEnglish();
    }
    
    private void setEnglish() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            columnNames = new String[]{"Date", "Time start", "Time end"};
        }
    }

    @Override
    public int getRowCount() {
        return termins.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Termin termin = termins.get(rowIndex);
        
        switch(columnIndex){
            case 0: return sdf.format(termin.getDatumTermina());
            case 1: return termin.getVremeOd();
            case 2: return termin.getVremeDo();
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
    
    public Termin getMedicalExaminationAt(int rowIndex){
        return termins.get(rowIndex);
    }
    
    public void isprazniListu(){
        trazeniTermini = new ArrayList<>();
    }
    
    public void azurirajListu(Termin termin){
        
        if(!trazeniTermini.contains(termin)){
            
            trazeniTermini.add(termin);
        }
        
        this.termins = trazeniTermini;
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
