package pl.pijok.skyblock.blockValues;

import org.bukkit.Material;
import pl.pijok.skyblock.JsonUtils;
import pl.pijok.skyblock.SkyBlock;
import pl.pijok.skyblock.island.Island;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class BlockValues {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final SkyBlock plugin;
    private final BlockValuesMapper mapper;

    private BlockValuesConfig config;

    public BlockValues(SkyBlock plugin){
        this.plugin = plugin;
        this.mapper = new BlockValuesMapper();
    }

    public void load(){
        File file = new File(plugin.getDataFolder() + File.separator + "blockValues.json");

        if(!file.exists()){
            config = createDefaultConfig();
            JsonUtils.saveObject(file, mapper.mapToDto(config));
        }
        else{
            config = mapper.mapDto((BlockValuesConfigDto) JsonUtils.loadObject(file, BlockValuesConfigDto.class));
        }

        logger.info("Loaded blocks values");
    }

    public Double countIslandValue(Island island){
        AtomicReference<Double> value = new AtomicReference<>(0.0);
        island.getPlacedBlocks().forEach((material, amount) -> {
            if(config.getValues().containsKey(material)){
                value.set(value.get() + (amount * config.getValues().get(material)));
            }
        });

        return value.get();
    }

    private BlockValuesConfig createDefaultConfig(){
        HashMap<Material, Double> values = new HashMap<>();
        values.put(Material.STONE, 0.05);
        values.put(Material.DIAMOND_BLOCK, 100.0);

        return new BlockValuesConfig(values);
    }

}
