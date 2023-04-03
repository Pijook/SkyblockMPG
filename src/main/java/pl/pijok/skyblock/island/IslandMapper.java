package pl.pijok.skyblock.island;

import pl.pijok.skyblock.Point;

import java.util.HashMap;

public class IslandMapper {

    public Island mapDto(IslandDto dto) {
        return new Island(
                dto.getIslandID(),
                dto.getOwnerID(),
                dto.getMembers(),
                Point.pointToLocation(dto.getCenter()),
                Point.pointToLocation(dto.getPoint1()),
                Point.pointToLocation(dto.getPoint2()),
                dto.getIslandLevel(),
                new HashMap<>()
        );
    }

    public IslandDto mapToDto(Island island) {
        return new IslandDto(
                island.getIslandID(),
                island.getOwnerID(),
                island.getMembers(),
                Point.locationToPoint(island.getCenter()),
                Point.locationToPoint(island.getPoint1()),
                Point.locationToPoint(island.getPoint2()),
                island.getIslandLevel()
        );
    }

}
