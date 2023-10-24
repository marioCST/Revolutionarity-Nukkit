package de.mariocst.revolutionarity.config;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

import java.util.HashMap;

public class Settings {
    private final Revolutionarity plugin;

    private final ConfigSection config;

    public HashMap<Player, Double> velo = new HashMap<>();

    @Getter
    private boolean airJump, antiImmobile, blockReach, editionFaker, flight, glide, noFall, noSwing, reach, selfHit, speed, step, toolBox;

    @Getter
    private int maxTicksInAir;

    @Getter
    private double maxBlockReach, maxReach, maxVelo;

    public Settings(Revolutionarity plugin, ConfigSection configSection) {
        this.plugin = plugin;

        this.config = configSection;
        this.init();
    }

    private void init() {
        this.airJump = this.config.getBoolean("airJump", true);
        this.antiImmobile = this.config.getBoolean("antiImmobile", true);
        this.blockReach = this.config.getBoolean("blockReach", true);
        this.maxBlockReach = this.config.getDouble("maxBlockReach", 6.2);
        this.editionFaker = this.config.getBoolean("editionFaker", true);
        this.flight = this.config.getBoolean("flight", true);
        this.glide = this.config.getBoolean("glide", true);
        this.maxTicksInAir = this.config.getInt("maxTicksInAir", 5);
        this.noFall = this.config.getBoolean("noFall", true);
        this.noSwing = this.config.getBoolean("noSwing");
        this.reach = this.config.getBoolean("reach", true);
        this.maxReach = this.config.getDouble("maxReach", 3.6);
        this.selfHit = this.config.getBoolean("selfHit", true);
        this.speed = this.config.getBoolean("speed");
        this.step = this.config.getBoolean("step", true);
        this.toolBox = this.config.getBoolean("toolBox", true);
        this.maxVelo = this.config.getDouble("maxVelo", 20.0);
    }

    public void save() {
        try {
            this.config.put("airJump", this.airJump);
            this.config.put("antiImmobile", this.antiImmobile);
            this.config.put("blockReach", this.blockReach);
            this.config.put("maxBlockReach", this.maxBlockReach);
            this.config.put("editionFaker", this.editionFaker);
            this.config.put("flight", this.flight);
            this.config.put("glide", this.glide);
            this.config.put("maxTicksInAir", this.maxTicksInAir);
            this.config.put("noFall", this.noSwing);
            this.config.put("noSwing", this.noSwing);
            this.config.put("reach", this.reach);
            this.config.put("maxReach", this.maxReach);
            this.config.put("selfHit", this.selfHit);
            this.config.put("speed", this.speed);
            this.config.put("step", this.step);
            this.config.put("toolBox", this.toolBox);
            this.config.put("maxVelo", this.maxVelo);

            Config c = new Config(this.plugin.getDataFolder() + "/settings.yml", Config.YAML);
            c.setAll(this.config);
            c.save();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
