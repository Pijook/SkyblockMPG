package pl.pijok.skyblock.blockLimiter;

import org.bukkit.Material;
import pl.pijok.skyblock.ChatUtils;
import pl.pijok.skyblock.JsonUtils;
import pl.pijok.skyblock.SkyBlock;
import pl.pijok.skyblock.island.Island;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class BlockLimiter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final SkyBlock plugin;
    private final BlockLimiterConfigMapper mapper;

    private BlockLimiterConfig config;

    public BlockLimiter(SkyBlock plugin){
        this.plugin = plugin;
        this.mapper = new BlockLimiterConfigMapper();
    }

    public void load(){
        File file = new File(plugin.getDataFolder() + File.separator + "blockLimiter.json");

        if(!file.exists()){
            config = createDefaultConfig();
            JsonUtils.saveObject(file, mapper.mapToDto(config));
        }
        else{
            config = mapper.mapDto((BlockLimiterConfigDto) JsonUtils.loadObject(file, BlockLimiterConfigDto.class));
        }

        logger.info("Loaded block limiter");
    }

    public boolean isBlockRestricted(Material material){
        return config.getRestrictedBlocks().contains(material);
    }

    public boolean canPlaceNextBlock(Island island, Material material){
        HashMap<Material, Integer> amounts = config.getBlocksLimits().get(island.getIslandLevel());

        if(!amounts.containsKey(material)){
            return true;
        }

        return amounts.get(material) > island.getPlacedBlocks().get(material);
    }

    private BlockLimiterConfig createDefaultConfig(){
        HashMap<Material, Integer> materials = new HashMap<>();
        HashMap<Integer, HashMap<Material, Integer>> levels = new HashMap<>();

        ArrayList<Material> restrictedBlocks = new ArrayList<>();

        restrictedBlocks.add(Material.STONE);
        restrictedBlocks.add(Material.REDSTONE);

        materials.put(Material.STONE, 100);
        materials.put(Material.REDSTONE, 50);

        levels.put(1, materials);

        materials.clear();

        materials.put(Material.STONE, 500);
        materials.put(Material.REDSTONE, 5000);

        levels.put(2, materials);

        return new BlockLimiterConfig(restrictedBlocks,levels);
    }

}
