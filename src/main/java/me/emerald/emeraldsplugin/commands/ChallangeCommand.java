package me.emerald.emeraldsplugin.commands;

import me.emerald.emeraldsplugin.ui.Challenges;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChallangeCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player p){
            if(p.getName().equalsIgnoreCase("AnEmerald")){
                p.openInventory(new Challenges().getInventory());
            }else{
                p.sendMessage(ChatColor.DARK_RED + "Invalid permissions to use this. You must be an administrator to use this.");
            }
        }

        return true;
    }
}