package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.messages.MessageChain;
import me.ed333.easybot.api.messages.MessageSection;
import me.ed333.easybot.api.utils.IBotUtils;
import me.ed333.easybot.api.utils.ILanguageUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import me.ed333.easybot.plugin.with_mirai_api_http.Main;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.BotUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.VerifyingPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BindChange {
    public BindChange(CommandSender sender, String[] args) {
        IBotUtils ibu = BotAPI.getIbu();
        ILanguageUtils ilu = BotAPI.getILanguageUtils();
        YamlConfiguration data = BotAPI.getiConfigManager().getData();

        if (!(sender instanceof Player)) {
            sender.sendMessage(ilu.getLangText("notPlayer"));
            return;
        }

        Player p = (Player) sender;

        if (args.length != 3) {
            sender.sendMessage(ilu.getLangText("InvalidArgs"));
            return;
        }

        if (!isQQ(args[1])|| !isQQ(args[2])) {
            sender.sendMessage(ilu.getLangText("nonQQ"));
            return;
        }

        long oldQQNum = Long.parseLong(args[1]);
        long newQQNum = Long.parseLong(args[2]);

        if (ibu.qq_isBound(newQQNum)) {
            sender.sendMessage(ilu.getLangText("newQQisBind"));
            return;
        }

        if (oldQQNum == newQQNum) {
            sender.sendMessage(ilu.getLangText("sameQQ"));
            return;
        }

        if (oldQQNum != data.getLong("Player." + p.getUniqueId() + ".bind")) {
            sender.sendMessage(ilu.getLangText("playerCBErr"));
            return;
        }

        sender.sendMessage(ilu.getLangText("playerBindChange"));

        Integer verifyCode = BotUtils.genVerifyCode();

        try {
            BotAPI.getIbu().getAllGroups().forEach((groupID, group) -> {
                if (group.getAll().containsKey(newQQNum)) {
                    VerifyingPlayer vfp = new VerifyingPlayer(p, newQQNum);
                    MessageSection section = new MessageSection(groupID, newQQNum);
                    MessageChain chain = new MessageChain().addPlain(
                            ilu.getLangText("verify_text", p)
                                    .replace("\\n", "\n")
                                    .replace("%1", verifyCode + "")
                                    .replace("%2", Configs.CODE_EXPIRE_TIME.getString())
                    );
                    section.addMessageChain(chain);

                    group.sendTempMessage(newQQNum, section);

                    sender.sendMessage(ilu.getLangText("verify_msgSend"));
                    BotUtils.addVerifyingPlayer(vfp, verifyCode);

                    vfp.runTaskLater(Main.getPlugin(Main.class), Configs.CODE_EXPIRE_TIME.getInt()*60*20).getTaskId();

                    // 跳出循环
                    throw new Error();
                }
            });
        } catch (Error ignored) {}

        System.out.println(BotUtils.getVerifyingPlayers());
    }

    private boolean isQQ(@NotNull String qq) {
        if (qq.length() > 12) {
            return false;
        }
        return qq.matches("[1-9][0-9]{5,12}");
    }
}
