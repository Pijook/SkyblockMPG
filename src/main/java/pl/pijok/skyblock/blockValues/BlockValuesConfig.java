package pl.pijok.skyblock.blockValues;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class BlockValuesConfig {

    private HashMap<Material, Double> values;

}
