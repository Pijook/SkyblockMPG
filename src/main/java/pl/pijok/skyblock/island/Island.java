package pl.pijok.skyblock.island;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Island {

    private UUID islandID;
    private UUID ownerID;
    private List<UUID> members;
    private Location center;
    private Location point1;
    private Location point2;
    private int islandLevel;

    public Island(UUID islandID, UUID ownerID, List<UUID> members, Location center, Location point1, Location point2, int islandLevel) {
        this.islandID = islandID;
        this.ownerID = ownerID;
        this.members = members;
        this.center = center;
        this.point1 = point1;
        this.point2 = point2;
        this.islandLevel = islandLevel;
    }

    public boolean isOnIsland(Player player){
        double x1 = point1.getX();
        double z1 = point1.getZ();

        double x2 = point2.getX();
        double z2 = point2.getZ();

        double xP = player.getLocation().getX();
        double zP = player.getLocation().getZ();

        return (x1 < xP && xP < x2) || (x1 > xP && xP > x2) && (z1 < zP && zP < z2) || (z1 > zP && zP > z2);
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

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public Location getPoint1() {
        return point1;
    }

    public void setPoint1(Location point1) {
        this.point1 = point1;
    }

    public Location getPoint2() {
        return point2;
    }

    public void setPoint2(Location point2) {
        this.point2 = point2;
    }

    public int getIslandLevel() {
        return islandLevel;
    }

    public void setIslandLevel(int islandLevel) {
        this.islandLevel = islandLevel;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }
}
