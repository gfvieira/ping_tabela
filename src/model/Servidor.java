/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.InetAddress;

/**
 *
 * @author telematica
 */
public class Servidor extends Equipamento {

    public Servidor(String nome, InetAddress ip) {
        super(nome, ip);
    }

    @Override
    public String toString() {
        return (super.getNome() + super.getIp());
    }
    
}
