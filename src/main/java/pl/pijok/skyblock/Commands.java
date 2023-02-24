package pl.pijok.skyblock;

import pl.pijok.skyblock.commands.IslandCommand;

public class Commands {

    public static void register(SkyBlock plugin){
        plugin.getCommand("wyspa").setExecutor(new IslandCommand(plugin));
    }

}
