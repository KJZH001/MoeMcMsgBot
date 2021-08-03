package me.ed333.easyBot.command;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.BotMain;
import me.ed333.easyBot.utils.MessageChain;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static me.ed333.easyBot.BOT.*;
import static me.ed333.easyBot.BOT.codeMap;
import static me.ed333.easyBot.utils.Messages.getMsg;

public class Bind {
    public Bind(String[] args, CommandSender sender) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(getMsg("notPlayer", null));
            return;
        }

        Player p = (Player) sender;
        if (args.length == 2 && isQQ(args[1])) {
            if (name_isBound(sender.getName())) {
                sender.sendMessage(getMsg("NameIsBound", null));
                return;
            }

            if (!qq_isBound(Long.parseLong(args[1]))) {
                codeMap.put(p.getName(), genVerifyCode());
                verifyPlayers.put(p.getName(), Long.parseLong(args[1]));
                sender.sendMessage(getMsg("verify_msgSend", null));

                // 发送临时消息
                sendTempMessage(
                        Long.parseLong(args[1]),
                        groupID,
                        false,
                        0,
                        new MessageChain().addPlain(getMsg("verify_text", p).replace("\\n", "\n")
                                .replace("%1", codeMap.get(p.getName()).toString())
                                .replace("%2", String.valueOf(BOT.verifyTime))
                        )
                );

                // 建立验证码自销任务
                new codeAutomaticallyExpires(p.getName()).runTaskLater(BotMain.getPlugin(BotMain.class), verifyTime*60*20);

            } else sender.sendMessage(getMsg("QQisBound", null));

        } else sender.sendMessage(getMsg("InvalidArgs", null));
    }

    private boolean isQQ(@NotNull String qq) {
        return qq.matches(Objects.requireNonNull(BotMain.cfg.getString("regex")));
    }
}
