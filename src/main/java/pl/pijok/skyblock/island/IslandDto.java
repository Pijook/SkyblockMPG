package pl.pijok.skyblock.island;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.pijok.skyblock.Point;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class IslandDto {

    private UUID islandID;
    private UUID ownerID;
    private List<UUID> members;
    private Point center;
    private Point point1;
    private Point point2;
    private int islandLevel;

}
