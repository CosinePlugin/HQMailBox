package kr.hqservice.mail.config;

import kr.hqservice.mail.data.DatabaseSetting;
import kr.hqservice.mail.registry.SettingRegistry;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class SettingConfig {

    private static final String path = "config.yml";

    private final File file;
    private final YamlConfiguration config;

    private final SettingRegistry settingRegistry;

    public SettingConfig(Plugin plugin, SettingRegistry settingRegistry) {
        var newFile = new File(plugin.getDataFolder(), path);
        if (!newFile.exists() && plugin.getResource(path) != null) {
            plugin.saveResource(path, false);
        }
        file = newFile;
        config = YamlConfiguration.loadConfiguration(newFile);
        this.settingRegistry = settingRegistry;
    }

    public void load() {
        var prefix = config.getString("prefix");

        var databaseSection = config.getConfigurationSection("db");
        var host = databaseSection.getString("host");
        var port = databaseSection.getInt("port");
        var database = databaseSection.getString("database");
        var user = databaseSection.getString("user");
        var password = databaseSection.getString("password");
        var maxPoolSize = databaseSection.getInt("max-pool-size");
        var minPoolSize = databaseSection.getInt("min-pool-size");

        var databaseSetting = new DatabaseSetting(host, port, database, user, password, maxPoolSize, minPoolSize);
        var settingSection = config.getConfigurationSection("setting");
        var maxTime = settingSection.getInt("max-time");

        SettingRegistry.prefix = prefix;
        settingRegistry.setDatabaseSetting(databaseSetting);
        settingRegistry.setMaxTime(maxTime);
    }
}
