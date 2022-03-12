package me.emerald.emeraldsplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player p){
            p.sendMessage(ChatColor.GREEN + "You were teleported to spawn!");
            p.teleport(new Location(p.getWorld(), -311, 71, 678));
        }

        return true;
    }
}
