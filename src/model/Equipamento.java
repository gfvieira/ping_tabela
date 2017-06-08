package model;

import java.net.InetAddress;

/**
 *
 * @author telematica
 */
public abstract class Equipamento {
    private String nome;
    private String host;
    private InetAddress ip;
    
    public Equipamento(String nome, InetAddress ip){
        this.nome = nome;
        this.ip = ip;
    }
    
    @Override
    public abstract String toString();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }
    
}
