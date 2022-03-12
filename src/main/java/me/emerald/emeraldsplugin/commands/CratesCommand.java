package me.emerald.emeraldsplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CratesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player p){
            p.sendMessage(ChatColor.GREEN + "You were teleported to crates!");
            p.teleport(new Location(p.getWorld(), -310, 229, 741));
        }

        return true;
    }
}