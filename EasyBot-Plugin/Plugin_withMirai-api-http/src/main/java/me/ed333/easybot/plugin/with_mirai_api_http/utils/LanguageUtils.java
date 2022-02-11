package me.ed333.easybot.plugin.with_mirai_api_http.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.utils.ILanguageUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LanguageUtils implements ILanguageUtils {
    private static YamlConfiguration lang;

    @Override
    public void loadLang() {
            lang = BotAPI.getiConfigManager().getLang();
    }

    @Override
    public String getLangText(String path) {
        String msg = lang.getString(path);
        if (Main.hasPAPI() && msg != null) {
            msg = PlaceholderAPI.setPlaceholders(null, replaceColor(msg));
        }
        return msg;
    }

    @Override
    public String getLangText(String path, Player p) {
        String msg = lang.getString(path);
        if (Main.hasPAPI() && msg != null) {
            msg = PlaceholderAPI.setPlaceholders(p, replaceColor(msg));
        }
        return msg;
    }

    @Override
    public @NotNull String hoverEvent_txt_replace(@NotNull String txt) {
        return txt.replace("[", "").replace("]", "").replace(", ", "\n");
    }

    @Override
    public @NotNull List<String> getLangList(String path) {
        List<String> rawList = lang.getStringList(path);
        List<String> coloredList = new ArrayList<>();
        rawList.forEach(s -> coloredList.add(PlaceholderAPI.setPlaceholders(null, replaceColor(s))));
        return coloredList;
    }

    @Override
    public List<String> getLangList(String path, Player p) {
        List<String> rawList = lang.getStringList(path);
        List<String> coloredList = new ArrayList<>();
        rawList.forEach(s -> coloredList.add(PlaceholderAPI.setPlaceholders(p, replaceColor(s))));
        return coloredList;
    }

    private static @NotNull String replaceColor(@NotNull String str) {
        return str.replace("&", "§");
    }
}
