package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemID;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.listener.PlayerTasks;
import de.mariocst.revolutionarity.utils.PlayerUtils;

public class NoFall implements Listener {
    private final Revolutionarity plugin;

    public NoFall(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(DataPacketReceiveEvent event) {
        if (!this.plugin.getSettings().isNoFall()) return;

        Player player = event.getPlayer();

        if (PlayerUtils.bypassesCheck(player, "nofall")) return;

        if (event.getPacket() instanceof PlayerActionPacket pap)
            if (pap.action != PlayerActionPacket.ACTION_START_GLIDE) return;

        if (event.getPacket() instanceof PlayerAuthInputPacket paip)
            if (!paip.getInputData().contains(AuthInputAction.START_GLIDING)) return;

        if (player.getInventory().getChestplate().getId() != ItemID.ELYTRA) {
            event.setCancelled(true);
            player.teleport(PlayerTasks.lastOnGround.get(player));
            this.plugin.flag("NoFall", player);
        }
    }
}
