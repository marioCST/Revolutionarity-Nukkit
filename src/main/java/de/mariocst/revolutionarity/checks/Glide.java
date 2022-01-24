package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.math.Vector3;
import de.mariocst.revolutionarity.Revolutionarity;

import java.util.HashMap;

public class Glide implements Listener {
    private final Revolutionarity plugin;

    public Glide(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    private final HashMap<Player, Integer> ticksInAir = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.plugin.getSettings().isGlide()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.glide.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

        boolean foundBlock = false;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (player.getLevel().getBlock(new Vector3(player.getX() + x, player.getY() - 0.1, player.getZ() + z)).getId() != BlockID.AIR) {
                    foundBlock = true;
                    break;
                }
            }
        }

        if (event.getFrom().getY() == player.getY() && player.getLevel().getBlock(player.getLocation().add(0.0, -0.1, 0.0)).getId() == BlockID.AIR && !foundBlock) {
            int airTime = this.ticksInAir.containsKey(player) ? this.ticksInAir.get(player) + 1 : 1;

            this.ticksInAir.remove(player);
            this.ticksInAir.put(player, airTime);
        }
        else {
            this.ticksInAir.remove(player);
        }

        if (!this.ticksInAir.containsKey(player)) return;

        if (this.ticksInAir.get(player) >= this.plugin.getSettings().getMaxTicksInAir()) {
            event.setCancelled(true);
            if (player.getLevel().getBlock(player.getLocation().add(0.0, -0.5, 0.0)).getId() == BlockID.AIR) player.teleport(player.getLocation().add(0.0, -0.5, 0.0));
            this.plugin.flag("Glide", "AirTime: " + this.ticksInAir.get(player) + "/" + this.plugin.getSettings().getMaxTicksInAir(), player);
        }
    }
}
