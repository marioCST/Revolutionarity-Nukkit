package de.mariocst.revolutionarity.listener;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.scheduler.Task;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.CheckUtils;

import java.util.HashMap;

public class PlayerTasks extends Task {
    private final Revolutionarity plugin;

    public static final HashMap<Player, Location> lastOnGround = new HashMap<>();

    public PlayerTasks(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onRun(int i) {
        for (Player player : this.plugin.getServer().getOnlinePlayers().values()) {
            if (CheckUtils.isOnGround(player) || player.getAdventureSettings().get(AdventureSettings.Type.FLYING)) {
                lastOnGround.remove(player);
                lastOnGround.put(player, player.getLocation());
            }
        }
    }
}
