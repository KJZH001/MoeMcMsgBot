package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.utils.ILanguageUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.BotUtils;
import org.bukkit.command.CommandSender;

public class Reload {
    public Reload(CommandSender sender, String[] args) {
        if (sender.hasPermission("bot.reload")) {
            ILanguageUtils ilu = BotAPI.getILanguageUtils();
            sender.sendMessage(ilu.getLangText("startReload"));
            BotAPI.getiConfigManager().checkFile();
            BotAPI.getiConfigManager().saveData();
            ilu.loadLang();
            BotUtils.close();
            BotUtils.initialize();
            sender.sendMessage(ilu.getLangText("reload"));
        }
    }
}
