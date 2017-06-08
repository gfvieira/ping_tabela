/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Component;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.StatusColumnCellRenderer;

/**
 *
 * @author telematica
 */
public class ThreadHandler implements Runnable {

    private JTable tabela;
    private int linhaInicio;
    private int linhaFinal;
    private Runtime rt;
    private int colIp;
    private int colStatus;
    private int tempoOcioso;

    public ThreadHandler(Runtime rt, JTable tabela, int linhaInicio, int linhaFinal, int colIP, int colStatus, int tempoOcioso) {
        this.rt = rt;
        this.tabela = tabela;
        this.linhaInicio = linhaInicio;
        this.linhaFinal = linhaFinal;
        this.colIp = colIP;
        this.colStatus = colStatus;
        this.tempoOcioso = tempoOcioso;
    }

    @Override
    public void run() {
        JTable tabelaServidores = this.tabela;
        DefaultTableModel model = (DefaultTableModel) tabelaServidores.getModel();

        PingServer ps = new PingServer();
        String ipDaVez;

        //DefaultTableCellRenderer test = new DefaultTableCellRenderer();
        //Component c;
        tabelaServidores.getColumnModel().getColumn(this.colStatus).setCellRenderer(new StatusColumnCellRenderer());
        for (int i = linhaInicio; i <= linhaFinal; i++) {
            try {
                ipDaVez = (String) model.getValueAt(i, this.colIp);
                if (ps.isReachablebyPing(this.rt, ipDaVez)) {
                    model.setValueAt("UP", i, this.colStatus);
                    //c = test.getTableCellRendererComponent(tabela, "UP", false, false, i, 2);
                } else {
                    model.setValueAt("DOWN", i, this.colStatus);
                    // c = test.getTableCellRendererComponent(tabela, "DOWN", false, false, i, 2);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            if (i == linhaFinal) {
                i = linhaInicio;
                try {
                    sleep(this.tempoOcioso);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
