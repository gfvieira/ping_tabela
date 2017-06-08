/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingtabela;

import control.Controller;
import control.Monitoramento;
import control.PingServer;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author telematica
 */
public class PingTabela {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        Monitoramento monit = new Monitoramento();
        Thread threadserver = new Thread(monit);
        threadserver.start();

        TelaPrincipal tp = new TelaPrincipal();
        tp.setVisible(true);
        Controller ctrl = new Controller(tp, 11);

        Runtime r = Runtime.getRuntime();

        ctrl.atualizaTabela(r);

    }

}
