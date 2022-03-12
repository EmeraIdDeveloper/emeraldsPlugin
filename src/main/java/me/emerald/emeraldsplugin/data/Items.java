package me.emerald.emeraldsplugin.data;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class Items {
    static HashMap<String, Items> itemList = new HashMap<String, Items>();
    ItemStack item;

    public Items(String locName, ItemStack item){
        this.item = item;
        itemList.put(locName, this);
    }

    public ItemStack returnItem() {
        return this.item;
    }

    public boolean setItem(ItemStack item) {
        this.item = item;
        return true;
    }

    public static HashMap<String, Items> getAllItems(){
        return itemList;
    }

    public static Items getItem(String locName){
        return itemList.get(locName);
    }

    public static Items newItem(String locName, ItemStack item){
        return new Items(locName,item);
    }
}