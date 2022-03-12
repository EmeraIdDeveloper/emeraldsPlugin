package me.emerald.emeraldsplugin.data;

import java.util.ArrayList;
import java.util.HashMap;

public class CrateInfo {
    String lowName;
    String detailName;

    ArrayList<String> itemList;

    static HashMap<String, CrateInfo> crateList = new HashMap<String, CrateInfo>();

    public CrateInfo(String lowName, String detailName){
        this.lowName = lowName;
        this.detailName = detailName;
        this.itemList = new ArrayList<>();
        crateList.put(lowName, this);
    }

    public CrateInfo returnCrate(){
        return this;
    }

    public static CrateInfo getCrate(String lowName){
        return crateList.get(lowName);
    }

    public static boolean removeCrate(String lowName){
        crateList.remove(lowName);
        return true;
    }

    public String getDetailName(){
        return this.detailName;
    }

    public ArrayList getItemList(){
        return this.itemList;
    }

    public Boolean addItemToList(String item){
        this.itemList.add(item);
        return true;
    }

    public static CrateInfo newCrate(String lowName, String detailName){
        return new CrateInfo(lowName,detailName);
    }
}