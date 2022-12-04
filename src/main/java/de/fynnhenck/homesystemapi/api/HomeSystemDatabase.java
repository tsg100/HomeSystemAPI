package de.fynnhenck.homesystemapi.api;

import de.fynnhenck.homesystemapi.database.DatabaseCommands;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.checkerframework.checker.formatter.qual.ReturnsFormat;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class HomeSystemDatabase {

    //Accessor for other plugins
    DatabaseCommands dbc = new DatabaseCommands();


    //boolean = successState
    public boolean setHome(Player p, String homeName){
        Location loc = p.getLocation();
        UUID uuid = p.getUniqueId();
        String username = Objects.requireNonNull(p.getPlayer()).getName();
        return dbc.setHome(username, homeName, uuid, loc);

    }

    public Home getHome(UUID uuid, String homeName){
        return dbc.getHome(uuid, homeName);
    }

    public ArrayList<Home> getHomes(UUID uuid){
        return dbc.getHomes(uuid);
    }

    //boolean = successState
    public boolean deleteHome(UUID uuid, String homeName){
        return dbc.deleteHome(uuid, homeName);
    }




}
