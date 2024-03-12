package network.vonix.vonixwelcomer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class VonixWelcomer extends JavaPlugin implements Listener {
    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Create or load the config file
        createConfig();
    }

    @Override
    public void onDisable() {
        // Nothing to do on disable
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String welcomeMessage = config.getString("welcomeMessage");
        if (welcomeMessage != null && !welcomeMessage.isEmpty()) {
            welcomeMessage = ChatColor.translateAlternateColorCodes('&', welcomeMessage);
            welcomeMessage = welcomeMessage.replace("{player}", player.getName());
            player.sendMessage(welcomeMessage);
        }
    }

    private void createConfig() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
