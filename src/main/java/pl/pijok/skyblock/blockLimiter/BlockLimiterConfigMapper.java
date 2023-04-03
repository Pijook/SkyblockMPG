package pl.pijok.skyblock.blockLimiter;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockLimiterConfigMapper {

    public BlockLimiterConfig mapDto(BlockLimiterConfigDto dto){
        List<Material> restrictedBlocks = new ArrayList<>();
        HashMap<Integer, HashMap<Material, Integer>> materials = new HashMap<>();

        dto.getBlocksLimits().forEach((key, value) -> {
            HashMap<Material, Integer> map = new HashMap<>();
            value.forEach((materialName, amount) -> {
                Material material = Material.valueOf(materialName);
                if(!restrictedBlocks.contains(material)){
                    restrictedBlocks.add(material);
                }
                map.put(material, amount);
            });

            materials.put(key, map);
        });

        return new BlockLimiterConfig(restrictedBlocks,materials);
    }

    public BlockLimiterConfigDto mapToDto(BlockLimiterConfig config){
        List<String> restrictedBlocks = new ArrayList<>();
        HashMap<Integer, HashMap<String, Integer>> materials = new HashMap<>();

        config.getBlocksLimits().forEach((key, value) -> {
            HashMap<String, Integer> map = new HashMap<>();
            value.forEach((material, amount) -> {
                if(!restrictedBlocks.contains(material.name())){
                    restrictedBlocks.add(material.name());
                }

                map.put(material.name(), amount);
            });

            materials.put(key, map);
        });

        return new BlockLimiterConfigDto(restrictedBlocks, materials);
    }

}
