package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJumpEvent;
import cn.nukkit.math.Vector3;
import de.mariocst.revolutionarity.Revolutionarity;

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

        boolean foundBlock = false;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (player.getLevel().getBlock(new Vector3(player.getX() + x, player.getY() - 0.1, player.getZ() + z)).getId() != BlockID.AIR) {
                    foundBlock = true;
                    break;
                }
            }
        }

        if (player.getLevel().getBlock(player.getLocation().add(0.0, -0.1, 0.0)).getId() == BlockID.AIR && !foundBlock) {
            player.teleport(Speed.lastPos.get(player));
            this.plugin.flag("AirJump", "", player);
        }
    }
}
