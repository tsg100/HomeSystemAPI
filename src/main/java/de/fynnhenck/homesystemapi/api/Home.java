package de.fynnhenck.homesystemapi.api;

import org.bukkit.Server;
import org.bukkit.World;

import java.util.UUID;

public class Home {

    int id;
    UUID uuid;
    String name;
    World world;
    long x;
    long y;
    long z;
    float pitch;
    float yaw;

    public Home(int id, UUID uuid, String name, World world, long x, long y, long z, float pitch, float yaw){

        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;

    }




}
