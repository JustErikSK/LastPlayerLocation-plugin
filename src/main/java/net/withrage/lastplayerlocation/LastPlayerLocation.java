package net.withrage.lastplayerlocation;

import net.withrage.lastplayerlocation.listener.PlayerConnectionListener;
import net.withrage.lastplayerlocation.storage.LocationStorage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LastPlayerLocation extends JavaPlugin {

    private PlayerConnectionListener connectionListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        LocationStorage locationStorage =
                new LocationStorage(this);

        connectionListener =
                new PlayerConnectionListener(this, locationStorage);

        getServer().getPluginManager().registerEvents(
                connectionListener,
                this
        );
    }

    @Override
    public void onDisable() {
        if (!getConfig().getBoolean("save-on-shutdown", true)) {
            return;
        }

        for (Player player : getServer().getOnlinePlayers()) {
            connectionListener.saveIfEligible(player);
        }
    }
}