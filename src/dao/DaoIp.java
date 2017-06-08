//aplicação de ping tabela
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import modelobd.Host_model;

public class DaoIp {

    private ConnectDataBase connectDataBase = null;
    private Statement statement;
    private Error log = null;

    public DaoIp() {
        connectDataBase = new ConnectDataBase();
    }

    public boolean insereIp(Host_model host) {
        Timestamp tm = new Timestamp(System.currentTimeMillis());
        String t = new SimpleDateFormat("HH:mm:ss").format(tm);
        Date date = new Date();
        LocalTime thisSec = LocalTime.parse(t);
        String insereTableSQL = "INSERT INTO oficinabh34_host (nome, ip, mac, setor, tipo, so, nip, lacre, resp,"
                + "data_cad, hora_cad, obs) "
                + "VALUES ('" + host.getNome() + "', '" + host.getIp() + "', '" + host.getMac() + "', '" + host.getSetor() + "',"
                + "'" + host.getTipo() + "', '" + host.getSo() + "', '" + host.getUser() + "', '" + host.getLacre() + "', '" + host.getResp() + "',"
                + "'" + date + "', '" + thisSec + "', '" + host.getObs() + "');";

        try {
            if (connectDataBase.openConection() == null) {
                return false;
            }
            statement = connectDataBase.openConection().createStatement();
            statement.executeUpdate(insereTableSQL);
            //statement.close();
            //connectDataBase.closeConnection();
            return true;
        } catch (SQLException e) {
//            log = new Error();
//            log.Gravar(e.getMessage(), "DaoIp_insereIp");
            return false;
        } finally {
            connectDataBase.closeConnection();
        }
    }

   

    public ArrayList<Host_model> buscaHost(String type) {
        String selectTableSQL = "SELECT * from oficinabh34_host WHERE tipo='" + type + "' ORDER BY ip ASC;";

        ArrayList<Host_model> lista = new ArrayList<>();
        int i = 0;
        try {
            if (connectDataBase.openConection() == null) {
                return null;//criar uma variavel no usauario pra condicao do banco
            }
            statement = connectDataBase.openConection().createStatement();
            //statement = dbConnection.createStatement();       
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Host_model hostRetorno = new Host_model();
                hostRetorno.setNome(rs.getString("nome"));
                hostRetorno.setIp(rs.getString("ip"));
                hostRetorno.setMac(rs.getString("mac"));
                hostRetorno.setSetor(rs.getString("setor"));
                if (rs.getString("tipo").equals("HARDWARE")) {
                    hostRetorno.setTipo("COMPUTADOR");
                }
                if (rs.getString("tipo").equals("SERVER")) {
                    hostRetorno.setTipo("SERVIDOR");
                }
                if (rs.getString("tipo").equals("SWITCH")) {
                    hostRetorno.setTipo("SWITCH");
                }
                if (rs.getString("tipo").equals("IMPRESSORA")) {
                    hostRetorno.setTipo("IMPRESSORA");
                }
                if (rs.getString("tipo").equals("DVR")) {
                    hostRetorno.setTipo("DVR");
                }
                hostRetorno.setTipo(rs.getString("tipo"));
                hostRetorno.setSo(rs.getString("so"));
                hostRetorno.setUser(rs.getString("nip"));
                hostRetorno.setLacre(rs.getString("lacre"));
                hostRetorno.setResp(rs.getString("resp"));
                hostRetorno.setDatacad(rs.getDate("data_cad"));
                hostRetorno.setHoracad(LocalTime.parse(rs.getString("hora_cad")));
                hostRetorno.setQnt(++i);
                hostRetorno.setObs(rs.getString("obs"));
                lista.add(hostRetorno);
            }
            return lista;
        } catch (SQLException e) {
            return null;
        } finally {
            connectDataBase.closeConnection();
        }
    }

    public ArrayList<Host_model> buscaHostAll() {
        String selectTableSQL = "SELECT * from oficinabh34_host ORDER BY ip ASC;";
        ArrayList<Host_model> lista = new ArrayList<>();
        int i = 0;
        try {
            if (connectDataBase.openConection() == null) {
                return null;//criar uma variavel no usauario pra condicao do banco
            }
            statement = connectDataBase.openConection().createStatement();
            //statement = dbConnection.createStatement();       
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                Host_model hostRetorno = new Host_model();
                hostRetorno.setId(rs.getInt("id_host"));
                hostRetorno.setNome(rs.getString("nome"));
                hostRetorno.setIp(rs.getString("ip"));
                hostRetorno.setMac(rs.getString("mac"));
                hostRetorno.setSetor(rs.getString("setor"));
                
                if (rs.getString("tipo").equals("HARDWARE")) {
                    hostRetorno.setTipo("HARDWARE");
                } else {
                    if (rs.getString("tipo").equals("SERVER")) {
                        hostRetorno.setTipo("SERVER");
                    } else {
                        if (rs.getString("tipo").equals("SWITCH")) {
                            hostRetorno.setTipo("SWITCH");
                        } else {
                            if (rs.getString("tipo").equals("IMPRESSORA")) {
                                hostRetorno.setTipo("IMPRESSORA");
                            }
                        }
                    }
                }
                hostRetorno.setTipo(rs.getString("tipo"));
                hostRetorno.setSo(rs.getString("so"));
                hostRetorno.setLacre(rs.getString("lacre"));
                hostRetorno.setResp(rs.getString("resp"));
                hostRetorno.setUser(rs.getString("nip"));
                hostRetorno.setDatacad(rs.getDate("data_cad"));
                hostRetorno.setHoracad(LocalTime.parse(rs.getString("hora_cad")));
                hostRetorno.setQnt(++i);
                hostRetorno.setObs(rs.getString("obs"));
                lista.add(hostRetorno);
            }
            return lista;
        } catch (SQLException e) {
            return null;
        } finally {
            connectDataBase.closeConnection();
        }
    }

    public Host_model buscaIp(Host_model host) {
        String selectTableSQL = "SELECT * from oficinabh34_host where ip ='" + host.getIp() + "'";
        Host_model hostRetorno = new Host_model();
        try {
            if (connectDataBase.openConection() == null) {
                return null;//criar uma variavel no usauario pra condicao do banco
            }
            statement = connectDataBase.openConection().createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                hostRetorno.setId(Integer.parseInt(rs.getString("id_host")));
                hostRetorno.setNome(rs.getString("nome"));
                hostRetorno.setIp(rs.getString("ip"));
                hostRetorno.setMac(rs.getString("mac"));
                hostRetorno.setSetor(rs.getString("setor"));
                hostRetorno.setTipo(rs.getString("tipo"));
                hostRetorno.setSo(rs.getString("so"));
                hostRetorno.setUser(rs.getString("nip"));//usuario que cadastrou
                hostRetorno.setLacre(rs.getString("lacre"));
                hostRetorno.setResp(rs.getString("resp"));
                hostRetorno.setDatacad(rs.getDate("data_cad"));
                hostRetorno.setObs(rs.getString("obs"));
                hostRetorno.setHoracad(LocalTime.parse(rs.getString("hora_cad")));
            }
            return hostRetorno;
        } catch (SQLException e) {
            return null;
        } finally {
            connectDataBase.closeConnection();
        }
    }

    public boolean attHost(Host_model host) {
        Timestamp tm = new Timestamp(System.currentTimeMillis());
        String t = new SimpleDateFormat("HH:mm:ss").format(tm);
        Date date = new Date();
        LocalTime thisSec = LocalTime.parse(t);
        String insereTableSQL = "UPDATE oficinabh34_host SET nome='" + host.getNome() + "', ip='" + host.getIp() + "', mac='" + host.getMac() + "'"
                + ", setor='" + host.getSetor() + "', tipo='" + host.getTipo() + "', so='" + host.getSo() + "', nip='" + host.getUser() + "'"
                + ", lacre='" + host.getLacre() + "', resp='" + host.getResp() + "', data_cad='" + date + "', hora_cad='" + thisSec + "' WHERE id_host='" + host.getId() + "';";
        try {
            if (connectDataBase.openConection() == null) {
                return false;
            }
            statement = connectDataBase.openConection().createStatement();
            statement.executeUpdate(insereTableSQL);
            //statement.close();
            //connectDataBase.closeConnection();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            connectDataBase.closeConnection();
        }
    }

    public int buscaIdHost(String ip) {
        String selectTableSQL = "SELECT id_host from oficinabh34_host where ip ='" + ip + "'";
        int temp = 0;
        try {
            if (connectDataBase.openConection() == null) {
                return 0;//criar uma variavel no usauario pra condicao do banco
            }
            statement = connectDataBase.openConection().createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                temp = rs.getInt("id_host");
            }
            return temp;
        } catch (SQLException e) {
            return 0;
        } finally {
            connectDataBase.closeConnection();
        }
    }

}
