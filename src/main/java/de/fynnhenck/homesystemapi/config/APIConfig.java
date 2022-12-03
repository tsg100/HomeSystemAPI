package de.fynnhenck.homesystemapi.config;

import de.fynnhenck.homesystemapi.HomeSystemAPI;
import de.fynnhenck.homesystemapi.database.DatabaseConnection;
import de.fynnhenck.homesystemapi.database.DatabaseCredentials;
import org.bukkit.configuration.file.FileConfiguration;

public class APIConfig{
    //get all things from config to server in methods later

    static DatabaseCredentials CREDS;

    public APIConfig(HomeSystemAPI main){
        FileConfiguration config = main.getConfig();
        main.saveDefaultConfig();

        //get DB Creds from Config
        String DB_HOST = config.getString("database.host");
        String DB_PORT = config.getString("database.port");
        String DB_DATABASE = config.getString("database.database");
        String DB_USER = config.getString("database.user");
        String DB_PASS = config.getString("database.password");

        //Instantiate DatabaseCredentials
        CREDS = new DatabaseCredentials(DB_HOST, DB_PORT, DB_DATABASE, DB_USER, DB_PASS);

        //Setup database structure at first launch
        if(!config.getBoolean("database.setup")){
            HomeSystemAPI.getPlugin(HomeSystemAPI.class).getLogger().warning("First start of Plugin! Setting up database!");
            DatabaseConnection dbc = new DatabaseConnection();
            if(dbc.firstSetup()){
             config.set("database.setup", true);
             main.saveConfig();
            }
        }

    }

    public static DatabaseCredentials getDatabaseCredentials(){
        return CREDS;
    }

}
