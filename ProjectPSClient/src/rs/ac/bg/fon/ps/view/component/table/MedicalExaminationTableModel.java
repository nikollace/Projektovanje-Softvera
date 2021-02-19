/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Pregled;
import rs.ac.bg.fon.ps.domain.StavkaPregleda;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author nikollace
 */
public class MedicalExaminationTableModel extends AbstractTableModel {

    private final Pregled pregled;

    private String[] columnNames = new String[]{"Rbr", "Naziv", "Cena", "Trajanje", "Opis"};

    public MedicalExaminationTableModel(Pregled pregled) {
        this.pregled = pregled;
        setEnglish();
    }

    public Pregled getMedicalExamination() {
        return pregled;
    }

    private void setEnglish() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            columnNames = new String[]{"No.", "Name", "Price", "Duration", "Description"};
        }
    }

    @Override
    public int getRowCount() {
        if (pregled != null) {
            return pregled.getStavkePregleda().size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaPregleda stavkaPregleda = pregled.getStavkePregleda().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return stavkaPregleda.getRbr();
            case 1:
                return stavkaPregleda.getNazivPregleda();
            case 2:
                return stavkaPregleda.getCena();
            case 3:
                return stavkaPregleda.getTrajanje();
            case 4:
                return stavkaPregleda.getOpis();
            default:
                return "n/a";
        }
    }

    public String getMedicalItemDescription(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.valueOf(pregled.getStavkePregleda().get(rowIndex).getRbr());
            case 1:
                return pregled.getStavkePregleda().get(rowIndex).getNazivPregleda();
            case 2:
                return String.valueOf(pregled.getStavkePregleda().get(rowIndex).getCena());
            case 3:
                return String.valueOf(pregled.getStavkePregleda().get(rowIndex).getTrajanje());
            case 4:
                return pregled.getStavkePregleda().get(rowIndex).getOpis();
            default:
                return "n/a";
        }

    }

    public void addMedicalExaminationItem(String naziv, double cena, double trajanje, String opis) {
        StavkaPregleda stavkaPregleda = new StavkaPregleda();
        stavkaPregleda.setPregled(pregled);
        stavkaPregleda.setRbr(pregled.getStavkePregleda().size() + 1);
        stavkaPregleda.setNazivPregleda(naziv);
        stavkaPregleda.setCena(cena);
        stavkaPregleda.setTrajanje(trajanje);
        stavkaPregleda.setOpis(opis);
        pregled.getStavkePregleda().add(stavkaPregleda);
        pregled.setUkupnaCena(pregled.getUkupnaCena() + stavkaPregleda.getCena());
        pregled.setUkupnoTrajanje(pregled.getUkupnoTrajanje() + stavkaPregleda.getTrajanje());
        fireTableRowsInserted(pregled.getStavkePregleda().size() - 1, pregled.getStavkePregleda().size() - 1);
    }

    public void removeMedicalExaminationItem(int rowIndex) {
        StavkaPregleda stavkaPregleda = pregled.getStavkePregleda().get(rowIndex);
        pregled.getStavkePregleda().remove(rowIndex);
        pregled.setUkupnaCena(pregled.getUkupnaCena() - stavkaPregleda.getCena());
        pregled.setUkupnoTrajanje(pregled.getUkupnoTrajanje() - stavkaPregleda.getTrajanje());
        setOrderNumbers();
        fireTableRowsInserted(pregled.getStavkePregleda().size() - 1, pregled.getStavkePregleda().size() - 1);
    }

    private void setOrderNumbers() {
        int orderNo = 0;
        for (StavkaPregleda stavka : pregled.getStavkePregleda()) {
            stavka.setRbr(++orderNo);
        }
    }

}
