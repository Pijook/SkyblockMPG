package pl.pijok.skyblock;

import java.io.File;

public class GeneralConfig {

    private static GeneralConfig generalConfig;

    public static void load(SkyBlock plugin){
        File file = new File(plugin.getDataFolder() + File.separator + "config.json");

        if(!file.exists()){
            generalConfig = new GeneralConfig(
                    new Point("world", 0, 0, 0),
                    10,
                    "islands");

            JsonUtils.saveObject(file, generalConfig);
            return;
        }

        generalConfig = (GeneralConfig) JsonUtils.loadObject(file, GeneralConfig.class);
    }

    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    private final Point spawn;
    private final int maxPlayersPerIsland;
    private final String islandsWorld;

    public GeneralConfig(Point spawn, int maxPlayersPerIsland, String islandsWorld) {
        this.spawn = spawn;
        this.maxPlayersPerIsland = maxPlayersPerIsland;
        this.islandsWorld = islandsWorld;
    }

    public Point getSpawn() {
        return spawn;
    }

    public int getMaxPlayersPerIsland() {
        return maxPlayersPerIsland;
    }

    public String getIslandsWorld() {
        return islandsWorld;
    }
}
