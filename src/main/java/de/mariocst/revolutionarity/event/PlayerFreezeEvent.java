package de.mariocst.revolutionarity.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerFreezeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private boolean isFreezed;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerFreezeEvent(Player player, boolean isFreezed) {
        this.player = player;
        this.isFreezed = isFreezed;
    }

    public boolean isFreezed() {
        return isFreezed;
    }

    public void setFreezed(boolean freezed) {
        isFreezed = freezed;
    }
}
