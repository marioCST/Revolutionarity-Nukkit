package de.mariocst.revolutionarity.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;

import java.util.HashMap;

public class PacketListener implements Listener {
    public final static HashMap<Player, DataPacket> lastPacket = new HashMap<>();
    private final static HashMap<Player, DataPacket> currentPacket = new HashMap<>();

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof LevelSoundEventPacket) return;

        lastPacket.remove(event.getPlayer());
        if (currentPacket.containsKey(event.getPlayer())) lastPacket.put(event.getPlayer(), currentPacket.get(event.getPlayer()));

        currentPacket.remove(event.getPlayer());
        currentPacket.put(event.getPlayer(), event.getPacket());
    }
}
