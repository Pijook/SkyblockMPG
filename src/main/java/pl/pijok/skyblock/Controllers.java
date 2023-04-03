package pl.pijok.skyblock;

import lombok.Getter;
import lombok.Setter;
import pl.pijok.skyblock.blockLimiter.BlockLimiter;
import pl.pijok.skyblock.blockValues.BlockValues;
import pl.pijok.skyblock.island.IslandController;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayerController;

public class Controllers {

    private static SkyBlockPlayerController skyBlockPlayerController;
    private static IslandController islandController;
    private static BlockLimiter blockLimiter;
    private static BlockValues blockValues;

    public static void load(SkyBlock plugin){
        skyBlockPlayerController = new SkyBlockPlayerController(plugin);
        islandController = new IslandController(plugin);
        blockLimiter = new BlockLimiter(plugin);
        blockValues = new BlockValues(plugin);
    }

    public static SkyBlockPlayerController getSkyBlockPlayerController() {
        return skyBlockPlayerController;
    }

    public static IslandController getIslandController() {
        return islandController;
    }

    public static BlockLimiter getBlockLimiter() {
        return blockLimiter;
    }

    public static BlockValues getBlockValues() {
        return blockValues;
    }
}
