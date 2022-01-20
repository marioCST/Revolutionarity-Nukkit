package de.mariocst.revolutionarity.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import de.mariocst.revolutionarity.Revolutionarity;

public class FreezeCommand extends Command {
    private final Revolutionarity plugin;

    public FreezeCommand(Revolutionarity plugin) {
        super("freeze");
        this.plugin = plugin;
        this.setDescription("Freeze a player");
        this.setUsage("freeze <player>");
        this.setPermission("revolutionarity.freeze");
        this.setPermissionMessage("§cNo permission");

        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                Player t = this.plugin.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                if (t != null) {
                    if (t.isImmobile()) {
                        t.setImmobile(false);
                        sender.sendMessage(this.plugin.getPrefix() + "The player " + t.getName() + " is not freezed anymore!");
                    }
                    else {
                        t.setImmobile(true);
                        sender.sendMessage(this.plugin.getPrefix() + "The player " + t.getName() + " got freezed!");
                    }
                }
                else {
                    sender.sendMessage(this.plugin.getPrefix() + "The player " + args[0] + " doesn't exist!");
                }
            }
            else {
                sender.sendMessage(this.plugin.getPrefix() + "/freeze <player>!");
            }
            return true;
        }

        Player player = (Player) sender;

        if (!this.testPermission(player)) {
            if (args.length == 0) {
                player.setImmobile(!player.isImmobile());
            }
            else if (args.length == 1) {
                Player t = player.getServer().getPlayer(args[0].replaceAll("_", " ").replaceAll("\"", ""));

                if (t != null) {
                    if (t.isImmobile()) {
                        t.setImmobile(false);
                        player.sendMessage(this.plugin.getPrefix() + "The player " + t.getName() + " is not freezed anymore!");
                    }
                    else {
                        t.setImmobile(true);
                        player.sendMessage(this.plugin.getPrefix() + "The player " + t.getName() + " got freezed!");
                    }
                }
                else {
                    player.sendMessage(this.plugin.getPrefix() + "The player " + args[0] + " doesn't exist!");
                }
            }
            else {
                player.sendMessage(this.plugin.getPrefix() + "/freeze <player>");
            }
        }
        return false;
    }
}
