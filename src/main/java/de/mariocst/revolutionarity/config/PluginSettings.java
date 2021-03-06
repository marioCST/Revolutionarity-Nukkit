package de.mariocst.revolutionarity.config;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;
import lombok.Setter;

public class PluginSettings {
    private final Revolutionarity plugin;

    private final ConfigSection config;

    @Getter
    @Setter
    private String prefix;

    @Getter
    private String discordWebhookLink;

    @Getter
    @Setter
    private String kickMessage;

    public PluginSettings(Revolutionarity plugin, ConfigSection config) {
        this.plugin = plugin;

        this.config = config;

        this.init();
    }

    private void init() {
        if (this.config.containsKey("prefix")) {
            this.prefix = this.config.getString("prefix");
        }
        else {
            this.prefix = "§8[§3Revolutionarity§8] | §f";
        }

        this.plugin.setPrefix(this.prefix);

        if (this.config.containsKey("discordWebhookLink")) {
            this.discordWebhookLink = this.config.getString("discordWebhookLink");
        }
        else {
            this.discordWebhookLink = "";
        }

        if (this.config.containsKey("kickMessage")) {
            this.kickMessage = this.config.getString("kickMessage");
        }
        else {
            this.kickMessage = "§cError while handling player movement";
        }
    }

    public void save() {
        this.config.put("prefix", this.prefix);
        this.config.put("discordWebhookLink", this.discordWebhookLink);
        this.config.put("kickMessage", this.kickMessage);
        Config c = new Config(this.plugin.getDataFolder() + "/pluginSettings.yml", Config.YAML);
        c.setAll(this.config);
        c.save();
    }
}
