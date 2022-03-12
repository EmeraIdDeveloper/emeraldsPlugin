package me.emerald.emeraldsplugin;

import me.emerald.emeraldsplugin.commands.ChallangeCommand;
import me.emerald.emeraldsplugin.commands.CratesCommand;
import me.emerald.emeraldsplugin.commands.SpecialGiveCommand;
import me.emerald.emeraldsplugin.commands.ToSpawnCommand;
import me.emerald.emeraldsplugin.data.CrateInfo;
import me.emerald.emeraldsplugin.data.CrateItems;
import me.emerald.emeraldsplugin.data.Items;
import me.emerald.emeraldsplugin.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public final class EmeraldsPlugin extends JavaPlugin implements Listener{
    public static JavaPlugin jp;

    public void setupCrates() {
        CrateInfo basicCrate = CrateInfo.newCrate("basicCrate", ChatColor.GREEN + "Basic Crate");

        CrateItems porkBasic = CrateItems.newCrateItem("porkBasic", new ItemStack(Material.COOKED_PORKCHOP, 16), 50.0);
        basicCrate.addItemToList("porkBasic");

        CrateItems pieBasic = CrateItems.newCrateItem("pieBasic", new ItemStack(Material.PUMPKIN_PIE, 8), 30.0);
        basicCrate.addItemToList("pieBasic");

        CrateItems ironBasic = CrateItems.newCrateItem("ironBasic", new ItemStack(Material.IRON_INGOT, 10), 10.0);
        basicCrate.addItemToList("ironBasic");

        CrateItems goldBasic = CrateItems.newCrateItem("goldBasic", new ItemStack(Material.GOLD_INGOT, 2), 2.0);
        basicCrate.addItemToList("goldBasic");

        ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        CrateItems stoneSwordBasic = CrateItems.newCrateItem("stoneSwordBasic", sword, 0.1);
        basicCrate.addItemToList("stoneSwordBasic");

        ItemStack keyBasicItem = Items.getItem("basicKey").returnItem().clone();
        keyBasicItem.setAmount(5);
        CrateItems keyBasic = CrateItems.newCrateItem("keyBasic", keyBasicItem, .03);
        basicCrate.addItemToList("keyBasic");
    }

    public void setupItems(){
        //BasicKey
        Items BasicKey = Items.newItem("basicKey",null);

        ItemStack itemA = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta metaA = itemA.getItemMeta();
        metaA.setDisplayName(ChatColor.GREEN + "Basic Key");
        metaA.addEnchant(Enchantment.DURABILITY, 1, true);
        NamespacedKey keyA = new NamespacedKey(EmeraldsPlugin.jp,"crateKey");
        metaA.getPersistentDataContainer().set(keyA, PersistentDataType.STRING, "basicCrate");
        ArrayList<String> loreA = new ArrayList<>();
        loreA.add(ChatColor.GOLD + "A key! can open a basic crate.");
        metaA.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaA.setLore(loreA);
        itemA.setItemMeta(metaA);

        BasicKey.setItem(itemA);

        //BasicCrateMaker
        Items BasicCrateMaker = Items.newItem("basicCrateMaker",null);

        ItemStack itemB = new ItemStack(Material.MAGMA_CREAM, 1);
        ItemMeta metaB = itemB.getItemMeta();
        metaB.setDisplayName(ChatColor.GREEN + "Basic Crate Maker");
        metaB.addEnchant(Enchantment.DURABILITY, 1, true);
        NamespacedKey keyB = new NamespacedKey(EmeraldsPlugin.jp,"crateCreator");
        metaB.getPersistentDataContainer().set(keyB, PersistentDataType.STRING, "basicCrate");
        ArrayList<String> loreB = new ArrayList<>();
        loreB.add(ChatColor.GOLD + "Turns a chest into a basic crate.");
        metaB.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaB.setLore(loreB);
        itemB.setItemMeta(metaB);

        BasicCrateMaker.setItem(itemB);

        //crateRemover
        Items crateRemover = Items.newItem("crateRemover",null);

        ItemStack itemC = new ItemStack(Material.MAGMA_CREAM, 1);
        ItemMeta metaC = itemC.getItemMeta();
        metaC.setDisplayName(ChatColor.BLUE + "Crate Remover");
        metaC.addEnchant(Enchantment.DURABILITY, 1, true);
        NamespacedKey keyC = new NamespacedKey(EmeraldsPlugin.jp,"crateDeleter");
        metaC.getPersistentDataContainer().set(keyC, PersistentDataType.STRING, "del");
        ArrayList<String> loreC = new ArrayList<>();
        loreC.add(ChatColor.GOLD + "Deletes a crate.");
        metaC.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaC.setLore(loreC);
        itemC.setItemMeta(metaC);

        crateRemover.setItem(itemC);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        jp = this;

        setupItems();
        setupCrates();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if(EmeraldsPlugin.jp.getConfig().getBoolean("Challenges.Hunger")){
                PotionEffect eff = new PotionEffect(PotionEffectType.HUNGER,200,1);
                Bukkit.getOnlinePlayers().forEach(sender -> sender.addPotionEffect(eff));
            }
        }, 0L, 0L);

        getCommand("specialgive").setExecutor(new SpecialGiveCommand());
        getCommand("challenge").setExecutor(new ChallangeCommand());
        getCommand("tospawn").setExecutor(new ToSpawnCommand());
        getCommand("crates").setExecutor(new CratesCommand());

        getServer().getPluginManager().registerEvents(new InteractionEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new InvClickEvent(), this);
        getServer().getPluginManager().registerEvents(new BreakBlockEvent(), this);
        getServer().getPluginManager().registerEvents(this, this);

        System.out.println("Starting plugin...");

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}