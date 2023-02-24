package pl.pijok.skyblock.skyblockPlayer;

import org.bukkit.entity.Player;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.island.Island;

import java.util.UUID;

public class SkyBlockPlayer {

    private final UUID skyblockPlayerUuid;
    private UUID islandID;

    public SkyBlockPlayer(UUID skyblockPlayerUuid, UUID islandID) {
        this.skyblockPlayerUuid = skyblockPlayerUuid;
        this.islandID = islandID;
    }

    public boolean hasIslandOrIsMember(){
        return islandID != null;
    }

    public boolean isOnHisIsland(Player player){
        if(islandID == null){
            return false;
        }

        Island island = Controllers.getIslandController().getIsland(islandID);
        return island.isOnIsland(player);
    }

    public UUID getSkyblockPlayerUuid() {
        return skyblockPlayerUuid;
    }

    public UUID getIslandID() {
        return islandID;
    }

    public void setIslandID(UUID islandID) {
        this.islandID = islandID;
    }
}
