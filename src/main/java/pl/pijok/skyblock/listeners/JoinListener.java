package pl.pijok.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.island.IslandController;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayer;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayerController;

import java.util.logging.Logger;

public class JoinListener implements Listener {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final SkyBlockPlayerController skyBlockPlayerController;
    private final IslandController islandController;

    public JoinListener(){
        skyBlockPlayerController = Controllers.getSkyBlockPlayerController();
        islandController = Controllers.getIslandController();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        skyBlockPlayerController.loadPlayer(player);
        skyBlockPlayerController.verifyPlayer(player);

        SkyBlockPlayer skyBlockPlayer = skyBlockPlayerController.getPlayer(player);

        if(skyBlockPlayer.hasIslandOrIsMember()){
            if(!islandController.isIslandLoaded(skyBlockPlayer.getIslandID())){
                islandController.loadIsland(skyBlockPlayer.getIslandID());
            }
        }
    }
}
