package me.emerald.emeraldsplugin.data;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class CrateItems {
    String findName;
    ItemStack item;
    Double weight;

    static HashMap<String, CrateItems> crateItemList = new HashMap<>();

    public CrateItems(String findName, ItemStack item, Double weight){
        this.findName = findName;
        this.item = item;
        this.weight = weight;
        crateItemList.put(findName, this);
    }

    public static CrateItems getItem(String findName){
        return crateItemList.get(findName);
    }

    public ItemStack getActualItem(){
        return this.item;
    }

    public Double getWeight(){
        return this.weight;
    }

    public static CrateItems newCrateItem(String findName, ItemStack item, Double weight){
        return new CrateItems(findName,item,weight);
    }
}