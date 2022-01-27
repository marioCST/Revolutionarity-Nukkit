package de.mariocst.revolutionarity.utils;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Location;

public class CheckUtils {
    // getYawToEntity, getPitchToEntity and wrapAngle is from https://github.com/Vrekt/ArcNukkit

    public static float getYawToEntity(Location player, float playerYaw, Location entity) {
        final double deltaX = entity.getX() - player.getX();
        final double deltaZ = entity.getZ() - player.getZ();
        double yaw;

        final double atan = (deltaX < 0.0 && deltaZ < 0.0 || deltaX > 0.0 && deltaZ < 0.0) ? Math.atan(deltaZ / deltaX) : -Math.atan(deltaX / deltaZ);
        final double v = Math.toDegrees(atan);

        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = 90.0 + v;
        }
        else if (deltaX > 0.0 && deltaZ < 0.0) {
            yaw = -90.0 + v;
        }
        else {
            yaw = v;
        }

        return Math.abs(wrapAngle(-(playerYaw - (float) yaw)));
    }

    public static float getPitchToEntity(Location playerLocation, float playerPitch, Location entityLocation) {
        final double deltaX = entityLocation.getX() - playerLocation.getX();
        final double deltaY = entityLocation.getY() - playerLocation.getY();
        final double deltaZ = entityLocation.getZ() - playerLocation.getZ();

        final double horizontal = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double pitch = -Math.toDegrees(Math.atan(deltaY / horizontal));

        return Math.abs(wrapAngle(playerPitch - (float) pitch));
    }

    public static float wrapAngle(float angle) {
        angle = angle % 360.0F;

        if (angle >= 180.0F) angle -= 360.0F;
        if (angle < -180.0F) angle += 360.0F;

        return angle;
    }

    public static boolean isOnGround(Player player) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (player.getLevel().getBlock(player.getLocation().add(x, -0.5, z)).getId() != BlockID.AIR) {
                    return true;
                }
            }
        }

        return false;
    }
}
