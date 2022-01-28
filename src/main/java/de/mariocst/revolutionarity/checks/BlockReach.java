package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.level.Location;
import de.mariocst.revolutionarity.Revolutionarity;

public class BlockReach implements Listener {
    private final Revolutionarity plugin;

    public BlockReach(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!this.plugin.getSettings().isBlockReach()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.hasPermission("revolutionarity.bypass.blockreach") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        Location playerLoc = player.getLocation();
        Location blockLoc = block.getLocation();

        double distance = playerLoc.distance(blockLoc.getX(), blockLoc.getY(), blockLoc.getZ());

        if (distance > this.plugin.getSettings().getMaxBlockReach()) {
            event.setCancelled(true);
            this.plugin.flag("BlockReach", "Distance: " + distance, player);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!this.plugin.getSettings().isBlockReach()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.hasPermission("revolutionarity.bypass.blockreach") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        Location playerLoc = player.getLocation();
        Location blockLoc = block.getLocation();

        double distance = playerLoc.distance(blockLoc.getX(), blockLoc.getY(), blockLoc.getZ());

        if (distance > this.plugin.getSettings().getMaxBlockReach()) {
            event.setCancelled(true);
            this.plugin.flag("BlockReach", "Distance: " + distance, player);
        }
    }
}
