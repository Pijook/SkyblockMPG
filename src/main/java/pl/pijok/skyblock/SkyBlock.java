package pl.pijok.skyblock;

import org.bukkit.plugin.java.JavaPlugin;

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
                logger.info("Loading controllers");
                Controllers.load(this);

                logger.info("Loading listeners");
                Listeners.registerListeners(this);

                logger.info("Registering commands");
                Commands.register(this);

                logger.info("Loading deleted islands");
                Controllers.getIslandController().loadDeletedIslands();
            }

            Controllers.getIslandController().loadConfig();
            Controllers.getIslandController().loadLastIslandLocation();

            GeneralConfig.load(this);

            Language.load(this);

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

    public void savePlugin(){
        Controllers.getIslandController().saveConfig();
        Controllers.getIslandController().saveLastIslandLocation();
        Controllers.getIslandController().saveDeletedIslands();
    }
}
