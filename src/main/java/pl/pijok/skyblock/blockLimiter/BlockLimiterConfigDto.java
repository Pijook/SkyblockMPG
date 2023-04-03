package pl.pijok.skyblock.blockLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlockLimiterConfigDto {

    private List<String> restrictedBlocks;
    private HashMap<Integer, HashMap<String, Integer>> blocksLimits;

}
