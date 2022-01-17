package de.mariocst.revolutionarity.checks;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Location;
import cn.nukkit.scheduler.Task;
import de.mariocst.revolutionarity.Revolutionarity;

import java.util.HashMap;

public class Speed extends Task {
    private final Revolutionarity plugin;

    private final HashMap<Player, Location> pos = new HashMap<>();
    private final HashMap<Player, Location> lastPos = new HashMap<>();

    public Speed(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    // Experimental
    @Override
    public void onRun(int i) {
        for (Player player : this.plugin.getServer().getOnlinePlayers().values()) {
            lastPos.remove(player);
            lastPos.put(player, pos.get(player));

            pos.remove(player);
            pos.put(player, player.getLocation());

            if (!this.plugin.getSettings().isSpeed()) return;

            if (player.hasPermission("revolutionarity.speed.bypass") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) return;

            if (player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT)) return;

            try {
                double posX = pos.get(player).getX();
                double posZ = pos.get(player).getZ();

                double posXOld = lastPos.get(player).getX();
                double posZOld = lastPos.get(player).getZ();

                if (posX < 0) posX *= -1;
                if (posZ < 0) posZ *= -1;

                if (posXOld < 0) posXOld *= -1;
                if (posZOld < 0) posZOld *= -1;

                double diffX = posX - posXOld;
                double diffZ = posZ - posZOld;

                if (diffX < 0) diffX *= -1;
                if (diffZ < 0) diffZ *= -1;

                diffX *= 5;
                diffZ *= 5;

                double speed = Math.sqrt((diffX * diffX) + (diffZ * diffZ));

                if (!player.isOnGround())
                    speed /= 1.5;

                double maxSpeed = 5.5;

                if (player.isSprinting()) {
                    if (player.getLevel().getBlock(player.getLocation().add(0.0, 2.0, 0.0)).getId() != BlockID.AIR) {
                        maxSpeed += 6.5;
                    }
                    else {
                        maxSpeed += 3.1;
                    }
                }

                if (player.isSneaking()) {
                    maxSpeed -= 3.7;
                }

                if (speed > maxSpeed) {
                    player.teleport(lastPos.get(player));
                    this.plugin.flag("Speed", "Speed: " + speed + "/" + maxSpeed, player);
                }
            }
            catch (NullPointerException ignored) { }
        }
    }
}
