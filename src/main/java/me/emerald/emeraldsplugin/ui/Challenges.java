package me.emerald.emeraldsplugin.ui;

import me.emerald.emeraldsplugin.EmeraldsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

@SuppressWarnings({"deprecation"})

public class Challenges implements InventoryHolder {
    private Inventory gui;

    public Challenges() {
        gui = Bukkit.createInventory(this, 9*5, ChatColor.DARK_RED + "Challenges");
        init();
    }

    private void init(){
        ItemStack potion = new ItemStack(Material.POTION, 1, (short) 21);
        ItemMeta potionMeta = potion.getItemMeta();
        assert potionMeta != null;
        potionMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        ArrayList<String> potionLore = new ArrayList<>();
        potionLore.add(ChatColor.GOLD + "Take damage for breaking blocks.");
        potionLore.add(ChatColor.GOLD + "Ores and Obsidian deal extra damage.");
        potionMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        ItemStack hunger = new ItemStack(Material.ROTTEN_FLESH, 1);
        ItemMeta hungerMeta = hunger.getItemMeta();
        assert hungerMeta != null;
        hungerMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        ArrayList<String> hungerLore = new ArrayList<>();
        hungerLore.add(ChatColor.GOLD + "Everyone has hunger 2 permanently.");
        hungerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta paperMeta = paper.getItemMeta();
        assert paperMeta != null;
        paperMeta.setDisplayName(ChatColor.GREEN + "Active Events: ");
        paperMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        ArrayList<String> paperLore = new ArrayList<>();
        paperMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if(EmeraldsPlugin.jp.getConfig().getBoolean("Challenges.BlockDamage")){
            potionMeta.setDisplayName(ChatColor.GREEN + "Damage Blocks");
            paperLore.add(ChatColor.WHITE + "Damage Blocks: " + ChatColor.RESET + ChatColor.DARK_GREEN + ChatColor.BOLD + "ON");
        }else{
            potionMeta.setDisplayName(ChatColor.RED + "Damage Blocks");
            paperLore.add(ChatColor.WHITE + "Damage Blocks: " + ChatColor.RESET + ChatColor.DARK_RED + ChatColor.BOLD + "OFF");
        }

        if(EmeraldsPlugin.jp.getConfig().getBoolean("Challenges.Hunger")){
            hungerMeta.setDisplayName(ChatColor.GREEN + "Hunger");
            paperLore.add(ChatColor.WHITE + "Hunger: " + ChatColor.RESET + ChatColor.DARK_GREEN + ChatColor.BOLD + "ON");
        }else{
            hungerMeta.setDisplayName(ChatColor.RED + "Hunger");
            paperLore.add(ChatColor.WHITE + "Hunger: " + ChatColor.RESET + ChatColor.DARK_RED + ChatColor.BOLD + "OFF");
        }

        potionMeta.setLore(potionLore);
        potion.setItemMeta(potionMeta);
        hungerMeta.setLore(hungerLore);
        hunger.setItemMeta(hungerMeta);
        paperMeta.setLore(paperLore);
        paper.setItemMeta(paperMeta);

        ItemStack deco = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta decoM = deco.getItemMeta();
        assert decoM != null;
        decoM.setDisplayName(" ");
        deco.setItemMeta(decoM);

        ItemStack decoRed = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta decoRedM = decoRed.getItemMeta();
        assert decoRedM != null;
        decoRedM.setDisplayName(" ");
        decoRed.setItemMeta(decoRedM);

        ItemStack decoBlack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta decoBlackM = decoBlack.getItemMeta();
        assert decoBlackM != null;
        decoBlackM.setDisplayName(" ");
        decoBlack.setItemMeta(decoBlackM);

        ItemStack[] menu_items = {
                decoBlack,decoRed,decoBlack,decoRed,decoBlack,decoRed,decoBlack,decoRed,decoBlack,
                decoRed,potion,deco,deco,deco,deco,deco,hunger,decoRed,
                decoBlack,deco,deco,deco,deco,deco,deco,deco,decoBlack,
                decoRed,deco,deco,deco,paper,deco,deco,deco,decoRed,
                decoBlack,decoRed,decoBlack,decoRed,decoBlack,decoRed,decoBlack,decoRed,decoBlack,
        };
        gui.setContents(menu_items);
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }
}
