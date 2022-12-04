package de.fynnhenck.homesystemapi.database;

import de.fynnhenck.homesystemapi.api.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseCommands {

    public boolean setHome(String username, String homeName, UUID uuid, Location loc){ //boolean states success; false = duplicate
        DatabaseConnection db = new DatabaseConnection();

        if(isDuplicate(uuid, homeName)){
            return false;
        }

        try {
            //Build SQL Command
            PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO `homesystem`.`homes`(`UUID`,`homeName`,`world`,`x`,`y`,`z`,`pitch`,`yaw`) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, homeName);
            ps.setString(3, loc.getWorld().getName());
            ps.setDouble(4, loc.getX());
            ps.setDouble(5, loc.getY());
            ps.setDouble(6, loc.getZ());
            ps.setFloat(7, loc.getPitch());
            ps.setFloat(8, loc.getYaw());

            //Write home into DB
            db.fetchUpdate(ps);
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Home getHome(UUID uuid, String homeName){
        DatabaseConnection db = new DatabaseConnection();

        try {
            PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM `homesystem`.`homes` WHERE uuid=? AND homeName=?");
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, homeName);

            ResultSet rs = db.fetchQuery(ps);

            if(rs.next()){

                int id = rs.getInt("id");
                World world = Bukkit.getWorld(rs.getString("world"));
                long x = rs.getLong("x");
                long y = rs.getLong("y");
                long z = rs.getLong("z");
                float pitch = rs.getFloat("pitch");
                float yaw = rs.getFloat("yaw");

                db.closeConnection();
                return new Home(id, uuid, homeName, world, x, y, z, pitch, yaw);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        db.closeConnection();
        return null;
    }

    public ArrayList<Home> getHomes(UUID uuid){

        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Home> homes = new ArrayList<Home>();

        try {
            PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM `homesystem`.`homes` WHERE uuid=?");
            ps.setString(1, String.valueOf(uuid));

            ResultSet rs = db.fetchQuery(ps);

            while(rs.next()){
                int id = rs.getInt("ID");
                String homeName = rs.getString("homeName");
                World world = Bukkit.getWorld(rs.getString("world"));
                long x = rs.getLong("x");
                long y = rs.getLong("y");
                long z = rs.getLong("z");
                float pitch = rs.getFloat("pitch");
                float yaw = rs.getFloat("yaw");


                Home tempHome = new Home(id, uuid, homeName, world, x, y, z, pitch, yaw);
                homes.add(tempHome);
            }
            db.closeConnection();
            return homes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteHome(UUID uuid, String homeName){ //boolean describes success
        DatabaseConnection db = new DatabaseConnection();
        int id = 0;
        //first get home; then delete vio primary key (id)

        try {

            PreparedStatement ps = db.getConnection().prepareStatement("SELECT ID FROM `homesystem`.`homes` WHERE uuid=? AND homeName=?");
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, homeName);

            ResultSet rs = db.fetchQuery(ps);

            if(rs.next()){
                id = rs.getInt("ID");
                PreparedStatement dps = db.getConnection().prepareStatement("DELETE FROM `homesystem`.`homes` WHERE ID=?");
                dps.setInt(1, id);
                db.fetchUpdate(dps);
                db.closeConnection();
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        db.closeConnection();
        return false;

    }


    private boolean isDuplicate(UUID uuid, String homeName){
        DatabaseConnection db = new DatabaseConnection();

        try {
            PreparedStatement ps = db.getConnection().prepareStatement("SELECT ID FROM `homesystem`.`homes` WHERE uuid=? AND homeName=?");
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, homeName);

            ResultSet rs = db.fetchQuery(ps);

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
