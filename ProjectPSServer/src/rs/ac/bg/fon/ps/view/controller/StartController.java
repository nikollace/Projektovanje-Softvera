/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import rs.ac.bg.fon.ps.component.table.ActiveUsersTableModel;
import rs.ac.bg.fon.ps.controller.Kontroler;
import rs.ac.bg.fon.ps.domain.Stomatolog;
import rs.ac.bg.fon.ps.properties.DBConfig;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.server.Server;
import rs.ac.bg.fon.ps.view.FrmStart;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author nikollace
 */
public class StartController implements SetProperties {

    FrmStart frmStart;
    Server server = null;
    boolean started;

    public StartController(FrmStart frmStart) {
        this.frmStart = frmStart;
        addActionListeners();
    }

    public void openForm() {
        try {
            prepareView();
        } catch (Exception ex) {
            Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        frmStart.setVisible(true);
    }

    private void addActionListeners() {
        frmStart.addStartServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null) {
                    server = new Server();
                    server.start();
                    started = true;
                    try {
                        MainCordinator.getInstance().clearList();
                        prepareUsersTable();
                        Refresh refresh = new Refresh();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frmStart.getPnlUsers().setVisible(true);
                    frmStart.getTxtServerStatus().setForeground(Color.CYAN);
                    frmStart.getTxtServerStatus().setText("Server started!");
                    ispisiPoruke(started);

                } else {
                    JOptionPane.showMessageDialog(frmStart, "Server is already running!\n", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        frmStart.addStopServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server != null) {
                    try {
                        server.stopServer();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    server = null;
                    started = false;
                    frmStart.getPnlUsers().setVisible(false);
                    frmStart.getTxtServerStatus().setText("Server stopped!");
                    frmStart.getTxtServerStatus().setForeground(Color.ORANGE);
                    ispisiPoruke(started);
                } else {
                    JOptionPane.showMessageDialog(frmStart, "Server is already stopped!\n", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        frmStart.addSaveDBConfigActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConfig db = new DBConfig();
                db.config(frmStart.getTxtURL().getText().trim(), frmStart.getTxtDBUsername().getText().trim(),
                         frmStart.getTxtDBPassword().getText().trim());
                JOptionPane.showMessageDialog(frmStart, "Successfuly configured database!");
            }
        });
    }

    private void ispisiPoruke(boolean start) {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (start) {
                    frmStart.getTxtServerStatus().setText("Server running!");
                    frmStart.getTxtServerStatus().setForeground(Color.green);
                } else {
                    frmStart.getTxtServerStatus().setText("Server is down!");
                    frmStart.getTxtServerStatus().setForeground(Color.red);
                }
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
    }

    private void prepareView() {
        frmStart.setLocationRelativeTo(null);
        frmStart.setResizable(false);
        readConfigProperties();
    }

    private void prepareUsersTable() throws Exception {
        List<Stomatolog> allUsers = new ArrayList<>();
        Kontroler.getInstance().vratiSveStomatologe(allUsers);
        ActiveUsersTableModel model = new ActiveUsersTableModel(allUsers);
        frmStart.getTblUsers().setModel(model);
        frmStart.getTblUsers().getColumnModel().getColumn(5).setCellRenderer(new StatusColumnCellRenderer());
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try { 
            input = new FileInputStream("config.properties");
            
  	    prop.load(input);
            frmStart.getTxtURL().setText(prop.getProperty("url"));
	    frmStart.getTxtDBUsername().setText(prop.getProperty("dbusername"));
            frmStart.getTxtDBPassword().setText(prop.getProperty("dbpassword"));
            
	  } catch (IOException ex){} 
            finally 
              { if (input != null) 
                    {  try { input.close();} catch (IOException e) {}
                    }
	     }
    }
    
    private class StatusColumnCellRenderer extends DefaultTableCellRenderer {

    @Override
    public JLabel getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //Cells are by default rendered as a JLabel.
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        //Get the status for the current row.
        ActiveUsersTableModel adminTableModel = (ActiveUsersTableModel) table.getModel();
        if (adminTableModel.getValueAt(row, col) == "Prijavljen") {
            label.setForeground(Color.GREEN);
        } else {
            label.setForeground(Color.RED);
        }

        //Return the JLabel which renders the cell.
        return label;
    }
}

    private class Refresh extends Thread {

        public Refresh() {
            start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    refreshTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void refreshTable() throws Exception {
            ActiveUsersTableModel model = (ActiveUsersTableModel) frmStart.getTblUsers().getModel();
            
            model.prosiriListu(MainCordinator.getInstance().getUserList());
            model.fireTableDataChanged();
        }
    }

}
