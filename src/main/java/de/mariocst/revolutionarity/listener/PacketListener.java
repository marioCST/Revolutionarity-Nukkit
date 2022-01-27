package de.mariocst.revolutionarity.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;

import java.util.HashMap;

public class PacketListener implements Listener {
    private static final HashMap<Player, DataPacket> lastLastLastPacket = new HashMap<>();
    private static final HashMap<Player, DataPacket> lastLastPacket = new HashMap<>();
    private static final HashMap<Player, DataPacket> lastPacket = new HashMap<>();
    private static final HashMap<Player, DataPacket> currentPacket = new HashMap<>();

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof LevelSoundEventPacket) return;

        lastLastLastPacket.remove(event.getPlayer());
        if (lastLastPacket.containsKey(event.getPlayer())) lastLastLastPacket.put(event.getPlayer(), lastLastPacket.get(event.getPlayer()));

        lastLastPacket.remove(event.getPlayer());
        if (lastPacket.containsKey(event.getPlayer())) lastLastPacket.put(event.getPlayer(), lastPacket.get(event.getPlayer()));

        lastPacket.remove(event.getPlayer());
        if (currentPacket.containsKey(event.getPlayer())) lastPacket.put(event.getPlayer(), currentPacket.get(event.getPlayer()));

        currentPacket.remove(event.getPlayer());
        currentPacket.put(event.getPlayer(), event.getPacket());
    }

    public static boolean containsAnimatePacket(Player player) {
        return lastLastLastPacket.get(player) instanceof AnimatePacket || lastLastPacket.get(player) instanceof AnimatePacket || lastPacket.get(player) instanceof AnimatePacket;
    }
}
