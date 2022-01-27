package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJumpEvent;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.CheckUtils;

public class AirJump implements Listener {
    private final Revolutionarity plugin;

    public AirJump(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if (!this.plugin.getSettings().isAirJump()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.airjump.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

        if (player.getGamemode() == 1 || player.getGamemode() == 3) return;

        if (!CheckUtils.isOnGround(player)) {
            player.teleport(Speed.lastPos.get(player));
            this.plugin.flag("AirJump", player);
        }
    }
}
