package me.emerald.emeraldsplugin.events;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockEvent implements Listener{
    @EventHandler
    public void breakEvent(BlockBreakEvent event){
        Block block = event.getBlock();
        String blockName = block.getType().name();
        Player player = event.getPlayer();

        if(EmeraldsPlugin.jp.getConfig().getBoolean("Challenges.BlockDamage")){
            if(EmeraldsPlugin.jp.getConfig().get("BlockBreakList." + blockName) != null){
                player.damage(EmeraldsPlugin.jp.getConfig().getInt("BlockBreakList." + blockName));
            }else{
                player.damage(1.0);
            }
        }
    }
}
