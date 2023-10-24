package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.listener.PacketListener;
import de.mariocst.revolutionarity.utils.PlayerUtils;

public class NoSwing implements Listener {
    private final Revolutionarity plugin;

    public NoSwing(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        if (!this.plugin.getSettings().isNoSwing()) return;

        if (!(event.getPacket() instanceof InventoryTransactionPacket packet)) return;

        Player player = event.getPlayer();

        if (PlayerUtils.bypassesCheck(player, "noswing")) return;

        if (packet.transactionType != 3) return;

        if (!PacketListener.containsAnimatePacket(player)) {
            event.setCancelled(true);
            this.plugin.flag("NoSwing", player);
        }
    }
}
