package me.ed333.easybot.plugin.with_mirai_api_http.utils;

import me.ed333.easybot.api.BotAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class MigrateData {
    private static ConfigurationSection oldBoundData;
    // f1: boundData.yml
    // f2: playerConfig.yml
    public MigrateData(File f1, File f2) {

        ConfigurationSection newDataSection = BotAPI.getiConfigManager().getData().getConfigurationSection("Player");
        oldBoundData = YamlConfiguration.loadConfiguration(f1).getConfigurationSection("Name_Bound");
        YamlConfiguration oldPlayerConfig = YamlConfiguration.loadConfiguration(f2);

        HashMap<String, Long> map = new HashMap<>();
        for (String s : oldBoundData.getKeys(false)) {
            map.put(s, oldBoundData.getLong(s));
        }

        Set<String> playerConfigKeys = oldPlayerConfig.getKeys(false);
        if (newDataSection != null) {
            for (String str : playerConfigKeys) {
                newDataSection.set(str + ".name", oldPlayerConfig.getString(str + ".lastName"));
                newDataSection.set(str + ".enableBot", oldPlayerConfig.getBoolean(str + ".enable_Bot"));
                newDataSection.set(str + ".bind", map.get(oldPlayerConfig.getString(str + ".lastName")));
            }
        }

        BotAPI.getiConfigManager().saveData();
    }
}
