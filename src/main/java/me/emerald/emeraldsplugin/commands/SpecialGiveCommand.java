package me.emerald.emeraldsplugin.commands;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import me.emerald.emeraldsplugin.data.Items;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpecialGiveCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player p){
            if(p.getName().equalsIgnoreCase("AnEmerald")){
                if(args.length > 0){
                     String user = args[0];
                     String item = args[1];
                     if(EmeraldsPlugin.jp.getServer().getPlayer(user) != null && Items.getItem(item) != null){
                         ItemStack foundItem = Items.getItem(item).returnItem();
                         EmeraldsPlugin.jp.getServer().getPlayer(user).getInventory().addItem(foundItem);
                    }
                }
            }else{
                p.sendMessage(ChatColor.DARK_RED + "Invalid permissions to use this. You must be an administrator to use this.");
            }
        }

        return true;
    }
}