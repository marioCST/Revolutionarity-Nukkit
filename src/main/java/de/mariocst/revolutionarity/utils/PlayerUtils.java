package de.mariocst.revolutionarity.utils;

import cn.nukkit.Player;

public class PlayerUtils {
    public static boolean bypassesCheck(Player player, String permission) {
        return player.hasPermission("revolutionarity.bypass." + permission) || player.isOp();
    }
}
