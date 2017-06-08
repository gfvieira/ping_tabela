package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PingServer {

    public boolean isReachablebyPing(Runtime r1, String ip) throws IOException {

        String command;
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            // For Windows
            command = "ping -n 1 " + ip;
        } else {
            // For Linux and OSX
            command = "ping -c 1 " + ip;
        }

        String resultado;

        try {
            Scanner scanner = new Scanner(r1.exec(command).getInputStream());
            resultado = scanner.useDelimiter("$$").next();
//            if ("10.5.176.72".equals(ip)) {
//                System.out.println(resultado);
//            }
            //outputLines1.add(resultado);
        } catch (Exception ex) {
            return false;
        }

        if (resultado.contains("Unreachable")) {
            System.out.println(ip + " DONW");
            System.out.println(resultado);
            return false;
        }
        if (resultado.contains("ttl=")) {
            System.out.println(ip + " UP");
            System.out.println(resultado);
            return true;
        }
        return false;
    }

}
