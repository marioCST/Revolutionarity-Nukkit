package de.mariocst.revolutionarity.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerFreezeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private boolean frozen;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerFreezeEvent(Player player, boolean frozen) {
        this.player = player;
        this.frozen = frozen;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
}
