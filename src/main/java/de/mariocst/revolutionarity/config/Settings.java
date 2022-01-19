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
    private boolean airJump, antiImmobile, blockReach, flight, killAura, reach, selfHit, speed;

    @Getter
    private double maxBlockReach, maxYaw, maxReach, maxVelo;

    public Settings(Revolutionarity plugin, ConfigSection configSection) {
        this.plugin = plugin;

        this.config = configSection;
        this.init();
    }

    private void init() {
        if (this.config.containsKey("airJump")) {
            this.airJump = this.config.getBoolean("airJump");
        }
        else {
            this.airJump = true;
        }

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

        if (this.config.containsKey("flight")) {
            this.flight = this.config.getBoolean("flight");
        }
        else {
            this.flight = true;
        }

        if (this.config.containsKey("killAura")) {
            this.killAura = this.config.getBoolean("killAura");
        }
        else {
            this.killAura = true;
        }

        if (this.config.containsKey("maxYaw")) {
            this.maxYaw = this.config.getDouble("maxYaw");
        }
        else {
            this.maxYaw = 66.0;
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

        if (this.config.containsKey("speed")) {
            this.speed = this.config.getBoolean("speed");
        }
        else {
            this.speed = true;
        }

        if (this.config.containsKey("maxVelo")) {
            this.maxVelo = this.config.getDouble("maxVelo");
        }
        else {
            this.maxVelo = 20.0;
        }
    }

    public void save() {
        try {
            this.config.put("airJump", this.airJump);
            this.config.put("antiImmobile", this.antiImmobile);
            this.config.put("blockReach", this.blockReach);
            this.config.put("maxBlockReach", this.maxBlockReach);
            this.config.put("flight", this.flight);
            this.config.put("killAura", this.killAura);
            this.config.put("maxYaw", this.maxYaw);
            this.config.put("reach", this.reach);
            this.config.put("maxReach", this.maxReach);
            this.config.put("selfHit", this.selfHit);
            this.config.put("speed", this.speed);
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
