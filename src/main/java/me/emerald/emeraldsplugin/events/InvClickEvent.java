package me.emerald.emeraldsplugin.events;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import me.emerald.emeraldsplugin.PluginFuncs;
import me.emerald.emeraldsplugin.ui.Challenges;
import me.emerald.emeraldsplugin.ui.Crate;
import me.emerald.emeraldsplugin.ui.CrateRarities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class InvClickEvent implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if(inv != null){
            InventoryView ui = p.getOpenInventory();
            InventoryHolder holder = event.getClickedInventory().getHolder();

            if(holder instanceof Challenges) {
                Material itemType = event.getCurrentItem().getType();

                if(itemType == Material.POTION) {
                    if (EmeraldsPlugin.jp.getConfig().getBoolean("Challenges.BlockDamage")) {
                        EmeraldsPlugin.jp.getConfig().set("Challenges.BlockDamage", false);
                        PluginFuncs.sendGlobalMessage(ChatColor.GREEN + "The challenge, Block Damage, has ended.");
                    } else {
                        EmeraldsPlugin.jp.getConfig().set("Challenges.BlockDamage", true);
                        PluginFuncs.sendGlobalMessage(ChatColor.RED + "The challenge, Block Damage, has started!");
                    }
                    p.openInventory(new Challenges().getInventory());
                }else if(itemType == Material.ROTTEN_FLESH) {
                    if (EmeraldsPlugin.jp.getConfig().getBoolean("Challenges.Hunger")) {
                        EmeraldsPlugin.jp.getConfig().set("Challenges.Hunger", false);
                        PluginFuncs.sendGlobalMessage(ChatColor.GREEN + "The challenge, Hunger, has ended.");
                    } else {
                        EmeraldsPlugin.jp.getConfig().set("Challenges.Hunger", true);
                        PluginFuncs.sendGlobalMessage(ChatColor.RED + "The challenge, Hunger, has started!");
                    }
                    p.openInventory(new Challenges().getInventory());
                }
                event.setCancelled(true);
            }else if (holder instanceof Crate || holder instanceof CrateRarities){
                event.setCancelled(true);
            }
        }
    }
}