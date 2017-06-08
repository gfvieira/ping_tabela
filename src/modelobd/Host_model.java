/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelobd;

import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Gustavo
 */
public class Host_model {
    
   private int id;
   private String nome;
   private String ip;
   private String lacre;
   private String resp;
   private String mac;
   private String setor;
   private String tipo;
   private String so;
   private String user;
   private Date datacad;
   private LocalTime horacad;
   private int qnt;
   private String obs;


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return the setor
     */
    public String getSetor() {
        return setor;
    }

    /**
     * @param setor the setor to set
     */
    public void setSetor(String setor) {
        this.setor = setor;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the so
     */
    public String getSo() {
        return so;
    }

    /**
     * @param so the os to set
     */
    public void setSo(String so) {
        this.so = so;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the datacad
     */
    public Date getDatacad() {
        return datacad;
    }

    /**
     * @param datacad the datacad to set
     */
    public void setDatacad(Date datacad) {
        this.datacad = datacad;
    }

    /**
     * @return the horacad
     */
    public LocalTime getHoracad() {
        return horacad;
    }

    /**
     * @param horacad the horacad to set
     */
    public void setHoracad(LocalTime horacad) {
        this.horacad = horacad;
    }

    /**
     * @return the qnt
     */
    public int getQnt() {
        return qnt;
    }

    /**
     * @param qnt the qnt to set
     */
    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    /**
     * @return the lacre
     */
    public String getLacre() {
        return lacre;
    }

    /**
     * @param lacre the lacre to set
     */
    public void setLacre(String lacre) {
        this.lacre = lacre;
    }

    /**
     * @return the resp
     */
    public String getResp() {
        return resp;
    }

    /**
     * @param resp the resp to set
     */
    public void setResp(String resp) {
        this.resp = resp;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
    }
   
    
}
