package pl.pijok.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IslandCommandTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        switch (args.length) {
            case 0: {
                ArrayList<String> list = new ArrayList<>(List.of(
                        "stworz",
                        "tp",
                        "dom",
                        "usun",
                        "chat",
                        "czat"
                ));
                if (sender.hasPermission("skyblock.admin.reload")) {
                    list.add("reload");
                }
                return list;
            }
            case 1: {
                if (args[0].equalsIgnoreCase("zapros")) {
                    return Bukkit.getOnlinePlayers()
                            .stream()
                            .map(Player::getName)
                            .collect(Collectors.toList());
                }
            }
            default: {
                return null;
            }
        }
    }
}
