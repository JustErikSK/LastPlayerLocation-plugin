package net.withrage.lastplayerlocation.storage;

import net.withrage.lastplayerlocation.LastPlayerLocation;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocationStorage {

    private final LastPlayerLocation plugin;
    private final File file;

    public LocationStorage(LastPlayerLocation plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "last_locations.txt");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public void savePlayerLocation(Player player) {
        Location location = player.getLocation();

        NamespacedKey dimensionKey = player.getWorld().getKey();

        String dateFormat = plugin.getConfig()
                .getString("date-format", "yyyy-MM-dd HH:mm:ss");

        String savedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(dateFormat));

        String entry =
                "UUID: " + player.getUniqueId() + System.lineSeparator() +
                "Player Name: " + player.getName() + System.lineSeparator() +
                "Dimension: " + dimensionKey + System.lineSeparator() +
                "X: " + location.getX() + System.lineSeparator() +
                "Y: " + location.getY() + System.lineSeparator() +
                "Z: " + location.getZ() + System.lineSeparator() +
                "Saved At: " + savedAt + System.lineSeparator() +
                System.lineSeparator();

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(entry);
        } catch (IOException e) {
            plugin.getLogger().severe(
                    "Could not save location for " + player.getName()
            );

            e.printStackTrace();
        }
    }
}