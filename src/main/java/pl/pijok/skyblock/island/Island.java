package pl.pijok.skyblock.island;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Island {

    private UUID islandID;
    private UUID ownerID;
    private List<UUID> members;
    private Location center;
    private Location point1;
    private Location point2;
    private int islandLevel;
    private HashMap<Material, Integer> placedBlocks;
    private ArrayList<UUID> islandChatMembers;

    public boolean isOnIsland(Player player){
        double x1 = point1.getX();
        double z1 = point1.getZ();

        double x2 = point2.getX();
        double z2 = point2.getZ();

        double xP = player.getLocation().getX();
        double zP = player.getLocation().getZ();

        return (x1 < xP && xP < x2) || (x1 > xP && xP > x2) && (z1 < zP && zP < z2) || (z1 > zP && zP > z2);
    }

    public boolean hasPlayerIslandChatEnabled(UUID playerID){
        return islandChatMembers.contains(playerID);
    }

    public ArrayList<Player> getIslandChatMembers(){
        ArrayList<Player> players = new ArrayList<>();
        for(UUID uuid : islandChatMembers){
            players.add(Bukkit.getPlayer(uuid));
        }
        return players;
    }

    public boolean setChat(UUID playerID) {
        if (islandChatMembers.contains(playerID)) {
            islandChatMembers.remove(playerID);
            return false;
        } else {
            islandChatMembers.add(playerID);
            return true;
        }
    }
}
