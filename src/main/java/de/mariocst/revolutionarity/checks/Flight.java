package de.mariocst.revolutionarity.checks;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.AdventureSettingsPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import de.mariocst.revolutionarity.Revolutionarity;

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

            this.plugin.flag("Flight", "", player);
            adventureSettingsPacket.setFlag(AdventureSettingsPacket.FLYING, false);
            return;
        }

        if (player.getGamemode() != 1 && player.getGamemode() != 3 && adventureSettingsPacket.getFlag(AdventureSettingsPacket.FLYING)) {
            if (!this.plugin.getSettings().isFlight()) return;

            if (player.hasPermission("revolutionarity.flight.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

            this.plugin.flag("Flight", "", player);
            adventureSettingsPacket.setFlag(AdventureSettingsPacket.FLYING, false);
            return;
        }

        isFlying.remove(player);
        isFlying.put(player, adventureSettingsPacket.getFlag(AdventureSettingsPacket.FLYING));
    }

    public static boolean isFlying(Player player) {
        if (!isFlying.containsKey(player)) return false;
        return isFlying.get(player);
    }
}
