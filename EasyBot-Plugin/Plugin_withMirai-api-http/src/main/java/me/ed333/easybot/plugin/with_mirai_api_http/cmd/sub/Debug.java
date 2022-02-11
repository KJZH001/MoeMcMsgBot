package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import me.ed333.easybot.plugin.with_mirai_api_http.Main;
import me.ed333.easybot.plugin.with_mirai_api_http.SocketClient;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class Debug {
    public Debug(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("bot.debug")) {
            sender.sendMessage("BOT_[DEBUG_INFO] enable bot: " + Configs.ENABLE_BOT.getBoolean());
            sender.sendMessage("BOT_[DEBUG_INFO] plugin version: " + Main.getPlugin(Main.class).getDescription().getVersion());
            sender.sendMessage("BOT_[DEBUG_INFO] socket connected: " + SocketClient.isConnected);
            sender.sendMessage("BOT_[DEBUG_INFO] mirai-api-http: " + BotAPI.getIBotMiraiHttpUtils().getApiVer());
            sender.sendMessage("BOT_[DEBUG_INFO] has PlaceHolderAPI: " + Main.hasPAPI());
            sender.sendMessage("BOT_[DEBUG_INFO] Java Version: " + g("java.version"));
            sender.sendMessage("BOT_[DEBUG_INFO] Java Home: " + g("java.home"));
            sender.sendMessage("BOT_[DEBUG_INFO] User dir: " + g("user.dir"));
            sender.sendMessage(String.format("BOT_[DEBUG_INFO] Os name: %s-%s", g("os.name"), g("os.arch")));
            if (Main.hasPAPI()) {
                sender.sendMessage("BOT_[DEBUG_INFO] registered PlaceholderAPI expansions: " + PlaceholderAPI.getRegisteredIdentifiers());
            }
        }
    }

    private @Unmodifiable String g(String k) {
        return System.getProperty(k);
    }
}
