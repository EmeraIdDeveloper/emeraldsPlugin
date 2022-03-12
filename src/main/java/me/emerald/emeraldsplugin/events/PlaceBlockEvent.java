package me.emerald.emeraldsplugin.events;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlaceBlockEvent implements Listener {
    @EventHandler
    public void BlockPlace(BlockPlaceEvent event){
        ItemStack item = event.getItemInHand();

        PersistentDataContainer cont = item.getItemMeta().getPersistentDataContainer();

        if(cont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateKey"), PersistentDataType.STRING) != null){
            event.setCancelled(true);
        }
    }
}
