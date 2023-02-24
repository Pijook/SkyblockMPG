package pl.pijok.skyblock;

import pl.pijok.skyblock.island.IslandController;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayerController;

public class Controllers {

    private static SkyBlockPlayerController skyBlockPlayerController;
    private static IslandController islandController;

    public static void load(SkyBlock plugin){
        skyBlockPlayerController = new SkyBlockPlayerController(plugin);
        islandController = new IslandController(plugin);
    }

    public static SkyBlockPlayerController getSkyBlockPlayerController() {
        return skyBlockPlayerController;
    }

    public static IslandController getIslandController() {
        return islandController;
    }

    public static void setIslandController(IslandController islandController) {
        Controllers.islandController = islandController;
    }
}
