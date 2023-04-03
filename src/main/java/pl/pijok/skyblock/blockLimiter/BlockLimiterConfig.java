package pl.pijok.skyblock.blockLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlockLimiterConfig {

    private List<Material> restrictedBlocks;
    private HashMap<Integer, HashMap<Material, Integer>> blocksLimits;

}
