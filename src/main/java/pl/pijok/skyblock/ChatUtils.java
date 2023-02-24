package pl.pijok.skyblock;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtils {

    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(format(Language.getText("prefix") + message));
    }

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
