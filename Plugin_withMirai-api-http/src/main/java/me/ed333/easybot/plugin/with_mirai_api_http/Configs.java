package me.ed333.easybot.plugin.with_mirai_api_http;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public enum Configs {
    CFG_VERSION("version"),
    UPDATE_CHECK("updateCheck"),
    HOST("host"),
    SEND_DELAY("sendDelay"),
    TIME_FORMAT("timeFormat"),
    CODE_EXPIRE_TIME("codeExpireTime"),
    CODE_COOLDOWN("codeCoolDown"),
    KEY("Key"),
    ENABLE_BOT("enableBot");


    private final String key;
    public static YamlConfiguration config;
    Configs(String key) {
        this.key = key;
    }

    public String getString() { return config.getString(key); }

    public @NotNull Integer getInt() { return config.getInt(key); }

    public @NotNull Long getLong() { return config.getLong(key); }

    public @NotNull Boolean getBoolean() { return config.getBoolean(key); }
}
