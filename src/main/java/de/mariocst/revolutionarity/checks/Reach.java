package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntityEnderDragon;
import cn.nukkit.entity.mob.EntityEnderman;
import cn.nukkit.entity.mob.EntityWither;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.level.Location;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.PlayerUtils;

public class Reach implements Listener {
    private final Revolutionarity plugin;

    public Reach(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!this.plugin.getSettings().isReach()) return;

        if (!(event.getDamager() instanceof Player player)) return;

        Entity damaged = event.getEntity();

        if (damaged instanceof EntityEnderman || damaged instanceof EntityEnderDragon || damaged instanceof EntityWither) return;

        if (player.getGamemode() != 0) return;

        if (PlayerUtils.bypassesCheck(player, "reach")) return;

        Location eyeHeight = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);

        double maxReach = this.plugin.getSettings().getMaxReach();

        if (damaged.getBoundingBox().getMaxY() > damaged.getBoundingBox().getMinY() + 1.1) {
            if (player.getY() < damaged.getY() - 0.6) {
                Location hitAt = damaged.getLocation().add(0.0, 0.5, 0.0);

                if (eyeHeight.distance(hitAt) >= maxReach) {
                    this.plugin.flag("Reach", "Reach: " + eyeHeight.distance(hitAt), player);
                    event.setCancelled(true);
                }
            }
            else {
                Location hitAt = damaged.getLocation().add(0.0, damaged.getEyeHeight(), 0.0);

                if (eyeHeight.distance(hitAt) >= maxReach) {
                    this.plugin.flag("Reach", "Reach: " + eyeHeight.distance(hitAt), player);
                    event.setCancelled(true);
                }
            }
        }
        else {
            Location hitAt = damaged.getLocation().add(0.0, 0.5, 0.0);

            if (eyeHeight.distance(hitAt) >= maxReach) {
                this.plugin.flag("Reach", "Reach: " + eyeHeight.distance(hitAt), player);
                event.setCancelled(true);
            }
        }
    }
}
