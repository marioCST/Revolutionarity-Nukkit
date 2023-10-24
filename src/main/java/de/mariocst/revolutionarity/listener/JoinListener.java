package de.mariocst.revolutionarity.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.PlayerUtils;

public class JoinListener implements Listener {
    private final Revolutionarity plugin;
    
    public JoinListener(Revolutionarity plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getLoginChainData().getDeviceOS() == 1) { // Android
            if (!this.plugin.getSettings().isToolBox()) return;

            if (PlayerUtils.bypassesCheck(player, "toolbox")) return;

            if (player.getLoginChainData().getDeviceModel().contains("nokia")) {
                if (player.getLoginChainData().getDeviceModel().equals(player.getLoginChainData().getDeviceModel().toLowerCase())) {
                    this.plugin.flag("ToolBox", "Device model: " + player.getLoginChainData().getDeviceModel(), player);
                    this.plugin.getSettings().velo.remove(player);
                    this.plugin.getSettings().velo.put(player, this.plugin.getSettings().getMaxVelo());
                }
            }
            else {
                if (!player.getLoginChainData().getDeviceModel().equals(player.getLoginChainData().getDeviceModel().toUpperCase())) {
                    this.plugin.flag("ToolBox", "Device model: " + player.getLoginChainData().getDeviceModel(), player);
                    this.plugin.getSettings().velo.remove(player);
                    this.plugin.getSettings().velo.put(player, this.plugin.getSettings().getMaxVelo());
                }
            }
        }

        // PCs do have device models now
        /*if ((player.getLoginChainData().getDeviceOS() == 1 || player.getLoginChainData().getDeviceOS() == 2) && player.getLoginChainData().getDeviceModel().equals("")) { // Android, iOS
            if (!this.plugin.getSettings().isEditionFaker()) return;

            if (player.hasPermission("revolutionarity.bypass.editionfaker") ||
                    player.hasPermission("revolutionarity.bypass.*") ||
                    player.hasPermission("revolutionarity.*") ||
                    player.hasPermission("*") ||
                    player.isOp()) return;

            this.plugin.flag("EditionFaker", player);
            this.plugin.getSettings().velo.remove(player);
            this.plugin.getSettings().velo.put(player, this.plugin.getSettings().getMaxVelo());
        }
        else */if (player.getLoginChainData().getDeviceOS() == 12 && !player.getLoginChainData().getDeviceModel().equals("Switch")) { // Nintendo Switch
            if (!this.plugin.getSettings().isEditionFaker()) return;

            if (PlayerUtils.bypassesCheck(player, "editionfaker")) return;

            this.plugin.flag("EditionFaker", player);
            this.plugin.getSettings().velo.remove(player);
            this.plugin.getSettings().velo.put(player, this.plugin.getSettings().getMaxVelo());
        }
    }
}
