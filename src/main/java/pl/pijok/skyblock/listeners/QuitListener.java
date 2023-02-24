package pl.pijok.skyblock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.island.IslandController;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayer;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayerController;

import java.util.logging.Logger;

public class QuitListener implements Listener {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final SkyBlockPlayerController skyBlockPlayerController;
    private final IslandController islandController;

    public QuitListener(){
        skyBlockPlayerController = Controllers.getSkyBlockPlayerController();
        islandController = Controllers.getIslandController();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        SkyBlockPlayer skyBlockPlayer = skyBlockPlayerController.getPlayer(player);

        skyBlockPlayerController.savePlayer(player);

    }

}
