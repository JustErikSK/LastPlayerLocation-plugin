package net.withrage.lastplayerlocation.listener;

import net.withrage.lastplayerlocation.LastPlayerLocation;
import net.withrage.lastplayerlocation.storage.LocationStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    private final LastPlayerLocation plugin;
    private final LocationStorage locationStorage;
    private final Map<UUID, Long> joinTimes = new HashMap<>();

    public PlayerConnectionListener(
            LastPlayerLocation plugin,
            LocationStorage locationStorage
    ) {
        this.plugin = plugin;
        this.locationStorage = locationStorage;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        joinTimes.put(
                event.getPlayer().getUniqueId(),
                System.currentTimeMillis()
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        saveIfEligible(player);

        joinTimes.remove(player.getUniqueId());
    }

    public void saveIfEligible(Player player) {
        Long joinTime = joinTimes.get(player.getUniqueId());

        if (joinTime == null) {
            return;
        }

        long onlineTimeSeconds =
                (System.currentTimeMillis() - joinTime) / 1000;

        long minimumOnlineTime = plugin.getConfig()
                .getLong("minimum-online-time", 60);

        if (onlineTimeSeconds < minimumOnlineTime) {
            return;
        }

        locationStorage.savePlayerLocation(player);
    }
}