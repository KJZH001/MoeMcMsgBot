package me.ed333.easyBot.command;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.ed333.easyBot.BOT.codeMap;
import static me.ed333.easyBot.BOT.verifyPlayers;
import static me.ed333.easyBot.BotMain.INSTANCE;
import static me.ed333.easyBot.BotMain.boundData;
import static me.ed333.easyBot.utils.Messages.getMsg;

public class Verify {
    public Verify(String[] args, CommandSender sender) throws IOException {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getMsg("notPlayer", null));
            return;
        }

        Player p = (Player) sender;

        if (args[0].equalsIgnoreCase("verify") && args.length == 2) {
            if (BOT.codeMap.containsKey(p.getName())) {
                if (args[1].equals(codeMap.get(p.getName()).toString())) {
                    boundData.set("QQ_Bound." + verifyPlayers.get(p.getName()), p.getName());
                    boundData.set("Name_Bound." + p.getName(), verifyPlayers.get(p.getName()));
                    codeMap.remove(p.getName());
                    verifyPlayers.remove(p.getName());
                    boundData.save(INSTANCE.boundDataFile);
                    sender.sendMessage(getMsg("verify_success",null));
                } else sender.sendMessage(getMsg("verifyCode_Err", null));
            } else {
                sender.sendMessage(getMsg("notInVerifying", null));
            }
        }
    }
}
