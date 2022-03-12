package me.emerald.emeraldsplugin.ui;

import me.emerald.emeraldsplugin.data.CrateInfo;
import me.emerald.emeraldsplugin.data.CrateItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CrateRarities implements InventoryHolder {
    private Inventory gui;

    public CrateRarities(Player p, String crateType) {
        gui = Bukkit.createInventory(this, 9*4, CrateInfo.getCrate(crateType).getDetailName() + ChatColor.RESET + ChatColor.GOLD + " Items");
        init(p, crateType);
    }

    private static ItemStack get(int pos, String crateType) {
        ItemStack invalid = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta invalidM = invalid.getItemMeta();
        assert invalidM != null;
        invalidM.setDisplayName(" ");
        invalid.setItemMeta(invalidM);

        Double chance = 0.0;
        CrateInfo crate = CrateInfo.getCrate(crateType);
        ItemStack item = invalid;

        for (int i = 0; i < crate.getItemList().size(); i++) {
            String itemString = (String) crate.getItemList().get(i);
            CrateItems s = CrateItems.getItem(itemString);
            chance += s.getWeight();
        }

        if (crate.getItemList().size() > pos) {
            if(crate.getItemList().get(pos) != null){
                String itemString = (String) crate.getItemList().get(pos);
                CrateItems s = CrateItems.getItem(itemString);
                item = s.getActualItem().clone();
                ItemMeta itemMeta = item.getItemMeta();
                ArrayList<String> itemLore = new ArrayList<>();
                Double percent = ((s.getWeight() / chance) * 100);

                String form = new DecimalFormat("###.##").format(percent);
                itemLore.add("Chance: " + form + "%");
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
            }
        }

        return item;
    }

    public void init(Player p, String crateType) {
        AtomicReference<Boolean> allowClose = new AtomicReference<>(false);

        ItemStack deco = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta decoM = deco.getItemMeta();
        assert decoM != null;
        decoM.setDisplayName(" ");
        deco.setItemMeta(decoM);

        ItemStack[] menu_items = {
                deco,deco,deco,deco,deco,deco,deco,deco,deco,
                deco,get(0,crateType),get(1,crateType),get(2,crateType),get(3,crateType),get(4,crateType),get(5,crateType),get(6,crateType),deco,
                deco,get(7,crateType),get(8,crateType),get(9,crateType),get(10,crateType),get(11,crateType),get(12,crateType),get(13,crateType),deco,
                deco,deco,deco,deco,deco,deco,deco,deco,deco,
        };
        gui.setContents(menu_items);
        p.openInventory(gui);
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }
}