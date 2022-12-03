package de.fynnhenck.homesystemapi;

import de.fynnhenck.homesystemapi.config.APIConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystemAPI extends JavaPlugin {


    @Override
    public void onEnable() {
        APIConfig config = new APIConfig(this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
