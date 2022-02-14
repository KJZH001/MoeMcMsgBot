package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.messages.MessageChain;
import me.ed333.easybot.api.messages.MessageSection;
import me.ed333.easybot.api.utils.ILanguageUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import me.ed333.easybot.plugin.with_mirai_api_http.Main;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.BotUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.VerifyingPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Bind {
    public Bind(CommandSender sender, String[] args) {
        ILanguageUtils ilu = BotAPI.getILanguageUtils();
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(ilu.getLangText("notPlayer"));
            return;
        }

        Player p = (Player) sender;

        // 参数长度是否正确
        if (args.length != 2) {
            sender.sendMessage(ilu.getLangText("InvalidArgs"));
            return;
        }

        // 检查 QQ 号码的格式是否正确
        if (!isQQ(args[1])) {
            sender.sendMessage(ilu.getLangText("nonQQ"));
            return;
        }

        // 检查是否处于已绑定状态
        if (BotAPI.getIbu().name_isBound(sender.getName())) {
            sender.sendMessage(ilu.getLangText("NameIsBound"));
            return;
        }

        Long qq = Long.parseLong(args[1]);
        String verifyCode = BotUtils.genVerifyCode();

        // 检查输入的qq是否已绑定
        if (BotAPI.getIbu().qq_isBound(qq)) {
            sender.sendMessage(ilu.getLangText("QQisBound"));
            return;
        }


        // 检查是否处于发送冷却状态
        if (BotUtils.getPlayersInCool().contains(p)) {
            sender.sendMessage(ilu.getLangText("verify_coolingDown"));
            return;
        }

        try {
            try {
                // 将原有的验证状态删除
                BotUtils.getVerifyingPlayers().forEach((verifyingPlayer, code) -> {
                    if (verifyingPlayer.getPlayer().equals(p)) {
                        verifyingPlayer.run();
                        // 跳出循环
                        throw new Error();
                    }
                });
            } catch (Error ignored) {}

            BotAPI.getIbu().getAllGroups().forEach((groupID, group) -> {
                if (group.getAll().containsKey(qq)) {
                    VerifyingPlayer vfp = new VerifyingPlayer(p, qq);

                    MessageSection section = new MessageSection(groupID, qq);
                    MessageChain chain = new MessageChain().addPlain(
                            ilu.getLangText("verify_text", p)
                                    .replace("\\n", "\n")
                                    .replace("%1", verifyCode)
                                    .replace("%2", Configs.CODE_EXPIRE_TIME.getString())
                    );
                    section.addMessageChain(chain);
                    group.sendTempMessage(qq, section);
                    sender.sendMessage(ilu.getLangText("verify_msgSend"));
                    BotUtils.addVerifyingPlayer(vfp, verifyCode);
                    vfp.runTaskLater(Main.getPlugin(Main.class), Configs.CODE_EXPIRE_TIME.getInt()*60*20).getTaskId();
                    // 跳出循环
                    throw new Error();
                }
            });
            sender.sendMessage(ilu.getLangText("verify_qqNotFind"));
        } catch (Error ignored) {}

    }

    private boolean isQQ(@NotNull String qq) {
        if (qq.length() > 12) {
            return false;
        }
        return qq.matches("[1-9][0-9]{5,12}");
    }
}