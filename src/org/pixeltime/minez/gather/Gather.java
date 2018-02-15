package org.pixeltime.minez.gather;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Gather extends JavaPlugin implements Listener {
    public final Material[] hoe = { Material.DIAMOND_HOE, Material.IRON_HOE,
        Material.GOLD_HOE, Material.STONE_HOE, Material.WOOD_HOE };
    public final Material[] gatherable = { Material.CROPS, Material.MELON_BLOCK,
        Material.BROWN_MUSHROOM, Material.RED_MUSHROOM };


    public void onEnable() {
        Bukkit.getLogger().info("MineZ Addon Gather by HealPot enabled.");
        getServer().getPluginManager().registerEvents(this, this);
    }


    public void onDisable() {
        Bukkit.getLogger().info("Minez Addon Gather by HealPot disabled.");
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer()
            .getItemInHand().getType() != Material.AIR) {
            if (isValid(event.getClickedBlock().getType(), gatherable)
                && isValid(event.getMaterial(), hoe)) {
                if (event.getClickedBlock().getType().equals(Material.CROPS)) {
                    if (event.getClickedBlock().getData() == 7) {
                        Random random = new Random();
                        ItemStack drop = new ItemStack(Material.WHEAT, (random
                            .nextInt(2) + 1));
                        event.getClickedBlock().getWorld().dropItemNaturally(
                            event.getClickedBlock().getLocation(), drop);
                        event.getClickedBlock().setType(Material.AIR);
                    }
                    else {
                        return;
                    }
                }
                else if (event.getClickedBlock().getType().equals(
                    Material.MELON_BLOCK)) {
                    Random random = new Random();
                    ItemStack drop = new ItemStack(Material.MELON, (random
                        .nextInt(2) + 1));
                    event.getClickedBlock().getWorld().dropItemNaturally(event
                        .getClickedBlock().getLocation(), drop);
                    event.getClickedBlock().setType(Material.AIR);
                }
                else {
                    event.getClickedBlock().breakNaturally();
                }
                event.getPlayer().getItemInHand().setDurability((short)(event
                    .getPlayer().getItemInHand().getDurability() + (short)(event
                        .getPlayer().getItemInHand().getType()
                        .getMaxDurability() * 0.2)));
                if (event.getPlayer().getItemInHand().getDurability() >= event
                    .getPlayer().getItemInHand().getType().getMaxDurability()) {
                    event.getPlayer().getInventory().remove(event.getPlayer()
                        .getItemInHand());
                }
            }
        }
    }


    public boolean isValid(Material material, Material[] comparable) {
        for (int i = 0; i < comparable.length; i++) {
            if (comparable[i].equals(material)) {
                return true;
            }
        }
        return false;
    }
}
