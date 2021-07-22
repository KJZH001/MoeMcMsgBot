package me.ed333.easyBot.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ed333.easyBot.BotMain;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Messages {
    private static final Map<String, Object> msgMap = new HashMap<>();

    public static void initializeMsg() {
        Set<String> keys = BotMain.lang.getKeys(true);

        for (String key : keys) {
            msgMap.put(key, replaceColor(BotMain.lang.get(key).toString()));
        }
    }

    public static String getMsg(String key, Player p) {
        if (BotMain.hasPAPI) return PlaceholderAPI.setPlaceholders(p, String.valueOf(msgMap.get(key)));
        else return msgMap.get(key).toString();
    }

    public static List<String> getList(String key) {
        List<String> rawlist = BotMain.lang.getStringList(key);
        List<String> newlist = new ArrayList<>();
        if (BotMain.hasPAPI) {
            for (String s : rawlist) newlist.add(s.replace("&", "§"));
        } else return rawlist;
        return newlist;
    }

    public static void reloadMsg() {
        BotMain.lang = YamlConfiguration.loadConfiguration(BotMain.INSTANCE.langFile);
    }



    private static String replaceColor(String text) {
        return text.replace("&", "§");
    }

    protected static String hoverEvent_txt_replace( String txt) {
        return txt.replace("[", "").replace("]", "").replace(", ", "\n");
    }

    public static class DEBUG {
        private static final boolean b = BotMain.cfg.getBoolean("DEBUG");
        public static void info(String txt) {
            if (b) BotMain.INSTANCE.getLogger().info(txt);
        }

        public static void warn(String txt) {
            if (b) BotMain.INSTANCE.getLogger().warning(txt);
        }
    }
}
