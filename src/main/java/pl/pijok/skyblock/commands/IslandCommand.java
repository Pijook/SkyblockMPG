package pl.pijok.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pijok.skyblock.ChatUtils;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.Language;
import pl.pijok.skyblock.SkyBlock;
import pl.pijok.skyblock.island.Island;
import pl.pijok.skyblock.island.IslandController;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayer;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayerController;

public class IslandCommand implements CommandExecutor {

    private final SkyBlock plugin;
    private final IslandController islandController;
    private final SkyBlockPlayerController skyBlockPlayerController;

    public IslandCommand(SkyBlock plugin){
        this.plugin = plugin;
        this.islandController = Controllers.getIslandController();
        this.skyBlockPlayerController = Controllers.getSkyBlockPlayerController();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1){
            //Create
            if(args[0].equalsIgnoreCase("stworz")){
                if(!(sender instanceof Player)){
                    ChatUtils.sendMessage(sender, "&cCommand only for players!");
                    return true;
                }

                Player player = (Player) sender;
                if(!islandController.canCreateNewIsland(player)){
                    ChatUtils.sendMessage(player, Language.getText("alreadyHasIsland"));
                    return true;
                }

                islandController.createNewIsland(player);
                ChatUtils.sendMessage(sender, Language.getText("islandCreated"));
                return true;
            }
            //Tp
            else if(args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("dom")){
                if(!(sender instanceof Player)){
                    ChatUtils.sendMessage(sender, "&cCommand only for players!");
                    return true;
                }

                Player player = (Player) sender;
                if(!skyBlockPlayerController.getPlayer(player).hasIslandOrIsMember()){
                    ChatUtils.sendMessage(sender, Language.getText("noIsland"));
                    return true;
                }

                Island island = islandController.getIsland(player);
                player.teleport(island.getCenter());
                ChatUtils.sendMessage(sender, Language.getText("islandTeleport"));
                return true;
            }
            //Delete
            else if(args[0].equalsIgnoreCase("usun")){
                if(!(sender instanceof Player)){
                    ChatUtils.sendMessage(sender, "&cCommand only for players!");
                    return true;
                }

                Player player = (Player) sender;
                if(!skyBlockPlayerController.getPlayer(player).hasIslandOrIsMember()){
                    ChatUtils.sendMessage(sender, Language.getText("noIsland"));
                    return true;
                }

                Island island = islandController.getIsland(player);

                if(!island.getOwnerID().equals(player.getUniqueId())){
                    ChatUtils.sendMessage(player, Language.getText("notOwner"));
                    return true;
                }

                islandController.removeIsland(island.getIslandID());
                ChatUtils.sendMessage(player, Language.getText("islandDeleted"));
                return true;
            }
            //Reload
            else if(args[0].equalsIgnoreCase("reload")){
                if(!sender.hasPermission("skyblock.admin.reload")){
                    ChatUtils.sendMessage(sender, Language.getText("noPermission"));
                    return true;
                }

                ChatUtils.sendMessage(sender, Language.getText("pluginReload"));
                plugin.loadPlugin(true);
                ChatUtils.sendMessage(sender, Language.getText("pluginReloaded"));
                return true;
            }
            // Island Chat
            else if (args[0].equalsIgnoreCase("chat") || args[0].equalsIgnoreCase("czat")) {
                if (!(sender instanceof Player player)) {
                    ChatUtils.sendMessage(sender, "&cCommand only for players!");
                    return true;
                }

                if (!islandController.isPlayerOnIsland(player)) {
                    ChatUtils.sendMessage(sender, Language.getText("notOnIsland"));
                    return true;
                }

                Island island = islandController.getIslandByPlayerLocation(player);
                if (island.setChat(player.getUniqueId())) {
                    ChatUtils.sendMessage(sender, Language.getText("islandChatEnabled"));
                } else {
                    ChatUtils.sendMessage(sender, Language.getText("islandChatDisabled"));
                }
            }
        }
        else if(args.length == 2){
            if(args[0].equalsIgnoreCase("zapros")){
                Player player = (Player) sender;

                String nickname = args[1];
                Player target = Bukkit.getPlayer(nickname);

                SkyBlockPlayer skyBlockPlayer = skyBlockPlayerController.getPlayer((Player) sender);

                if(!skyBlockPlayer.hasIslandOrIsMember()){
                    ChatUtils.sendMessage(sender, Language.getText("noIsland"));
                    return true;
                }

                Island island = islandController.getIsland(skyBlockPlayer.getIslandID());

                if(!island.getOwnerID().equals(((Player) sender).getUniqueId())){
                    ChatUtils.sendMessage(sender, Language.getText("notOwner"));
                    return true;
                }

                if(target == null || !target.isOnline()){
                    ChatUtils.sendMessage(sender, Language.getText("playerOffline"));
                    return true;
                }

                SkyBlockPlayer targetSkyBlockPlayer = skyBlockPlayerController.getPlayer(target.getUniqueId());

                if(targetSkyBlockPlayer.hasIslandOrIsMember()){
                    ChatUtils.sendMessage(sender, Language.getText("playerAlreadyHasIsland"));
                    return true;
                }

                islandController.createNewInvite(island, target);
                ChatUtils.sendMessage(target, Language.getText("islandInvite").replace("{PLAYER}", player.getName()));
                ChatUtils.sendMessage(player, Language.getText("playerReceivedInvite").replace("{PLAYER}", nickname));
                return true;
            }
        }

        ChatUtils.sendMessage(sender, "&7/" + label + " stworz");
        ChatUtils.sendMessage(sender, "&7/" + label + " dom");
        ChatUtils.sendMessage(sender, "&7/" + label + " tp");
        ChatUtils.sendMessage(sender, "&7/" + label + " usun");
        ChatUtils.sendMessage(sender, "&7/" + label + " zapros");
        if(sender.hasPermission("skyblock.admin.reload")){
            ChatUtils.sendMessage(sender, "&7/" + label + " reload");
        }
        return true;
    }

}
