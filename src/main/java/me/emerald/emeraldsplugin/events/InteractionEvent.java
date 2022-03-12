package me.emerald.emeraldsplugin.events;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import me.emerald.emeraldsplugin.data.CrateInfo;
import me.emerald.emeraldsplugin.ui.Crate;
import me.emerald.emeraldsplugin.ui.CrateRarities;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InteractionEvent implements Listener {
    @EventHandler
    public void clickEvent(PlayerInteractEvent event) throws InterruptedException {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = event.getItem();
            Block block = event.getClickedBlock();
            Player p = event.getPlayer();

            if((item != null && block != null) && item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(EmeraldsPlugin.jp,"crateKey"), PersistentDataType.STRING) != null){
                PersistentDataContainer itemCont = item.getItemMeta().getPersistentDataContainer();
                if(block.getType() == Material.CHEST){
                    TileState state = (TileState) block.getState();
                    PersistentDataContainer blockCont = state.getPersistentDataContainer();
                    if(blockCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING) != null){
                        String itemKey = itemCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateKey"), PersistentDataType.STRING);
                        String blockKey = blockCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING);
                        if(blockKey.equals(blockKey)){
                            p.openInventory(new Crate(p, itemKey, item).getInventory());
                        }else{
                            p.sendMessage(ChatColor.DARK_RED + "This key isnt for this crate.");
                        }
                    }else{
                        p.sendMessage(ChatColor.DARK_RED + "This is a regular chest, not a crate.");
                    }
                }
                event.setCancelled(true);
            }else if((item != null && block != null) && item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(EmeraldsPlugin.jp,"crateCreator"), PersistentDataType.STRING) != null){
                if(block.getType() == Material.CHEST){TileState state = (TileState) block.getState();
                    PersistentDataContainer blockCont = state.getPersistentDataContainer();
                    if(blockCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING) == null){
                        String blockKey = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(EmeraldsPlugin.jp,"crateCreator"), PersistentDataType.STRING);
                        blockCont.set(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING, blockKey);
                        state.update();
                        p.sendMessage(ChatColor.GREEN + "Crate is now a " + CrateInfo.getCrate(blockKey).getDetailName() +"!");
                        Location loc = block.getLocation().add(.5, .5, .5);
                        ArmorStand stand = block.getWorld().spawn(loc, ArmorStand.class);
                        stand.setCustomName(CrateInfo.getCrate(blockKey).getDetailName());
                        stand.setCustomNameVisible(true);
                        stand.setVisible(false);
                        stand.setCollidable(false);
                        stand.setSmall(true);
                    }else{
                        p.sendMessage(ChatColor.DARK_RED + "Chest already is assigned a crate value.");
                    }
                }else{
                    p.sendMessage(ChatColor.DARK_RED + "Crates can only be made out of chests.");
                }
                event.setCancelled(true);
            }else if((item != null && block != null) && item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(EmeraldsPlugin.jp,"crateDeleter"), PersistentDataType.STRING) != null){
                PersistentDataContainer itemCont = item.getItemMeta().getPersistentDataContainer();
                if(block.getType() == Material.CHEST){
                    TileState state = (TileState) block.getState();
                    PersistentDataContainer blockCont = state.getPersistentDataContainer();
                    if(blockCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING) != null){
                        blockCont.remove(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"));
                        state.update();
                        Bukkit.dispatchCommand(p, "execute at @a positioned "+block.getX()+" "+block.getY()+" "+block.getZ()+" run kill @e[type=armor_stand,distance=..1]");
                        p.sendMessage(ChatColor.DARK_RED + "Chest is no longer a crate!");
                        event.setCancelled(true);
                    }
                }
            }else if(block != null){
                if(block.getType() == Material.CHEST){
                    TileState state = (TileState) block.getState();
                    PersistentDataContainer blockCont = state.getPersistentDataContainer();
                    if(blockCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING) != null){
                        String crate = blockCont.get(new NamespacedKey(EmeraldsPlugin.jp,"crateMain"), PersistentDataType.STRING);
                        p.openInventory(new CrateRarities(p, crate).getInventory());
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}