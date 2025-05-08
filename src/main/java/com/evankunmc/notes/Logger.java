package com.evankunmc.notes;

import com.sainttx.notes.NotesPlugin;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
    private final File PLUGIN_FOLDER = NotesPlugin.plugin.getDataFolder();
    public static String ACTION_DEPOSIT = "deposited";
    public static String ACTION_WITHDRAW = "withdrew";

    public void addLog(String sender, String receiver, String balance) {
        File logs = checkCreateFileDirectory(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log");
        addLineToLog(logs.getPath(), sender + " gave " + receiver + " $ " + balance);
    }

    public void addLog(Player player, String balance, String action) {
        File logs = checkCreateFileDirectory(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log");
        addLineToLog(logs.getPath(), player.getName() + "(" + player.getUniqueId().toString() + ") " + action + " $" + balance);
    }

    public void addLineToLog(String path, String message) {
        BufferedWriter writer = null;
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String line = "[" + time + "] " + message;

        try {
            writer = new BufferedWriter(new FileWriter(path, true)); // 'true' for append mode.
            writer.write(line);
            writer.newLine(); // Add a newline character
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your plugin.
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace(); // Handle closing exception.
                }
            }
        }
    }

    private File checkCreateFileDirectory(String fileDirectory) {
        File logs = new File(PLUGIN_FOLDER, "logs/" + fileDirectory);
        File logsFolder = logs.getParentFile();
        if (!logsFolder.exists()) {
            logsFolder.mkdirs();
        }

        if (!logs.exists()) {
            try {
                logs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return logs;
    }

}
