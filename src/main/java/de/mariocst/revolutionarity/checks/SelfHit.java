package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.PlayerUtils;

public class SelfHit implements Listener {
    private final Revolutionarity plugin;

    public SelfHit(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!this.plugin.getSettings().isSelfHit()) return;

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player player)) return;

        if (PlayerUtils.bypassesCheck(player, "selfhit")) return;

        if (player == event.getEntity()) {
            event.setCancelled(true);
            this.plugin.flag("SelfHit", player);
        }
    }
}
