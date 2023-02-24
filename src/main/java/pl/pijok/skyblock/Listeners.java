package pl.pijok.skyblock;

import org.bukkit.plugin.PluginManager;
import pl.pijok.skyblock.listeners.JoinListener;
import pl.pijok.skyblock.listeners.QuitListener;

public class Listeners {

    public static void registerListeners(SkyBlock plugin){
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new JoinListener(), plugin);
        pluginManager.registerEvents(new QuitListener(), plugin);
    }

}
