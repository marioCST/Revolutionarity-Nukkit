package de.mariocst.revolutionarity.listener;

import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.event.PlayerFreezeEvent;

import java.util.HashMap;

public class FreezeEventListener extends Task {
    private final Revolutionarity plugin;

    private final HashMap<Player, Boolean> lastFreezed = new HashMap<>();

    public FreezeEventListener(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onRun(int i) {
        for (Player player : this.plugin.getServer().getOnlinePlayers().values()) {
            if (lastFreezed.containsKey(player)) {
                if (player.isImmobile() != lastFreezed.get(player)) {
                    //Wait, why did I even add this event???
                    PlayerFreezeEvent event = new PlayerFreezeEvent(player, player.isImmobile());
                    this.plugin.getServer().getPluginManager().callEvent(event);
                }
            }

            lastFreezed.put(player, player.isImmobile());

            if (player.isImmobile()) {
                this.plugin.frozen.put(player, player.getLocation());
            }
            else {
                this.plugin.frozen.remove(player);
            }
        }
    }
}
