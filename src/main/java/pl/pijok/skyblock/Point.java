package pl.pijok.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Point {

    public static Point locationToPoint(Location location){
        return new Point(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        );
    }

    public static Location pointToLocation(Point point){
        return new Location(
                Bukkit.getWorld(point.world),
                point.getX(),
                point.getY(),
                point.getZ()
        );
    }

    private String world;
    private double x;
    private double y;
    private double z;

    public Point(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Point{" +
                "world='" + world + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
