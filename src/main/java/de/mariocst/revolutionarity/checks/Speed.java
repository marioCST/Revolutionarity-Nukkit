package de.mariocst.revolutionarity.checks;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Location;
import cn.nukkit.scheduler.Task;
import de.mariocst.revolutionarity.Revolutionarity;

import java.util.HashMap;

public class Speed extends Task implements Listener {
    private final Revolutionarity plugin;

    private final HashMap<Player, Location> pos = new HashMap<>();
    public static final HashMap<Player, Location> lastPos = new HashMap<>();

    private final HashMap<Player, Integer> wait = new HashMap<>();

    public Speed(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    /*@EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.plugin.getSettings().isSpeed()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.speed") ||
            player.hasPermission("revolutionarity.bypass.*") ||
            player.hasPermission("revolutionarity.*") ||
            player.hasPermission("*")
            || player.isOp()) return;

        if (player.getEffects().containsKey(Effect.SPEED)) return;

        if (player.getAdventureSettings().get(AdventureSettings.Type.FLYING)) return;

        if (player.getGamemode() == 3) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        Vector3f vector = to.asVector3f().setY(0.0f);
        double dist = vector.distance(from.asVector3f().setY(0.0f));

        dist *= player.getServer().getTicksPerSecond();

        player.sendMessage(this.plugin.getPrefix() + "Speed: " + dist);
    }*/

    @Override
    public void onRun(int i) {
        for (Player player : this.plugin.getServer().getOnlinePlayers().values()) {
            lastPos.remove(player);
            lastPos.put(player, pos.get(player));

            pos.remove(player);
            pos.put(player, player.getLocation());

            /*if (!this.plugin.getSettings().isSpeed()) return;

            if (player.hasPermission("revolutionarity.bypass.speed") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

            if (this.wait.containsKey(player)) if (this.wait.get(player) != 0) {
                this.wait.put(player, this.wait.get(player) - 1);
                return;
            }

            if (Flight.isFlying(player)) return;

            Vector3f vector = pos.get(player).asVector3f().setY(0.0f);
            double dist = vector.distance(lastPos.get(player).asVector3f().setY(0.0f)) * 20;

            player.sendMessage(this.plugin.getPrefix() + "Speed: " + dist);*/

            /*try {
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

                diffX *= player.getServer().getTicksPerSecond();
                diffZ *= player.getServer().getTicksPerSecond();

                double speed = Math.sqrt((diffX * diffX) + (diffZ * diffZ));

                if (!player.isOnGround())
                    speed /= 1.5;

                // Nukkit is breaking the limits

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
                    this.wait.put(player, 3);
                }
            }
            catch (NullPointerException ignored) { }*/
        }
    }
}
