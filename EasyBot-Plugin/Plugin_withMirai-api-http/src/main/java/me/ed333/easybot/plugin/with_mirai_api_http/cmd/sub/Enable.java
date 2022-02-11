package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.messages.DEBUG;
import me.ed333.easybot.api.utils.IBotMiraiHttpUtils;
import me.ed333.easybot.api.utils.IConfigManager;
import me.ed333.easybot.api.utils.ILanguageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Enable {
    public Enable(CommandSender sender, String[] args) {
        IConfigManager icm = BotAPI.getiConfigManager();
        ILanguageUtils ilu = BotAPI.getILanguageUtils();
        IBotMiraiHttpUtils ibm = BotAPI.getIBotMiraiHttpUtils();

        if (!(sender instanceof Player)) {
            sender.sendMessage(ilu.getLangText("notPlayer"));
            return;
        }

        Player p = (Player) sender;

        if (args.length != 1) {
            sender.sendMessage(ilu.getLangText("InvalidArgs"));
            return;
        }

        ConfigurationSection section = icm.getData().getConfigurationSection("Player");
        if (section != null) {
            ibm.addEnableBotPlayer(p);
            section.set(p.getUniqueId() + "enableBot", true);
            p.sendMessage(ilu.getLangText("player_enableBot"));
        } else {
            DEBUG.debugWarn("Player data configuration section is null.");
        }
    }
}
