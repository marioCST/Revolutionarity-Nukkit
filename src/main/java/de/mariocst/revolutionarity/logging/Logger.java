package de.mariocst.revolutionarity.logging;

import cn.nukkit.Player;
import de.mariocst.revolutionarity.Revolutionarity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Logger {
    private File file;
    private final ArrayList<String> contents = new ArrayList<>();

    public Logger(Revolutionarity plugin) {
        File dir = new File(plugin.getDataFolder(), "logs");

        if (!dir.exists()) {
            try {
                Files.createDirectory(dir.toPath());
            }
            catch (IOException ignored) { }
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDateTime time = LocalDateTime.now();
        String formatted = dtf.format(time);

        this.file = new File(dir, formatted + ".txt");

        if (this.file.exists()) {
            int i = 1;

            while ((this.file = new File(dir, formatted + " (" + i +  ").txt")).exists()) {
                i++;
            }
        }

        if (!this.file.exists()) {
            try {
                Files.createFile(this.file.toPath());
            }
            catch (IOException ignored) { }
        }
    }

    public void log(String msg) {
        this.contents.clear();

        try {
            this.contents.addAll(Files.readAllLines(this.file.toPath()));
        }
        catch (IOException ignored) { }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();

        this.contents.add("[" + dtf.format(time) + "] " + msg);

        try {
            Files.write(this.file.toPath(), this.contents);
        }
        catch (IOException ignored) { }
    }

    public void log(Player flagged, String check, String details) {
        this.contents.clear();

        try {
            this.contents.addAll(Files.readAllLines(this.file.toPath()));
        }
        catch (IOException ignored) { }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();

        String dtls = details.equals("") ? "" : ". Details: " + details;

        String msg = "The player " + flagged.getName() + " got flagged for " + check + "! DeviceOS: " + flagged.getLoginChainData().getDeviceOS() + dtls;

        this.contents.add("[" + dtf.format(time) + "] " + msg);

        try {
            Files.write(this.file.toPath(), this.contents);
        }
        catch (IOException ignored) { }
    }
}
