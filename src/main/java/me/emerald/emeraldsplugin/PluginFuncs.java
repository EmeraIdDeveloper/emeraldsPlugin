package me.emerald.emeraldsplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PluginFuncs{
    public static void sendGlobalMessage(String msg){
        Bukkit.getOnlinePlayers().forEach(sender -> sender.sendMessage(msg));
    }

    public static void consumeItem(Player player, int count, ItemStack item) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(item);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }

        player.updateInventory();
    }
}