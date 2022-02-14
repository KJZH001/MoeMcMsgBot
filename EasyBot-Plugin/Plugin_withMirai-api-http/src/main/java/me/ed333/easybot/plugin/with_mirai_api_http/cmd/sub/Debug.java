package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import me.ed333.easybot.plugin.with_mirai_api_http.Main;
import me.ed333.easybot.plugin.with_mirai_api_http.SocketClient;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.BotUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class Debug {
    public Debug(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("bot.debug")) {
            sender.sendMessage("BOT_[DEBUG_INFO] enable bot: " + Configs.ENABLE_BOT.getBoolean());
            sender.sendMessage("BOT_[DEBUG_INFO] bot initialized: " + BotAPI.getIbu().isInitialized());
            sender.sendMessage("BOT_[DEBUG_INFO] web-socket connected: " + SocketClient.isConnected);
            sender.sendMessage("BOT_[DEBUG_INFO] plugin version: " + Main.getPlugin(Main.class).getDescription().getVersion());
            sender.sendMessage("BOT_[DEBUG_INFO] mirai-api-http: " + BotAPI.getIBotMiraiHttpUtils().getApiVer());
            sender.sendMessage("BOT_[DEBUG_INFO] Java Version: " + g("java.version"));
            sender.sendMessage("BOT_[DEBUG_INFO] Java Home: " + g("java.home"));
            sender.sendMessage("BOT_[DEBUG_INFO] User dir: " + g("user.dir"));
            sender.sendMessage(String.format("BOT_[DEBUG_INFO] Os name: %s-%s", g("os.name"), g("os.arch")));
            sender.sendMessage("BOT_[DEBUG_INFO] has PlaceHolderAPI: " + Main.hasPAPI());
            if (Main.hasPAPI()) {
                sender.sendMessage("BOT_[DEBUG_INFO] PlaceholderAPI version" + PlaceholderAPIPlugin.getInstance().getDescription().getVersion());
                sender.sendMessage("BOT_[DEBUG_INFO] registered PlaceholderAPI expansions: " + PlaceholderAPI.getRegisteredIdentifiers());
            }
            sender.sendMessage(String.format("BOT_[DEBUG_INFO] Verify in cooling down players' set: %s", BotUtils.getPlayersInCool()));
            sender.sendMessage(String.format("BOT_[DEBUG_INFO] Verifying players' set: %s", BotUtils.getVerifyingPlayers()));
        }
    }

    private @Unmodifiable String g(String k) {
        return System.getProperty(k);
    }
}
