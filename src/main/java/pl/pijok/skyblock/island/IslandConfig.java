package pl.pijok.skyblock.island;

import pl.pijok.skyblock.Point;

public class IslandConfig {

    private Point startingLocation;
    private int islandsDistance;
    private int islandSize;
    private int maxMembers;
    private String islandSchematic;

    public IslandConfig(Point startingLocation, int islandsDistance, int islandSize, int maxMembers, String islandSchematic) {
        this.startingLocation = startingLocation;
        this.islandsDistance = islandsDistance;
        this.islandSize = islandSize;
        this.maxMembers = maxMembers;
        this.islandSchematic = islandSchematic;
    }

    public Point getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(Point startingLocation) {
        this.startingLocation = startingLocation;
    }

    public int getIslandsDistance() {
        return islandsDistance;
    }

    public void setIslandsDistance(int islandsDistance) {
        this.islandsDistance = islandsDistance;
    }

    public int getIslandSize() {
        return islandSize;
    }

    public void setIslandSize(int islandSize) {
        this.islandSize = islandSize;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getIslandSchematic() {
        return islandSchematic;
    }

    public void setIslandSchematic(String islandSchematic) {
        this.islandSchematic = islandSchematic;
    }
}
