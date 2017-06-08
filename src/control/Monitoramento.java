package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitoramento implements Runnable {

    ArrayList<String> outputLines1 = null;

    @Override
    public void run() {
        final String ip = "10.5.";
        int ip1 = 183;
        int ip2 = 0;
        while (true) {
            try {
                isReachablebyPing(ip + ip1 + "." + ip2);
                ip2++;
                if (ip2 > 255 && ip1 == 183) {
                    ip1++;
                    ip2 = 0;
                } else if (ip2 > 255 && ip1 == 183) { 
// criar um arraylist com os conectados e salvar no array 
//na segunda passagem verifica se tiver na lista e tiver off line salvar no banco a ultima hora vista
                    ip1 = 183;
                    ip2 = 0;
                }
            } catch (IOException ex) {
                Logger.getLogger(PingServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isReachablebyPing(String ip) throws IOException {

        String command;

        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            // For Windows
            command = "ping -n 2 " + ip;
        } else {
            // For Linux and OSX
            command = "ping -c 2 " + ip;
        }
        //Process proc = Runtime.getRuntime().exec(command);
        //StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
        //outputGobbler.start();
        outputLines1 = new ArrayList<>();
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(command);

            Scanner scanner = new Scanner(p.getInputStream());
            String resultado = scanner.useDelimiter("$$").next();
            outputLines1.add(resultado);
        } catch (IOException ex) {
            return false;
        }

        for (String line : outputLines1) {
            if (line.contains("Unreachable")) {
                return false;
            }
            if (line.contains("ttl=")) {
                return true;
            }
        }
        return false;
    }

}
