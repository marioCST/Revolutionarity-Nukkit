package de.mariocst.revolutionarity.checks;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import cn.nukkit.potion.Effect;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.listener.PlayerTasks;
import de.mariocst.revolutionarity.utils.CheckUtils;
import de.mariocst.revolutionarity.utils.PlayerUtils;

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

        if (!player.getServer().getAllowFlight() && !player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT)) {
            if (event.getPacket() instanceof PlayerActionPacket pap)
                if (pap.action != PlayerActionPacket.ACTION_START_FLYING) return;

            if (event.getPacket() instanceof PlayerAuthInputPacket paip)
                if (!paip.getInputData().contains(AuthInputAction.START_FLYING)) return;

            if (!this.plugin.getSettings().isFlight()) return;

            if (PlayerUtils.bypassesCheck(player, "flight")) return;

            this.plugin.flag("FlightA", player);
            event.setCancelled(true);
            return;
        }

        boolean updated = false;
        boolean flying = false;

        if (event.getPacket() instanceof PlayerActionPacket pap) {
            if (pap.action == PlayerActionPacket.ACTION_START_FLYING) {
                updated = true;
                flying = true;
            }

            if (pap.action == PlayerActionPacket.ACTION_STOP_FLYING)
                updated = true;
        }

        if (event.getPacket() instanceof PlayerAuthInputPacket paip) {
            if (paip.getInputData().contains(AuthInputAction.START_FLYING)) {
                updated = true;
                flying = true;
            }

            if (paip.getInputData().contains(AuthInputAction.STOP_FLYING))
                updated = true;
        }

        if (!updated) return;

        isFlying.remove(player);
        isFlying.put(player, flying);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.plugin.getSettings().isFlight()) return;

        Player player = event.getPlayer();

        if (PlayerUtils.bypassesCheck(player, "flight")) return;

        if (player.getEffects().containsKey(Effect.JUMP_BOOST)) return; // Checks will be implemented later

        if (player.getAdventureSettings().get(AdventureSettings.Type.FLYING) || isFlying.containsKey(player)) return;

        if (CheckUtils.isOnGround(player)) return;

        if (!PlayerTasks.lastOnGround.containsKey(player)) return;

        if (PlayerTasks.lastOnGround.get(player).getY() < player.getY() - 2.0) {
            player.teleport(PlayerTasks.lastOnGround.get(player));
            this.plugin.flag("FlightB", player);
        }
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        if (!this.plugin.getSettings().isFlight()) return;

        Player player = event.getPlayer();

        if (PlayerUtils.bypassesCheck(player, "flight")) return;

        if (!player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT) && player.getAdventureSettings().get(AdventureSettings.Type.FLYING)) {
            player.getAdventureSettings().set(AdventureSettings.Type.FLYING, false);
            isFlying.remove(player);
            isFlying.put(player, player.getAdventureSettings().get(AdventureSettings.Type.FLYING));
            this.plugin.flag("FlightC", player);
        }
    }

    public static boolean isFlying(Player player) {
        return isFlying.getOrDefault(player, false);
    }
}
