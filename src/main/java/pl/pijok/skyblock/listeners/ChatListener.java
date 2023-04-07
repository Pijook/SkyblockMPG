package pl.pijok.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.pijok.skyblock.ChatUtils;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.Language;
import pl.pijok.skyblock.island.IslandController;

public class ChatListener implements Listener {

    private final IslandController islandController;

    public ChatListener() {
        this.islandController = Controllers.getIslandController();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // Check if player is on an island
        if (!islandController.isPlayerOnIsland(event.getPlayer())) return;

        // Get island by player location
        var island = islandController.getIslandByPlayerLocation(event.getPlayer());

        // Check if island has island chat option enabled
        if (!island.hasPlayerIslandChatEnabled(event.getPlayer().getUniqueId())) return;

        // Cancel default chat event
        event.setCancelled(true);
        String message = Language.getText("prefix") + " &e" + event.getPlayer().getDisplayName() + "&7: &r" + event.getMessage();
        island.getIslandChatMembers().forEach(
                player -> player.sendMessage(ChatUtils.format(message))
        );
    }
}
