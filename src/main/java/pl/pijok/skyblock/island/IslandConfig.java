package pl.pijok.skyblock.island;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.pijok.skyblock.Point;

@Getter
@Setter
@AllArgsConstructor
public class IslandConfig {

    private Point startingLocation;
    private int islandsDistance;
    private int islandSize;
    private int maxMembers;
    private String islandSchematic;

}
