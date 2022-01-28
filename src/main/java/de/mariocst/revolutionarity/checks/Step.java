package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import de.mariocst.revolutionarity.Revolutionarity;

public class Step implements Listener {
    private final Revolutionarity plugin;

    public Step(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.plugin.getSettings().isStep()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.step") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        double diff = player.getY() - Speed.lastPos.get(player).getY();

        if (diff >= 1.0) {
            event.setCancelled(true);
            this.plugin.flag("Step", "Height: " + diff, player);
        }
    }
}
