package pl.pijok.skyblock;

import pl.pijok.skyblock.commands.IslandCommand;
import pl.pijok.skyblock.commands.IslandCommandTabCompleter;

public class Commands {

    public static void register(SkyBlock plugin){
        plugin.getCommand("wyspa").setExecutor(new IslandCommand(plugin));

        plugin.getCommand("wyspa").setTabCompleter(new IslandCommandTabCompleter());
    }

}
