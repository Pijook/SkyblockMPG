package pl.pijok.skyblock.blockValues;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.HashMap;


public class BlockValuesMapper {

    public BlockValuesConfig mapDto(BlockValuesConfigDto dto){
        HashMap<Material, Double> values = new HashMap<>();
        dto.getValues().forEach((materialName, value) -> {
            values.put(Material.valueOf(materialName), value);
        });

        return new BlockValuesConfig(values);
    }

    public BlockValuesConfigDto mapToDto(BlockValuesConfig config){
        HashMap<String, Double> values = new HashMap<>();
        config.getValues().forEach((material, value) -> {
            values.put(material.name(), value);
        });

        return new BlockValuesConfigDto(values);
    }

}
