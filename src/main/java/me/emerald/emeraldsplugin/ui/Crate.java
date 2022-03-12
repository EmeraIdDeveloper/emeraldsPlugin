package me.emerald.emeraldsplugin.ui;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import me.emerald.emeraldsplugin.PluginFuncs;
import me.emerald.emeraldsplugin.data.CrateInfo;
import me.emerald.emeraldsplugin.data.CrateItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

public class Crate implements InventoryHolder {
    private Inventory gui;
    private static ArrayList<ItemStack> cIA = new ArrayList<>();

    public Crate(Player p, String crateType, ItemStack itemUsed) throws InterruptedException {
        gui = Bukkit.createInventory(this, 9*5, ChatColor.GOLD + "Opening " + ChatColor.RESET + CrateInfo.getCrate(crateType).getDetailName());
        init(p, crateType, itemUsed);
    }

    private static void updateAsync(String crateType){
        cIA.set(6,cIA.get(5));
        cIA.set(5,cIA.get(4));
        cIA.set(4,cIA.get(3));
        cIA.set(3,cIA.get(2));
        cIA.set(2,cIA.get(1));
        cIA.set(1,cIA.get(0));
        cIA.set(0,getRandomItem(crateType));
    }

    private static ItemStack getRandomItem(String crateType){
        Double counter = 0.0;
        Random rand = new Random();

        CrateInfo crate = CrateInfo.getCrate(crateType);
        ItemStack item = null;

        for (int i = 0; i < crate.getItemList().size(); i++) {
            String itemString = (String) crate.getItemList().get(i);
            CrateItems s = CrateItems.getItem(itemString);
            counter += s.getWeight();
        }

        Double Chosen = rand.nextDouble(counter);

        for (int i = 0; i < crate.getItemList().size(); i++) {
            String itemString = (String) crate.getItemList().get(i);
            CrateItems s = CrateItems.getItem(itemString);

            if (counter > Chosen) {
                item = s.getActualItem();
            }

            counter -= s.getWeight();
        }

        return item;
    }

    private void init(Player p, String crateType, ItemStack itemUsed) throws InterruptedException {
        AtomicReference<Boolean> allowClose = new AtomicReference<>(false);

        for(int i = 0; i <= 7; i++){ //Insert random items
            cIA.add(getRandomItem(crateType));
        }

        PluginFuncs.consumeItem(p, 1, itemUsed);

        ItemStack deco = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta decoM = deco.getItemMeta();
        assert decoM != null;
        decoM.setDisplayName(" ");
        deco.setItemMeta(decoM);

        ItemStack dR = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
        ItemMeta decoRM = dR.getItemMeta();
        assert decoRM != null;
        decoRM.setDisplayName(" ");
        dR.setItemMeta(decoRM);

        ItemStack dD = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta decoDM = dD.getItemMeta();
        assert decoDM != null;
        decoDM.setDisplayName(" ");
        dD.setItemMeta(decoDM);

        ItemStack tor = new ItemStack(Material.REDSTONE_TORCH, 1);
        ItemMeta torM = tor.getItemMeta();
        assert torM != null;
        torM.setDisplayName(" ");
        tor.setItemMeta(torM);

        EmeraldsPlugin.jp.getServer().getScheduler().scheduleSyncRepeatingTask(EmeraldsPlugin.jp, () -> {

            if(!allowClose.get() && !p.getOpenInventory().getTopInventory().equals(gui)){
                p.openInventory(gui);
            }
        }, 0L, 0L);

        EmeraldsPlugin.jp.getServer().getScheduler().scheduleSyncDelayedTask(EmeraldsPlugin.jp, () -> {
            allowClose.set(true);
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
            p.closeInventory();
        }, 125L);

        for (int i = 0; i < 25; i++) {
            ItemStack newRan = getRandomItem(crateType);
            ItemStack[] menu_items = {
                    dR,dR,dR,dR,dR,dR,dR,dR,dR,
                    dR,deco,deco,deco,tor,deco,deco,deco,dR,
                    dR,cIA.get(0),cIA.get(1),cIA.get(2),cIA.get(3),cIA.get(4),cIA.get(5),cIA.get(6),dR,
                    dR,deco,deco,deco,tor,deco,deco,deco,dR,
                    dR,dR,dR,dR,dR,dR,dR,dR,dR,
            };
            gui.setContents(menu_items);
            p.openInventory(gui);
            p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
            sleep(100);
            updateAsync(crateType);
        }

        ItemStack selectedItem = cIA.get(3);
        ItemStack[] menu_items = {
                dD,dD,dD,dD,dD,dD,dD,dD,dD,
                dD,deco,deco,deco,tor,deco,deco,deco,dD,
                dD,cIA.get(0),cIA.get(1),cIA.get(2),cIA.get(3),cIA.get(4),cIA.get(5),cIA.get(6),dD,
                dD,deco,deco,deco,tor,deco,deco,deco,dD,
                dD,dD,dD,dD,dD,dD,dD,dD,dD,
        };
        gui.setContents(menu_items);
        p.openInventory(gui);
        p.getInventory().addItem(selectedItem);
        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }
}
