package de.mariocst.revolutionarity.commands;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import de.mariocst.revolutionarity.Revolutionarity;

import java.io.IOException;

public class ReportCommand extends Command {
    private final Revolutionarity plugin;

    public ReportCommand(Revolutionarity plugin) {
        super("report");
        this.plugin = plugin;
        this.setDescription("Report a player");
        this.setUsage("report [player] [reason]");
        this.setAliases(new String[]{"rp"});
        this.setPermission("revolutionarity.report");
        this.setPermissionMessage("§cNo permission");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("action", true, new String[]{"player"})
        });
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.getPrefix() + "Ban the player by yourself");
            return true;
        }

        Player player = (Player) sender;

        if (this.testPermission(player)) {
            if (args.length >= 2) {
                try {
                    Player t = this.plugin.getServer().getPlayer(args[0]);
                    IPlayer oT = this.plugin.getServer().getOfflinePlayer(args[0]);

                    if (t != null) {
                        StringBuilder msg = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            msg.append(args[i]).append(" ");
                        }

                        player.sendMessage("The player §c" + t.getName() + " §fhas been reported for §c" + msg + "§f!");

                        for (Player staff : this.plugin.getServer().getOnlinePlayers().values()) {
                            if (staff.hasPermission("revolutionarity.staff")) {
                                staff.sendMessage(this.plugin.getPrefix() + "The player §a" + player.getName() + " §freported §c" + t.getName() + " §ffor §c" + msg + "§f!");
                            }
                        }

                        if (!this.plugin.getPluginSettings().getDiscordWebhookLink().equals("")) {
                            try {
                                this.plugin.sendReport(player, t, msg.toString());
                                player.sendMessage("Your report has been successfully sent to the discord team!");
                            }
                            catch (IOException e) {
                                player.sendMessage("Your report couldn't be sent to the discord team because an error occurred.");
                            }
                        }
                    }
                    else if (oT != null) {
                        StringBuilder msg = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            msg.append(args[i]).append(" ");
                        }

                        player.sendMessage("The player §c" + oT.getName() + " §fhas been reported for §c" + msg + "§f!");

                        for (Player staff : this.plugin.getServer().getOnlinePlayers().values()) {
                            if (staff.hasPermission("revolutionarity.staff")) {
                                staff.sendMessage("The player §a" + player.getName() + " §freported §c" + oT.getName() + " §ffor §c" + msg + "§f!");
                            }
                        }

                        if (!this.plugin.getPluginSettings().getDiscordWebhookLink().equals("")) {
                            try {
                                this.plugin.sendReport(player, oT, msg.toString());
                                player.sendMessage("Your report has been successfully sent to the discord team!");
                            }
                            catch (IOException e) {
                                player.sendMessage("Your report couldn't be sent to the discord team because an error occurred.");
                            }
                        }
                    }
                    else {
                        player.sendMessage("§cThe player " + args[0] + " doesn't exist!");
                    }
                }
                catch (NullPointerException e) {
                    player.sendMessage("§cThe player " + args[0] + " doesn't exist!");
                }
            }
            else if (args.length == 1) {
                try {
                    Player t = this.plugin.getServer().getPlayer(args[0]);
                    IPlayer oT = this.plugin.getServer().getOfflinePlayer(args[0]);

                    if (t != null) {
                        player.sendMessage("The player §c" + t.getName() + " §fhas been reported!");

                        for (Player staff : this.plugin.getServer().getOnlinePlayers().values()) {
                            if (staff.hasPermission("revolutionarity.staff")) {
                                staff.sendMessage("The player §a" + player.getName() + " §freported §c" + t.getName() + " §f!");
                            }
                        }

                        if (!this.plugin.getPluginSettings().getDiscordWebhookLink().equals("")) {
                            try {
                                this.plugin.sendReport(player, t, "No reason");
                                player.sendMessage("Your report has been successfully sent to the discord team!");
                            }
                            catch (IOException e) {
                                player.sendMessage("Your report couldn't be sent to the discord team because an error occurred.");
                            }
                        }
                    }
                    else if (oT != null) {
                        player.sendMessage("The player §c" + oT.getName() + " §fhas been reported!");

                        for (Player staff : this.plugin.getServer().getOnlinePlayers().values()) {
                            if (staff.hasPermission("revolutionarity.staff")) {
                                staff.sendMessage("The player §a" + player.getName() + " §freported §c" + oT.getName() + " §f!");
                            }
                        }

                        if (!this.plugin.getPluginSettings().getDiscordWebhookLink().equals("")) {
                            try {
                                this.plugin.sendReport(player, oT, "No reason");
                                player.sendMessage("Your report has been successfully sent to the discord team!");
                            }
                            catch (IOException e) {
                                player.sendMessage("Your report couldn't be sent to the discord team because an error occurred.");
                            }
                        }
                    }
                    else {
                        player.sendMessage("§cThe player " + args[0] + " doesn't exist!");
                    }
                }
                catch (NullPointerException e) {
                    player.sendMessage("§cThe player " + args[0] + " doesn't exist!");
                }
            }
            else {
                this.plugin.getReportForm().openReport(this.plugin, player);
            }
        }
        return false;
    }
}
