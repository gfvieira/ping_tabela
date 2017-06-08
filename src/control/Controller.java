/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DaoIp;
import dao.Dao_Host;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Equipamento;
import model.Servidor;
import model.Switch;
import modelobd.Host_model;
import pingtabela.TelaPrincipal;

/**
 *
 * @author telematica
 */
public class Controller {

    private ArrayList<Equipamento> equi183;
    private ArrayList<Equipamento> equi184;
    private TelaPrincipal view;
    private ArrayList<Thread> handlers;
    private Runtime r;

    public Controller(TelaPrincipal view, int numThreads) throws UnknownHostException {
        this.equi183 = new ArrayList<>();
        this.equi184 = new ArrayList<>();
        this.view = view;
        limparJTable();
        DaoIp daoip = new DaoIp();
        ArrayList<Host_model> hosts = daoip.buscaHost("HARDWARE");

        int numHosts = hosts.size() / 4;

        System.out.println("Número de hosts: " + numHosts);

        for (int i = 0; i < numHosts * 4; i += 4) {
//            int aux = i * 4;
//            if (aux < hosts.size()) {
            addEquipamento("servidor",
                    "Ponto " + i,
                    hosts.get(i).getNome(),
                    hosts.get(i).getIp(),
                    hosts.get(i + 1).getNome(),
                    hosts.get(i + 1).getIp(),
                    hosts.get(i + 2).getNome(),
                    hosts.get(i + 2).getIp(),
                    hosts.get(i + 3).getNome(),
                    hosts.get(i + 3).getIp());
//            }
        }
//        for (int i = 0; i < hosts.size(); i++) {
//            String ip183_1 = "10.5.183." + i;
//            String ip184_1 = "10.5.184." + i;
//            String ip183_2 = "10.5.183." + (i + 128);
//            String ip184_2 = "10.5.184." + (i + 128);
//            addEquipamento("servidor", "Ponto " + i, ip183_1, ip183_2, ip184_1, ip184_2);
//        }
        this.handlers = geraHandlers(numThreads);

    }

    public ArrayList<Thread> geraHandlers(int numThreads) {
        ArrayList<Thread> handlers = new ArrayList<>();
        JTable tabelaEquip = this.view.getTabelaEquip();
        DefaultTableModel modelo = (DefaultTableModel) tabelaEquip.getModel();
        int numLinhasTabela = modelo.getRowCount();
        int numLinhasPorThread = (int) Math.ceil(numLinhasTabela / numThreads);

        for (int i = 0; i < numThreads; i++) {
            handlers.add(new Thread(new ThreadHandler(Runtime.getRuntime(),
                    tabelaEquip, (i * numLinhasPorThread),
                    (((i + 1) * numLinhasPorThread) - 1), 0, 2, 60000)));
        }

        for (int i = 0; i < numThreads; i++) {
            handlers.add(new Thread(new ThreadHandler(Runtime.getRuntime(),
                    tabelaEquip, (i * numLinhasPorThread),
                    (((i + 1) * numLinhasPorThread) - 1), 3, 5, 60000)));
        }

        for (int i = 0; i < numThreads; i++) {
            handlers.add(new Thread(new ThreadHandler(Runtime.getRuntime(),
                    tabelaEquip, (i * numLinhasPorThread),
                    (((i + 1) * numLinhasPorThread) - 1), 6, 8, 60000)));
        }

        for (int i = 0; i < numThreads; i++) {
            handlers.add(new Thread(new ThreadHandler(Runtime.getRuntime(),
                    tabelaEquip, (i * numLinhasPorThread),
                    (((i + 1) * numLinhasPorThread) - 1), 9, 11, 60000)));
        }

        JTable tabelaServ = this.view.getTabelaServidores();
        DefaultTableModel modeloServ = (DefaultTableModel) tabelaServ.getModel();
        int numServ = modeloServ.getRowCount();

        //for (int i = 0; i < numServ; i++) {
        handlers.add(new Thread(new ThreadHandler(Runtime.getRuntime(), tabelaServ, (0), (6), 1, 2, 90000)));
        //}

        JTable tabelaSwitch = this.view.getTabelaSwitch();
        DefaultTableModel modelSwitch = (DefaultTableModel) tabelaSwitch.getModel();
        int numSwitch = modelSwitch.getRowCount();
        System.out.println("numero de switchs" + numSwitch);

        // for (int i = 0; i < numSwitch; i++) {
        handlers.add(new Thread(new ThreadHandler(Runtime.getRuntime(), tabelaSwitch, (0), (21), 1, 2, 90000)));
        //}

        return handlers;
    }

    public void startView() {
        this.view.setVisible(true);
    }

    public ArrayList<Equipamento> getEquipamentos() {
        return equi183;
    }

    public Equipamento getEquipamento(String nome) {
        for (int i = 0; i < this.equi183.size(); i++) {
            if (this.equi183.get(i).getNome().equals(nome)) {
                return this.equi183.get(i);
            }
        }
        return null;
    }

    public void atualizaTabela(Runtime r1) throws IOException {
        System.out.println("# = " + this.handlers.size());
        for (int i = 0; i < this.handlers.size(); i++) {
            System.out.println("Lançando Thread" + i);
            this.handlers.get(i).start();
        }
    }

    private void insereTabela(Equipamento eq183_1, Equipamento eq183_2, Equipamento eq184_1, Equipamento eq184_2, JTable tabela) {
        JTable tabelaEquip = tabela;
        DefaultTableModel model = (DefaultTableModel) tabelaEquip.getModel();
        //System.out.println(eq.getIp().toString());
        model.addRow(new Object[]{
            eq183_1.getIp().toString().replace("/", ""), eq183_1.getHost(), "DOWN",
            eq183_2.getIp().toString().replace("/", ""), eq183_2.getHost(), "DOWN",
            eq184_1.getIp().toString().replace("/", ""), eq184_1.getHost(), "DOWN",
            eq184_2.getIp().toString().replace("/", ""), eq184_2.getHost(), "DOWN"});
    }

    public void addEquipamento(String tipo,
            String nome,
            String nome1,
            String ip183_1,
            String nome2,
            String ip183_2,
            String nome3,
            String ip184_1,
            String nome4,
            String ip184_2) throws UnknownHostException {

        Equipamento e183_1 = factoryMethodEquip(tipo);
        e183_1.setNome(nome);
        e183_1.setHost(nome1);
        e183_1.setIp(InetAddress.getByName(ip183_1));

        Equipamento e184_1 = factoryMethodEquip(tipo);
        e184_1.setNome("Equip");
        e184_1.setHost(nome2);
        e184_1.setIp(InetAddress.getByName(ip184_1));

        Equipamento e183_2 = factoryMethodEquip(tipo);
        e183_2.setNome(nome);
        e183_2.setHost(nome3);
        e183_2.setIp(InetAddress.getByName(ip183_2));

        Equipamento e184_2 = factoryMethodEquip(tipo);
        e184_2.setNome("Equip");
        e184_2.setHost(nome4);
        e184_2.setIp(InetAddress.getByName(ip184_2));

        this.equi183.add(e183_1);
        this.equi183.add(e183_2);
        this.equi184.add(e184_1);
        this.equi184.add(e184_2);
        insereTabela(e183_1, e183_2, e184_1, e184_2, this.view.getTabelaEquip());
    }

    public Equipamento factoryMethodEquip(String tipo) {
        switch (tipo) {
            case "switch":
                return new Switch("", null);
            case "servidor":
                return new Servidor("", null);
            default:
                return null;
        }
    }

    private void limparJTable() {
        DefaultTableModel tableModel = (DefaultTableModel) this.view.getTabelaEquip().getModel();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }
}
