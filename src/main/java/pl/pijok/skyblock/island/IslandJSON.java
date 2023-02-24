package pl.pijok.skyblock.island;

import org.bukkit.Location;
import pl.pijok.skyblock.Point;

import java.util.List;
import java.util.UUID;

public class IslandJSON {

    public static IslandJSON islandToIslandJSON(Island island){
        return new IslandJSON(
                island.getIslandID(),
                island.getOwnerID(),
                island.getMembers(),
                Point.locationToPoint(island.getCenter()),
                Point.locationToPoint(island.getPoint1()),
                Point.locationToPoint(island.getPoint2()),
                island.getIslandLevel()
        );
    }

    public static Island islandJSONToIsland(IslandJSON island){
        return new Island(
                island.getIslandID(),
                island.getOwnerID(),
                island.getMembers(),
                Point.pointToLocation(island.getCenter()),
                Point.pointToLocation(island.getPoint1()),
                Point.pointToLocation(island.getPoint2()),
                island.getIslandLevel()
        );
    }

    private UUID islandID;
    private UUID ownerID;
    private List<UUID> members;
    private Point center;
    private Point point1;
    private Point point2;
    private int islandLevel;

    public IslandJSON(UUID islandID, UUID ownerID, List<UUID> members, Point center, Point point1, Point point2, int islandLevel) {
        this.islandID = islandID;
        this.ownerID = ownerID;
        this.members = members;
        this.center = center;
        this.point1 = point1;
        this.point2 = point2;
        this.islandLevel = islandLevel;
    }

    public UUID getIslandID() {
        return islandID;
    }

    public void setIslandID(UUID islandID) {
        this.islandID = islandID;
    }

    public UUID getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(UUID ownerID) {
        this.ownerID = ownerID;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public int getIslandLevel() {
        return islandLevel;
    }

    public void setIslandLevel(int islandLevel) {
        this.islandLevel = islandLevel;
    }
}
