package me.ed333.easybot.api.utils;

import org.bukkit.entity.Player;

import java.util.List;

public interface ILanguageUtils {
    void loadLang();
    String getLangText(String path);
    String getLangText(String path, Player p);
    String hoverEvent_txt_replace(String txt);
    List<String> getLangList(String path);
    List<String> getLangList(String path, Player p);
}
