package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.listener.PacketListener;

public class NoSwing implements Listener {
    private final Revolutionarity plugin;

    public NoSwing(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        if (!this.plugin.getSettings().isNoSwing()) return;

        if (!(event.getPacket() instanceof InventoryTransactionPacket)) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.noswing.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

        InventoryTransactionPacket packet = (InventoryTransactionPacket) event.getPacket();

        if (!(PacketListener.lastPacket.get(player) instanceof AnimatePacket)) {
            event.setCancelled(true);
            this.plugin.flag("NoSwing", "", player);
        }
    }
}
