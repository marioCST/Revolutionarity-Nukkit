package de.mariocst.revolutionarity.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import de.mariocst.revolutionarity.Revolutionarity;

public class JoinListener implements Listener {
    private final Revolutionarity plugin;
    
    public JoinListener(Revolutionarity plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getLoginChainData().getDeviceOS() == 1) { // Android
            if (!player.getLoginChainData().getDeviceModel().equals(player.getLoginChainData().getDeviceModel().toUpperCase())) {
                this.plugin.flag("Anti ToolBox", player);
                player.kick(this.plugin.getPluginSettings().getKickMessage(), false);
            }
        }

        if ((player.getLoginChainData().getDeviceOS() == 1 || player.getLoginChainData().getDeviceOS() == 2) && player.getLoginChainData().getDeviceModel().equals("")) { // Android, iOS
            this.plugin.flag("EditionFaker", player);
            player.kick(this.plugin.getPluginSettings().getKickMessage(), false);
        }
    }
}
