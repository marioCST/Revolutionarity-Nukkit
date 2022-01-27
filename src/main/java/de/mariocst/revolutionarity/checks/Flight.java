package de.mariocst.revolutionarity.checks;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.AdventureSettingsPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.potion.Effect;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.listener.PlayerTasks;
import de.mariocst.revolutionarity.utils.CheckUtils;

import java.util.HashMap;

public class Flight implements Listener {
    private final Revolutionarity plugin;

    private static final HashMap<Player, Boolean> isFlying = new HashMap<>();

    public Flight(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();

        if (event.getPacket().pid() != ProtocolInfo.ADVENTURE_SETTINGS_PACKET) return;

        AdventureSettingsPacket adventureSettingsPacket = (AdventureSettingsPacket) event.getPacket();

        if (!player.getServer().getAllowFlight() && adventureSettingsPacket.getFlag(AdventureSettingsPacket.FLYING) && !player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT)) {
            if (!this.plugin.getSettings().isFlight()) return;

            if (player.hasPermission("revolutionarity.flight.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

            this.plugin.flag("FlightA", player);
            adventureSettingsPacket.setFlag(AdventureSettingsPacket.FLYING, false);
            return;
        }

        if (player.getGamemode() != 1 && player.getGamemode() != 3 && adventureSettingsPacket.getFlag(AdventureSettingsPacket.FLYING)) {
            if (!this.plugin.getSettings().isFlight()) return;

            if (player.hasPermission("revolutionarity.flight.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

            this.plugin.flag("FlightA", player);
            adventureSettingsPacket.setFlag(AdventureSettingsPacket.FLYING, false);
            return;
        }

        isFlying.remove(player);
        isFlying.put(player, adventureSettingsPacket.getFlag(AdventureSettingsPacket.FLYING));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.plugin.getSettings().isFlight()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.flight.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

        if (player.getEffects().containsKey(Effect.JUMP_BOOST)) return; // Checks will be implemented later

        if (CheckUtils.isOnGround(player)) return;

        if (!PlayerTasks.lastOnGround.containsKey(player)) return;

        if (PlayerTasks.lastOnGround.get(player).getY() < player.getY() - 2.0) {
            player.teleport(PlayerTasks.lastOnGround.get(player));
            this.plugin.flag("FlightB", player);
        }
    }

    public static boolean isFlying(Player player) {
        if (!isFlying.containsKey(player)) return false;
        return isFlying.get(player);
    }
}
