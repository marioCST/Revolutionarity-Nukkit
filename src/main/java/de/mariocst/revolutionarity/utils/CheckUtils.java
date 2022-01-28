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
        /*double subtract = -0.3;
        
        if (isCoordinateXBetween(player, 0.3, 0.7) && isCoordinateZBetween(player, 0.0, 0.3)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(0.0, subtract, -1)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.3, 0.7) && isCoordinateZBetween(player, 0.7, 1.0)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 1)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.0, 0.3) && isCoordinateZBetween(player, 0.3, 0.7)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(-1, subtract, 0.0)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.7, 1.0) && isCoordinateZBetween(player, 0.3, 0.7)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(1, subtract, 0.0)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.3, 0.7) && isCoordinateZBetween(player, 0.3, 0.7)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.0, 0.3) && isCoordinateZBetween(player, 0.0, 0.3)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(-1, subtract, -1)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(-1, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(0.0, subtract, -1)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.7, 1.0) && isCoordinateZBetween(player, 0.7, 1.0)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(1, subtract, 1)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(1, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 1)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.7, 1.0) && isCoordinateZBetween(player, 0.0, 0.3)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(1, subtract, -1)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(1, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(0.0, subtract, -1)).getId() != BlockID.AIR;
        }

        if (isCoordinateXBetween(player, 0.0, 0.3) && isCoordinateZBetween(player, 0.7, 1.0)) {
            return player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(-1, subtract, 1)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(-1, subtract, 0.0)).getId() != BlockID.AIR ||
                    player.getLevel().getBlock(player.getLocation().add(0.0, subtract, 1)).getId() != BlockID.AIR;
        }*/

        if (player.getY() != Math.floor(player.getY())) {
            if (player.getLevelBlock().getId() != BlockID.AIR) return true;

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (player.getLevel().getBlock(player.getLocation().add(x, 0.0, z)).getId() != BlockID.AIR && x != 0 && z != 0) continue;

                    if (player.getLevel().getBlock(player.getLocation().add(x, -0.1, z)).getId() != BlockID.AIR) {
                        return true;
                    }
                    else if (player.getLevel().getBlock(player.getLocation().add(x, -0.2, z)).getId() != BlockID.AIR) {
                        return true;
                    }
                    else if (player.getLevel().getBlock(player.getLocation().add(x, -0.6, z)).getId() != BlockID.AIR && player.getY() >= Math.floor(player.getY()) + 0.47) {
                        return true;
                    }
                    else if (player.getLevel().getBlock(player.getLocation().add(x, -0.8, z)).getId() != BlockID.AIR && player.getY() >= Math.floor(player.getY()) + 0.74) {
                        return true;
                    }
                    else if (player.getLevel().getBlock(player.getLocation().add(x, -0.95, z)).getId() != BlockID.AIR && player.getY() >= Math.floor(player.getY()) + 0.89) {
                        return true;
                    }
                }
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (player.getLevel().getBlock(player.getLocation().add(x, 0.0, z)).getId() != BlockID.AIR && x != 0 && z != 0) continue;

                if (player.getLevel().getBlock(player.getLocation().add(x, -0.3, z)).getId() != BlockID.AIR) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isCoordinateXBetween(Player player, double minX, double maxX) {
        if (player.getX() < 0) return player.getX() <= Math.floor(player.getX()) - minX && player.getX() >= Math.floor(player.getX()) - maxX;

        return player.getX() >= Math.floor(player.getX()) + minX && player.getX() <= Math.floor(player.getX()) + maxX;
    }

    private static boolean isCoordinateYBetween(Player player, double minY, double maxY) {
        if (player.getY() < 0) return player.getY() <= Math.floor(player.getY()) - minY && player.getY() >= Math.floor(player.getY()) - maxY;

        return player.getY() >= Math.floor(player.getY()) + minY && player.getY() <= Math.floor(player.getY()) + maxY;
    }

    private static boolean isCoordinateZBetween(Player player, double minZ, double maxZ) {
        if (player.getZ() < 0) return player.getZ() <= Math.floor(player.getZ()) - minZ && player.getZ() >= Math.floor(player.getZ()) - maxZ;

        return player.getZ() >= Math.floor(player.getZ()) + minZ && player.getZ() <= Math.floor(player.getZ()) + maxZ;
    }
}
