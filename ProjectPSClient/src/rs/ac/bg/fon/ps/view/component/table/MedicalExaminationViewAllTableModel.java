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
import rs.ac.bg.fon.ps.domain.Pacijent;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author nikollace
 */
public class MedicalExaminationViewAllTableModel extends AbstractTableModel{
    
    private List<Pregled> medicalExaminations;
    private List<Pregled> trazeniPregledi = new ArrayList<>();
    
    String[] columnNames = new String[]{"Cena", "Trajanje", "Pacijent", "Datum", "Vreme pocetka"};
    Class[] classNames = new Class[]{Double.class, Double.class, Pacijent.class, String.class, String.class};

    public MedicalExaminationViewAllTableModel(List<Pregled> medicalExaminations1) {
        this.medicalExaminations = medicalExaminations1;
        setEnglish();
    }
    
    private void setEnglish() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            columnNames = new String[]{"Price", "Duration", "Patient", "Date", "Time start"};
        }
    }

    @Override
    public int getRowCount() {
        return medicalExaminations.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Pregled medicalExamination = medicalExaminations.get(rowIndex);
        
        switch(columnIndex){
            case 0: return medicalExamination.getUkupnaCena();
            case 1: return medicalExamination.getUkupnoTrajanje();
            case 2: return medicalExamination.getPacijent();
            case 3: return sdf.format(medicalExamination.getTermin().getDatumTermina());
            case 4: return medicalExamination.getTermin().getVremeOd();
            default: return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classNames[columnIndex];
    } 
    
    public Pregled getMedicalExaminationAt(int rowIndex){
        return medicalExaminations.get(rowIndex);
    }
    
    public void isprazniListu(){
        trazeniPregledi = new ArrayList<>();
    }
    
    public void azurirajListu(Pregled pregled){
        
        if(!trazeniPregledi.contains(pregled)){
            
            trazeniPregledi.add(pregled);
        }
        
        this.medicalExaminations = trazeniPregledi;
        fireTableDataChanged();
    }
}
