package de.fynnhenck.homesystemapi.database;

import de.fynnhenck.homesystemapi.HomeSystemAPI;
import de.fynnhenck.homesystemapi.config.APIConfig;
import org.bukkit.Bukkit;

import java.sql.*;

public class DatabaseConnection {
    Connection conn;

    public DatabaseConnection(){
        //Start connection
        DatabaseCredentials creds = APIConfig.getDatabaseCredentials();
        String url = "jdbc:mysql://" + creds.getHost() + ":" + creds.getPort() + "/" + creds.getDatabase();
        try {
            conn = DriverManager.getConnection(url, creds.getUsername(), creds.getPassword());


        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("Die Datenbankverbindung ist fehlgeschlagen!");
        }

    }

    //setup databases for first use; Boolean determines if successfully
    public boolean firstSetup(){

        String sql ="CREATE TABLE `homes` (" +
                "  `ID` int NOT NULL AUTO_INCREMENT," +
                "  `uuid` varchar(45) DEFAULT NULL," +
                "  `homeName` varchar(45) DEFAULT NULL," +
                "  `world` varchar(45) DEFAULT NULL," +
                "  `x` bigint DEFAULT NULL," +
                "  `z` bigint DEFAULT NULL," +
                "  `y` bigint DEFAULT NULL," +
                "  `pitch` float DEFAULT NULL," +
                "  `yaw` float DEFAULT NULL," +
                "  PRIMARY KEY (`ID`)" +
                ");";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            fetchUpdate(ps);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void fetchUpdate(PreparedStatement ps){

        try {
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet fetchQuery(PreparedStatement ps) {

        try {
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Connection getConnection(){
        return conn;
    }


    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
