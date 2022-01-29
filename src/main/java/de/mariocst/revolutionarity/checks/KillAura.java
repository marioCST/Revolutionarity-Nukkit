package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.level.Location;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.CheckUtils;

public class KillAura implements Listener {
    private final Revolutionarity plugin;

    public KillAura(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!this.plugin.getSettings().isKillAura()) return;

        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();

        if (player.hasPermission("revolutionarity.bypass.killaura") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        if (player.getLoginChainData().getDeviceOS() == 1 || player.getLoginChainData().getDeviceOS() == 2) return; // Filter mobile players (Android, iOS), ToolBox KillAura is shit and will probably get flagged by a NoSwing check

        final Location playerLocation = player.getLocation();
        final Location entityLocation = event.getEntity().getLocation();

        final float yawToEntity = CheckUtils.getYawToEntity(playerLocation, (float) player.yaw, entityLocation);

        if (yawToEntity >= this.plugin.getSettings().getMaxYaw()) {
            event.setCancelled(true);
            this.plugin.flag("KillAura", "Yaw: " + yawToEntity + "/" + this.plugin.getSettings().getMaxYaw(), player);
        }
    }
}
