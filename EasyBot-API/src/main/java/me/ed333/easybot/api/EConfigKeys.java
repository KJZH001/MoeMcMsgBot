package me.ed333.easybot.api;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
public enum EConfigKeys {
    BOTID("botID"),
    GROUPID("groupID"),
    DEBUG("DEBUG");

    private final String key;
    public static YamlConfiguration config;

    EConfigKeys(String key) {
        this.key = key;
    }

    public String getString() { return config.getString(key); }

    public @NotNull Long getLong() { return config.getLong(key); }

    public @NotNull List<Long> getLongList() {
        return config.getLongList(key);
    }

    public @NotNull Boolean getBoolean() { return config.getBoolean(key); }
}
