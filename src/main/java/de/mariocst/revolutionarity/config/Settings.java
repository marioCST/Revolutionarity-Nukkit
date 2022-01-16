package de.mariocst.revolutionarity.config;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.revolutionarity.Revolutionarity;

import java.util.HashMap;

public class Settings {
    private final Revolutionarity plugin;

    private final ConfigSection config;

    public HashMap<Player, Double> velo = new HashMap<>();

    private boolean antiImmobile, blockReach, reach, selfHit;

    private double maxBlockReach, maxReach, maxVelo;

    public Settings(Revolutionarity plugin, ConfigSection configSection) {
        this.plugin = plugin;

        this.config = configSection;
        this.init();
    }

    public boolean isAntiImmobile() {
        return antiImmobile;
    }

    public boolean isBlockReach() {
        return blockReach;
    }

    public double getMaxBlockReach() {
        return maxBlockReach;
    }

    public boolean isReach() {
        return this.reach;
    }

    public double getMaxReach() {
        return this.maxReach;
    }

    public boolean isSelfHit() {
        return this.selfHit;
    }

    public double getMaxVelo() {
        return maxVelo;
    }

    private void init() {
        if (this.config.containsKey("antiImmobile")) {
            this.antiImmobile = this.config.getBoolean("antiImmobile");
        }
        else {
            this.antiImmobile = true;
        }

        if (this.config.containsKey("blockReach")) {
            this.blockReach = this.config.getBoolean("blockReach");
        }
        else {
            this.blockReach = true;
        }

        if (this.config.containsKey("maxBlockReach")) {
            this.maxBlockReach = this.config.getDouble("maxBlockReach");
        }
        else {
            this.maxBlockReach = 5.77;
        }

        if (this.config.containsKey("reach")) {
            this.reach = this.config.getBoolean("reach");
        }
        else {
            this.reach = true;
        }

        if (this.config.containsKey("maxReach")) {
            this.maxReach = this.config.getDouble("maxReach");
        }
        else {
            this.maxReach = 3.6;
        }

        if (this.config.containsKey("selfHit")) {
            this.selfHit = this.config.getBoolean("selfHit");
        }
        else {
            this.selfHit = true;
        }

        if (this.config.containsKey("maxVelo")) {
            this.maxVelo = this.config.getDouble("maxVelo");
        }
        else {
            this.maxVelo = 20.0;
        }

        this.save();
    }

    public void save() {
        try {
            this.config.put("antiImmobile", this.antiImmobile);
            this.config.put("blockReach", this.blockReach);
            this.config.put("maxBlockReach", this.maxBlockReach);
            this.config.put("reach", this.reach);
            this.config.put("maxReach", this.maxReach);
            this.config.put("selfHit", this.selfHit);
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
