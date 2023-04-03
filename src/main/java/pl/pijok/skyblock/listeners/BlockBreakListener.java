package pl.pijok.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.pijok.skyblock.ChatUtils;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.GeneralConfig;
import pl.pijok.skyblock.Language;
import pl.pijok.skyblock.island.Island;
import pl.pijok.skyblock.island.IslandController;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayer;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayerController;

public class BlockBreakListener implements Listener {

    private final SkyBlockPlayerController skyBlockPlayerController;
    private final IslandController islandController;

    public BlockBreakListener() {
        this.skyBlockPlayerController = Controllers.getSkyBlockPlayerController();
        this.islandController = Controllers.getIslandController();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        SkyBlockPlayer skyBlockPlayer = skyBlockPlayerController.getPlayer(player);

        if(!player.getWorld().getName().equalsIgnoreCase(GeneralConfig.getGeneralConfig().getIslandsWorld())){
            return;
        }

        if(!skyBlockPlayer.hasIslandOrIsMember()){
            ChatUtils.sendMessage(player, Language.getText("cantDoThis"));
            event.setCancelled(true);
            return;
        }

        Island island = islandController.getIsland(skyBlockPlayer.getIslandID());

        if(!island.isOnIsland(player)){
            ChatUtils.sendMessage(player, Language.getText("cantDoThis"));
            event.setCancelled(true);
            return;
        }

    }

}
