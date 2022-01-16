package de.mariocst.revolutionarity.forms;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.forms.custom.CustomForm;

import java.io.IOException;

public class ReportForm {
    public void openReport(Revolutionarity plugin, Player player) {
        CustomForm form = new CustomForm.Builder(plugin, "§6Report")
                .addElement(new ElementInput("Player", player.getName()))
                .addElement(new ElementInput("Reason", "Cheating"))
                .onSubmit((p, r) -> {
                    if (r.getInputResponse(0).isEmpty() || r.getInputResponse(1).isEmpty()) {
                        player.sendMessage("Please fill out all fields!");
                        return;
                    }

                    Player t = plugin.getServer().getPlayer(r.getInputResponse(1));
                    IPlayer oT = plugin.getServer().getOfflinePlayer(r.getInputResponse(1));
                    String[] args = r.getInputResponse(1).split(" ");

                    if (t != null) {
                        StringBuilder msg = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            msg.append(args[i]).append(" ");
                        }

                        player.sendMessage("The player §c" + t.getName() + " §fhas been reported for §c" + msg + "§f!");

                        for (Player staff : plugin.getServer().getOnlinePlayers().values()) {
                            if (staff.hasPermission("revolutionarity.staff")) {
                                staff.sendMessage(plugin.getPrefix() + "The player §a" + player.getName() + " §freported §c" + t.getName() + " §ffor §c" + msg + "§f!");
                            }
                        }

                        if (!plugin.getPluginSettings().getDiscordWebhookLink().equals("")) {
                            try {
                                plugin.sendReport(player, t, msg.toString());
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

                        for (Player staff : plugin.getServer().getOnlinePlayers().values()) {
                            if (staff.hasPermission("revolutionarity.staff")) {
                                staff.sendMessage("The player §a" + player.getName() + " §freported §c" + oT.getName() + " §ffor §c" + msg + "§f!");
                            }
                        }

                        if (!plugin.getPluginSettings().getDiscordWebhookLink().equals("")) {
                            try {
                                plugin.sendReport(player, oT, msg.toString());
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
                })
                .build();
        form.send(player);
    }
}
