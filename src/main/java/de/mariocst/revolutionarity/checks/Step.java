package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.PlayerUtils;

public class Step implements Listener {
    private final Revolutionarity plugin;

    public Step(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.plugin.getSettings().isStep()) return;

        Player player = event.getPlayer();

        if (PlayerUtils.bypassesCheck(player, "step")) return;

        double diff = player.getY() - Speed.lastPos.get(player).getY();

        if (diff >= 1.2) {
            event.setCancelled(true);
            this.plugin.flag("Step", "Height: " + diff, player);
        }
    }
}
