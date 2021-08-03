package me.ed333.easyBot.command;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.BotMain;
import me.ed333.easyBot.Client;
import me.ed333.easyBot.PlaceHolders;
import me.ed333.easyBot.utils.Messages;
import org.bukkit.command.CommandSender;

public class Reload {
    private final BotMain i = BotMain.INSTANCE;

    public Reload(CommandSender sender) throws Exception {
        if (sender.hasPermission("bot.reload")) {
            sender.sendMessage("§3BOT: §a保存配置并重新连接...");
            BotMain.boundData.save(i.boundDataFile);
            BotMain.playerConfig.save(i.playerConfigFile);

            BotMain.INSTANCE.checkFile();

            Messages.reloadMsg();
            BotMain.flushCfgVal();

            BOT.enableBot = BotMain.cfg.getBoolean("enable_Bot");
            if (BOT.enableBot) {
                if (Client.isConnected) BOT.close();
                BOT.initializeBot();
            } else BOT.close();

            if (BotMain.hasPAPI) new PlaceHolders().register();
        } else sender.sendMessage(Messages.getMsg("permissionDeny", null));
    }
}
