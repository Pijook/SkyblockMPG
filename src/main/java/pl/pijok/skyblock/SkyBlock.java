package pl.pijok.skyblock;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class SkyBlock extends JavaPlugin {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void onEnable() {
        logger.info("Starting skyblock plugin");

        loadPlugin(false);
    }

    @Override
    public void onDisable() {
        savePlugin();
    }

    public boolean loadPlugin(boolean reload){
        try{
            if(!reload){
                checkFileStructure();

                logger.info("Loading controllers");
                Controllers.load(this);

                logger.info("Loading listeners");
                Listeners.registerListeners(this);

                logger.info("Registering commands");
                Commands.register(this);

                logger.info("Loading deleted islands");
                Controllers.getIslandController().loadDeletedIslands();
            }

            Language.load(this);

            GeneralConfig.load(this);

            Controllers.getIslandController().loadConfig();
            Controllers.getIslandController().loadLastIslandLocation();

            Controllers.getBlockLimiter().load();
            Controllers.getBlockValues().load();

            if(!reload){
                Controllers.getIslandController().initAutoIslandSaving();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void checkFileStructure(){
        File folder = new File(this.getDataFolder().getName());
        if(!folder.exists()){
            folder.mkdir();
        }
    }

    public void savePlugin(){
        Controllers.getIslandController().saveConfig();
        Controllers.getIslandController().saveLastIslandLocation();
        Controllers.getIslandController().saveDeletedIslands();
    }
}
