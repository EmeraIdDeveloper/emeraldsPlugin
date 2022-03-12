package me.emerald.emeraldsplugin.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnPlayerChatEvent implements Listener {
    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
        if(p.getName().equals("AnEmerald")){
            event.setFormat(ChatColor.GREEN + "[Owner] " + ChatColor.RESET + event.getFormat());
        }else if(p.getName().equals("floppyorsomebody")){
            event.setFormat(ChatColor.DARK_GREEN + "[Funder] " + ChatColor.RESET + event.getFormat());
        }
    }
}
