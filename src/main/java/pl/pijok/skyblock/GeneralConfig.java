package pl.pijok.skyblock;

import java.io.File;

public class GeneralConfig {

    private static GeneralConfig generalConfig;

    public static void load(SkyBlock plugin){
        File file = new File(plugin.getDataFolder() + File.separator + "config.json");

        if(!file.exists()){
            generalConfig = new GeneralConfig(
                    new Point("world", 0, 0, 0)
            );

            JsonUtils.saveObject(file, generalConfig);
            return;
        }

        generalConfig = (GeneralConfig) JsonUtils.loadObject(file, GeneralConfig.class);
    }

    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    private final Point spawn;

    public GeneralConfig(Point spawn) {
        this.spawn = spawn;
    }

    public Point getSpawn() {
        return spawn;
    }
}
