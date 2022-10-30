package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemID;
import cn.nukkit.network.protocol.PlayerActionPacket;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.listener.PlayerTasks;

public class NoFall implements Listener {
    private final Revolutionarity plugin;

    public NoFall(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(DataPacketReceiveEvent event) {
        if (!this.plugin.getSettings().isNoFall()) return;

        if (!(event.getPacket() instanceof PlayerActionPacket)) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.nofall") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        PlayerActionPacket packet = (PlayerActionPacket) event.getPacket();

        if (packet.action != PlayerActionPacket.ACTION_START_GLIDE) return;

        if (player.getInventory().getChestplate().getId() != ItemID.ELYTRA) {
            event.setCancelled(true);
            player.teleport(PlayerTasks.lastOnGround.get(player));
            this.plugin.flag("NoFall", player);
        }
    }
}
