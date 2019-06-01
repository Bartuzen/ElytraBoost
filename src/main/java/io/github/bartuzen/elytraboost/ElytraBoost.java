package io.github.bartuzen.elytraboost;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class ElytraBoost extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack chestplate = inventory.getChestplate();
        if (!player.isSneaking() &&
                chestplate != null && chestplate.getType().equals(Material.ELYTRA) &&
                player.isOnGround() &&
                !player.getWorld().getBlockAt(player.getLocation()).getType().equals(Material.WATER)
        ) {
            BossBar bossbar = Bukkit.createBossBar("§eCharging §f0§e%", BarColor.RED, BarStyle.SEGMENTED_20);
            bossbar.setProgress(0);
            bossbar.addPlayer(player);
            final Int fly = new Int(0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isSneaking()) {
                        ItemStack chestplate = inventory.getChestplate();
                        if (chestplate == null || !chestplate.getType().equals(Material.ELYTRA) || !player.isOnGround()) {
                            bossbar.removeAll();
                            this.cancel();
                        }
                        if (!fly.equals(10)) {
                            fly.setFly(fly.getFly() + 1);
                            bossbar.setTitle("§eCharging §f" + fly.getFly() * 10 + "§e%");
                            bossbar.setProgress(fly.getFly() / 10.0);
                        }
                    } else {
                        bossbar.removeAll();
                        Vector vector = new org.bukkit.util.Vector(0, player.getLocation().getY() + 1, 0);
                        player.setVelocity(vector.multiply(fly.getFly() / 500.0));
                        this.cancel();
                    }
                }
            }.runTaskTimer(this, 0, 2);
        }

    }
}

